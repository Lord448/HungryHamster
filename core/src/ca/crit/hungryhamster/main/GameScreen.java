package ca.crit.hungryhamster.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ca.crit.hungryhamster.GameHandler;
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
    private Timer sesionTimer;

    /*USER INTERFACE*/
    private Skin skin;
    private Stage stage;
    private Label lblTime;
    private Label lblReps;

    /*TEXT*/
    //private final BitmapFont font;

    private final GameText WinText;

    /*FIXED TYPES*/
    private boolean repetitionFinished = false;
    private boolean sessionFinished = false;
    private int repetitions = 0;

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
        /*USER INTERFACE*/
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("UISkin/uiskin.json"));
        graphicsConstruct();
        /*OBJECTS*/
        sesionTimer = new Timer(Timer.Modes.TIME_MEASURE);
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
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        lblTime.setText(animal.timer.getStringTime());
        Gdx.input.setInputProcessor(stage);
        stage.draw();
        stage.act(deltaTime);
    }

    private void graphicsConstruct() {
        lblTime = new Label("", skin);
        lblReps = new Label("Reps: 0", skin);
        TextButton btnEndReps = new TextButton("Terminar Repeticion", skin);
        TextButton btnEndSession = new TextButton("Terminar la sesion", skin);

        btnEndReps.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetRepetition();
                GameHandler.sessionReps++;
                lblReps.setText("Reps: " + GameHandler.sessionReps);
            }
        });

        btnEndSession.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        lblTime.setPosition(10, 10);
        lblReps.setPosition(20, 430);
        btnEndReps.setPosition(50, 180);
        btnEndSession.setPosition(50, 150);


        stage.addActor(lblTime);
        stage.addActor(lblReps);
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