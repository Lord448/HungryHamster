package ca.crit.hungryhamster.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.resources.GameSounds;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.text.PrintTag;
import ca.crit.hungryhamster.resources.time.Time;
import ca.crit.hungryhamster.resources.time.TimeMillis;
import ca.crit.hungryhamster.resources.time.Timer;
import ca.crit.hungryhamster.resources.time.TimerMillis;

public class Animal {
    private final String TAG = "Animal";
    protected final int REGION_MAX_LIM = 107;
    protected final int REGION_MIN_LIM = 20;
    protected final int REGION_HOUSE = 117;
    private boolean isInHouse = false;
    private boolean isFinished = false;
    private boolean oneActionFlag = true;
    private boolean moveToUpHouse = false;
    public boolean started = false;
    private boolean meanIsCalculated = false;
    private final int width;
    private final int height;
    private int animalCounter = 0;
    private float x, y;
    private final float speed;
    private final float[] positions = new float[GameHandler.numHouseSteps];
    private final Texture animal_texture;
    private final GameText winText;
    private final GameText extraText;
    private final Sound victorySound;
    public Circle hitbox;
    public final Timer sessionTimer;
    public final Timer timer;
    private Time repTime;
    private final TimerMillis timerMillis;
    public final List<TimeMillis> stepTimeList = new ArrayList<>();

    public Animal (float x, float y, int width, int height, float speed) {
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
        GameHandler.nextStep = GameHandler.minStep;
        timer = new Timer(Timer.Modes.TIME_MEASURE);
        sessionTimer = new Timer(Timer.Modes.TIME_MEASURE);
        repTime = new Time();
        timerMillis = new TimerMillis(Timer.Modes.TIME_MEASURE);
        //Each position has a step of 7.5 units when we have a length of 8 positions
        for(int i = 0, j = 0; i < positions.length; i++) {
            positionSet += ((float) (REGION_MAX_LIM - REGION_MIN_LIM) / positions.length);
            positions[i] = positionSet;
            //System.out.println("Pos:" + i + " " + positions[i]);
            if(i%2 == 0) { //If i is even
                GameHandler.foodPositions[j] = positions[i];
                j++;
            }
        }

        timer.setSecondElapsedCallback(new Timer.SecondElapsedCallback() {
            @Override
            public void secondElapsedCallback() {
                repTime = timer.getTime();
            }
        });
    }
    public void render(final SpriteBatch batch, float deltaTime){
        batch.draw(animal_texture, x, y, width, height);
        timer.update(true);
        timerMillis.update(true);
        sessionTimer.update(true);

        hitbox.setPosition(x, y);
        checkKeyPressed();

        climb();

        if(isInHouse) {
            extraText.draw(batch);
        }
        else if (isFinished) {
            winText.draw(batch);
            if(oneActionFlag) {
                GameHandler.avgRepTimeStep.add(new TimeMillis(GameHandler.calculateMeanOfTimeMillis(stepTimeList)));
                GameHandler.allTimeInSteps.add(new ArrayList<>(stepTimeList));
                PrintTag.print(TAG, "Added stepTimeList");
                PrintTag.print(TAG, "Mean: " + GameHandler.calculateMeanOfTimeMillis(stepTimeList));
                stepTimeList.clear();
                PrintTag.print(TAG, "Finished " + timer);
                timer.stop();
                oneActionFlag = false;
            }
        }

        if(GameHandler.resetAnimal) {

        }

        //Quick an dirty zone
        if(moveToUpHouse) {
            if(y < REGION_HOUSE-GameHandler.animHysteresis)
                y += Gdx.graphics.getDeltaTime()*speed;
        }
    }

    //Quick and dirty
    private void checkKeyPressed(){
        for(int i = GameHandler.minStep; i < GameHandler.countsToWin+GameHandler.extraStep; i++) {
            if(Gdx.input.isKeyJustPressed(GameHandler.key[i]) || GameHandler.espTouch[i]) {
                //PrintTag.print(TAG, "Key: " + i);
                //PrintTag.print(TAG, "CountsToWin: " + GameHandler.countsToWin);

                //Quick and dirty zone
                if(isInHouse) {
                    if(i == GameHandler.nextStep) {
                        GameHandler.nextStep++;
                        if(GameHandler.nextStep == GameHandler.countsToWin+GameHandler.extraStep) {
                            moveToUpHouse = true;
                            isFinished = true;
                            isInHouse = false;
                            GameSounds.megaWin();
                        }
                        //System.out.println("CTW " + nextPin + " | " + (GameHandler.countsToWin));
                    }
                }
                //Standard zone
                if(i == GameHandler.nextStep) {
                    //PrintTag.print(TAG, "Nextpin: " + GameHandler.nextStep);
                    //Acquiring Measure
                    if (i == 0) {
                        timerMillis.start();
                        PrintTag.print(TAG, "Timer Started");
                        started = true;
                    }
                    else {
                        PrintTag.print(TAG, "Millis " + timerMillis.toString());
                        stepTimeList.add(new TimeMillis(timerMillis.getTime()));
                        timerMillis.restart();
                    }
                    //Game control
                    GameHandler.nextStep++;
                    GameHandler.successfulSteps += 2;
                    GameHandler.touchPins[i] = true;
                    GameSounds.jump();
                }
                //TODO Solve movement bug when pressed a lot of keys
                /*
                When pressed to fast the touch pin is in false,
                need to synchronize with the arrive event
                Possible sol: Use a FIFO to handle and process the incoming data
                 */
                for(int j = 0; j < GameHandler.maxStep; j++) {
                    if(j != i)
                        GameHandler.touchPins[j] = false;
                    GameHandler.espTouch[i] = false;
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
                    sessionTimer.start();
                    y += Gdx.graphics.getDeltaTime()*speed;
                    //Checking if we have reached the position
                    if(y >= positions[animalCounter] - GameHandler.animHysteresis) {
                        animalCounter++;
                        //System.out.println(animalCounter + " | " + (GameHandler.numHouseSteps));
                        if(animalCounter == GameHandler.numHouseSteps) { //Reached the house
                            timer.getStringTime();
                            isInHouse = true;
                            GameSounds.win();
                            isFinished = false;
                        }
                        //System.out.println("CTW " + animalCounter + " | " + (GameHandler.countsToWin));
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

    public void reset() {

        x = (float) (GameHandler.WORLD_WIDTH/2)+5;
        y = 0f;
        Arrays.fill(GameHandler.touchPins, false);
        GameHandler.nextStep = GameHandler.minStep;
        moveToUpHouse = false;
        isFinished = false;
        isInHouse = false;
        animalCounter = 0;
        oneActionFlag = true;
    }

    public void dispose(){
        victorySound.dispose();
    }

    //Getters
    public boolean isFinished() {
        return isFinished;
    }
    public Timer getTimer() {
        return timer;
    }
    public Time getRepTime() {
        return repTime;
    }
    //Setters
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
}
