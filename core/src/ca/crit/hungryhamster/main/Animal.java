package ca.crit.hungryhamster.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ca.crit.hungryhamster.GameHandler;

public class Animal {
    protected final int REGION_MAX_LIM = 107;
    protected final int REGION_MIN_LIM = 20;
    protected final int REGION_HOUSE = 117;
    private boolean isInHouse = false;
    private boolean isFinished = false;
    private boolean oneActionFlag = true;
    private boolean move = false;
    private final int width;
    private final int height;
    private int animalCounter = 0;
    private int nextPin;
    private float x, y;
    private final float speed;
    private final float[] positions = new float[GameHandler.numHouseSteps];
    private final Texture animal_texture;
    private final GameText winText;
    private final GameText extraText;
    private final Sound victorySound;
    public Circle hitbox;
    public final Timer timer;

    public Animal (int x, int y, int width, int height, float speed) {
        float positionSet = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        hitbox = new Circle((float)width/4, (float)height/4, (float)height/4+1);
        GameHandler.foodPositions = new float[GameHandler.numHouseSteps];
        animal_texture = new Texture("Animals/cutiehamster.png");
        victorySound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Effects/achievement.ogg"));
        winText = new GameText("¡Bien \nHecho!", 3, 80);
        winText.setScales(0.2f, 0.3f);
        extraText = new GameText("¡Tu puedes \n un poco más!", 3, 80);
        extraText.setScaleX(0.1f);
        nextPin = GameHandler.minStep;
        timer = new Timer(Timer.Modes.TIME_MEASURE);
        //Each position has a step of 7.5 units when we have a length of 8 positions
        for(int i = 0, j = 0; i < positions.length; i++) {
            positionSet += ((float) (REGION_MAX_LIM - REGION_MIN_LIM) / positions.length);
            positions[i] = positionSet;
            System.out.println("Pos:" + i + " " + positions[i]);
            if(i%2 == 0) { //If i is even
                GameHandler.foodPositions[j] = positions[i];
                j++;
            }
        }
    }
    public void render(final SpriteBatch batch, float deltaTime){
        batch.draw(animal_texture, x, y, width, height);
        timer.timerRender();

        hitbox.setPosition(x, y);
        if(GameHandler.environment == GameHandler.DESKTOP_ENV)
            checkKeyPressed(); //Only for desktop environment

        climb();

        if(isInHouse) {
            extraText.draw(batch);
        }
        else if (isFinished) {
            winText.draw(batch);
            if(oneActionFlag) {
                System.out.println(timer.getStringMeasure());
                timer.stop();
                oneActionFlag = false;
            }
        }

        //Quick an dirty zone
        if(move) {
            if(y < REGION_HOUSE-GameHandler.animHysteresis)
                y += Gdx.graphics.getDeltaTime()*speed;
        }
    }

    //Quick and dirty
    private void checkKeyPressed(){
        for(int i = GameHandler.minStep; i < GameHandler.countsToWin+GameHandler.extraStep; i++) {
            if(Gdx.input.isKeyJustPressed(GameHandler.key[i])) {
                //Quick and dirty zone
                if(isInHouse) {
                    if(i == nextPin) {
                        nextPin++;
                        if(nextPin == GameHandler.countsToWin+GameHandler.extraStep) {
                            move = true;
                            isFinished = true;
                            isInHouse = false;
                            GameSounds.megaWin();
                        }
                        System.out.println("CTW " + nextPin + " | " + (GameHandler.countsToWin));
                    }
                }
                //Standard zone
                if(i == nextPin){
                    nextPin++;
                    GameHandler.touchPins[i] = true;
                    GameSounds.jump();
                }
                for(int j = 0; j < GameHandler.maxStep; j++) {
                    if(j != i)
                        GameHandler.touchPins[j] = false;
                }
            }
        }
    }

    private void climb(){
        float currentPos = y;
        //Controls the move of the animal
        for(int i = GameHandler.minStep; i < GameHandler.maxStep; i++) {
            int index = i - GameHandler.minStep;
            if(GameHandler.touchPins[i]) { //Searching if we need to move the animal
                if(currentPos > positions[index]+GameHandler.animHysteresis) { //Moving down
                    y -= Gdx.graphics.getDeltaTime()*speed;
                }
                else if(currentPos < positions[index]-GameHandler.animHysteresis){ //Moving up
                    timer.start();
                    y += Gdx.graphics.getDeltaTime()*speed;
                    //Checking if we have reached the position
                    if(y >= positions[animalCounter] - GameHandler.animHysteresis) {
                        animalCounter++;
                        System.out.println(animalCounter + " | " + (GameHandler.numHouseSteps));
                        if(animalCounter == GameHandler.numHouseSteps) { //Reached the house
                            timer.getStringMeasure();
                            isInHouse = true;
                            GameSounds.win();
                            isFinished = false;
                        }
                        System.out.println("CTW " + animalCounter + " | " + (GameHandler.countsToWin));
                        if(animalCounter == GameHandler.countsToWin) {
                            isFinished = true;
                            isInHouse = false;
                            GameSounds.megaWin();
                        }
                    }
                }
            }
        }
    }

    public void dispose(){
        victorySound.dispose();
    }
}
