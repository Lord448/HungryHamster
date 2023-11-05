package ca.crit.hungryhamster.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.time.Time;
import ca.crit.hungryhamster.time.Timer;

public class GameScreen implements Screen {

    /*SCREEN*/
    private final Camera camera;
    private final Viewport viewport;

    /*GRAPHICS*/
    private final SpriteBatch batch;
    private final Texture treeHouse;
    private final Background background;

    /*CHARACTER*/
    private final Wizard wizard;
    private Animal animal;

    /*OBJECTS*/
    private Food[] food;
    private Timer stepTimer;

    /*USER INTERFACE*/
    private Skin skin;
    private Skin shadeSkin;
    private Stage stage;
    private Label lblTime;
    private Label lblReps;
    private Label lblRepsUncompleted;
    private Label lblTimeSession;

    /*TEXT*/
    //private final BitmapFont font;

    private final GameText WinText;

    /*FIXED TYPES*/
    private boolean sessionFinished = false;

    /* CONSTANTS */
    private final float animalInitialX = (float) GameHandler.WORLD_WIDTH/2+5;
    private final float animalInitialY = 0f;


    public GameScreen(){
        /*SCREEN*/
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT, camera);
        /*GRAPHICS*/
        background = new Background();
        treeHouse = new Texture("Background/tree_house.png");
        batch = new SpriteBatch();
        /*CHARACTERS*/
        wizard = new Wizard(GameHandler.WORLD_WIDTH/2 - 25 , 2, 26, 25, 1/10f);
        /*TEXT*/
        WinText = new GameText("¡Bien \nHecho!", Gdx.files.internal("Fonts/logros.fnt"), Gdx.files.internal("Fonts/logros.png"), false);
        WinText.setX(3);
        WinText.setY(50);
        /* STAGES AND UI */
        stage = new Stage(new StretchViewport(GameHandler.NATIVE_RES_WIDTH, GameHandler.NATIVE_RES_HEIGHT, new OrthographicCamera()));
        skin = new Skin(Gdx.files.internal("UISkin/uiskin.json"));
        shadeSkin = new Skin(Gdx.files.internal("ShadeUISkin/uiskin.json"));
        graphicsConstruct();
        /*OBJECTS*/
        stepTimer = new Timer(Timer.Modes.TIME_MEASURE);
    }
    @Override
    public void show() {
        food = new Food[GameHandler.numHouseSteps/2];
        animal = new Animal(animalInitialX, animalInitialY, 7, 10, 30);
        //Construct for the food
        for(int i = 0, j = 0; i < GameHandler.numHouseSteps/2; i++, j++) {
            if(j == Fruits.totalFruits) {
                j = 0;
            }
            switch (j) {
                case 0: //BANANA
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.BANANA);
                break;
                case 1: //APPLE
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.APPLE);
                break;
                case 2: //GRAPE
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.GRAPE);
                break;
                case 3: //GREEN_APE
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.GREEN_APE);
                break;
                case 4: //PINEAPPLE
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.PINEAPPLE);
                break;
                case 5: //KIWI
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.KIWI);
                break;
                case 6: //CHERRY
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.CHERRY);
                break;
                case 7: //STRAWBERRY
                    food[i] = new Food((float) GameHandler.WORLD_WIDTH / 2 + 6, GameHandler.foodPositions[i], 5, 6, Fruits.STRAWBERRY);
                break;
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        for(Food i : food) {
            if(Intersector.overlaps(animal.hitbox, i.hitbox) && !i.isPicked()) {
                System.out.println("Collide on " + i);
                i.setPicked(true);
                GameSounds.eat();
            }
        }
        batch.begin();
        /*BACKGROUND*/
        background.renderDynamicBackground(deltaTime, batch);
        background.renderStaticBackground(batch);
        /*OBJECTS*/
        batch.draw(treeHouse, (float) GameHandler.WORLD_WIDTH/2 - 27, 0, GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT+30);
        /*OBJECTS*/
        for(Food i : food) {
            i.render(batch);
        }
        /*CHARACTERS*/
        wizard.render(batch);
        animal.render(batch, deltaTime);

        batch.end();
        graphicsRender(deltaTime);
        stepTimer.update(true);

        if(sessionFinished) {
            //Show Resume UI
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        wizard.dispose();
        animal.dispose();
        WinText.dispose();
    }

    private void graphicsRender(float deltaTime) {
        Time time = new Time();
        time.addTime(animal.timer.getTime());
        time.addTime(GameHandler.sessionTime);

        lblTime.setText("Tiempo de repeticion " + animal.timer.getStringTime());
        lblTimeSession.setText("Tiempo de sesion " + time);
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().apply();
        stage.draw();
        stage.act(deltaTime);
    }

    private void graphicsConstruct() {
        final int xREPS = 10;
        final int xTIME = 280;
        final int xBtn = 10;
        final int yTOP_TEXT = 612;

        lblTime = new Label("Tiempo de repeticion ", skin);
        lblTimeSession = new Label("Tiempo de sesion ", skin);
        lblReps = new Label("Reps Completas: 0", skin);
        lblRepsUncompleted = new Label("Reps Incompletas: 0", skin);
        TextButton btnEndReps = new TextButton("Terminar Repeticion", skin);
        TextButton btnEndSession = new TextButton("Terminar la sesion", skin);

        lblTime.setColor(Color.BLACK);
        lblTimeSession.setColor(Color.BLACK);
        lblReps.setColor(Color.BLACK);
        lblRepsUncompleted.setColor(Color.BLACK);

        btnEndReps.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(animal.isFinished()) {
                    GameHandler.sessionReps++;
                    lblReps.setText("Reps Completas: " + GameHandler.sessionReps);
                }
                else {
                    GameHandler.sessionUncompletedReps++;
                    lblRepsUncompleted.setText("Reps Incompletas: " + GameHandler.sessionUncompletedReps);
                }
                resetRepetition();
                GameHandler.sessionTime.addTime(animal.timer.getTime());
                GameHandler.repsTime.add(animal.timer.getTime());
                animal.timer.reset();
            }
        });

        btnEndSession.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sessionFinished = true;
            }
        });
        lblRepsUncompleted.setPosition(xREPS, yTOP_TEXT);
        lblReps.setPosition(xREPS, yTOP_TEXT - 22);
        lblTimeSession.setPosition(xTIME, yTOP_TEXT);
        lblTime.setPosition(xTIME, yTOP_TEXT - 22);
        btnEndReps.setPosition(xBtn, 250);
        btnEndSession.setPosition(xBtn, 220);

        stage.addActor(lblTime);
        stage.addActor(lblTimeSession);
        stage.addActor(lblReps);
        stage.addActor(lblRepsUncompleted);
        stage.addActor(btnEndReps);
        stage.addActor(btnEndSession);
    }

    private void resetRepetition() {
        animal.reset();
        //Reset Food
        for(Food i : food) {
            i.setPicked(false);
        }
    }
}