package ca.crit.hungryhamster.menus.main.stages;

import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.BTN_PLAY;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.BTN_RETURN;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.CB_BOTH_HANDS;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.CB_EXTRA_FRUIT;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.CB_FREE_TIME;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.CB_LEFT_HAND;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.CB_RIGHT_HAND;
import static ca.crit.hungryhamster.menus.main.stages.ConfigMenu.BtnListeners.FIELD_EXTRA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.main.MainMenuScreen;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.time.Time;
import ca.crit.hungryhamster.resources.time.TimeFormatException;

public class ConfigMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners {
        BTN_PLAY,
        ARROW_BUTTONS,
        BTN_RETURN,
        FIELD_EXTRA,
        CB_FREE_TIME,
        CB_EXTRA_FRUIT,
        CB_LEFT_HAND,
        CB_RIGHT_HAND,
        CB_BOTH_HANDS,
    }
    /**
     * ---------------------------------------------------------------------
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblMaxStep;
    private final Label lblMinStep;
    private final Label lblTime;
    private final Label lblReps;
    private final Label lblError;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField fieldMaxStep;
    private final TextField fieldMinStep;
    private final TextField fieldTime;
    private final TextField fieldReps;
    private final TextField fieldExtra;
    /**
     * ---------------------------------------------------------------------
     *                               BUTTONS
     * ---------------------------------------------------------------------
     */
    private final TextButton btnPlay;
    private final TextButton btnReturn;
    private final Button[] btnArrows = new Button[10];
    /**
     * ---------------------------------------------------------------------
     *                             CHECKBOXES
     * ---------------------------------------------------------------------
     */
    private final CheckBox cbExtraFruit;
    private final CheckBox cbRightHand;
    private final CheckBox cbLeftHand;
    private final CheckBox cbBothHands;
    private final CheckBox cbFreeTime;
    /**
     * ---------------------------------------------------------------------
     *                              TABLES
     * ---------------------------------------------------------------------
     */
    private final Table upperArrowsTable;
    private final Table lowerArrowsTable;
    private final Table timeArrowsTable;
    private final Table extraArrowsTable;
    private final Table repsArrowsTable;
    private final Table handCheckTable;
    private final Table timeTable;
    /**
     * ---------------------------------------------------------------------
     *                              INTERNS
     * ---------------------------------------------------------------------
     */
    private final String initStringTime = "1:00";
    public ConfigMenu(Skin skin, Skin shadeSkin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        //Labels
        lblMaxStep = new Label("Escalon superior", skin);
        lblMinStep = new Label("Escalon inferior", skin);
        lblTime = new Label("Tiempo", skin);
        lblReps = new Label("Repeticiones", skin);
        lblError = new Label("", skin);
        //Text Fields Constructions
        fieldMaxStep = new TextField("10", skin);
        fieldMinStep = new TextField("0", skin);
        fieldTime = new TextField(initStringTime, skin);
        fieldReps = new TextField("Libre", skin);
        fieldExtra = new TextField("", skin);
        //Text Fields configuration
        fieldMaxStep.setAlignment(Align.center);
        fieldMinStep.setAlignment(Align.center);
        fieldTime.setAlignment(Align.center);
        fieldReps.setAlignment(Align.center);
        fieldExtra.setAlignment(Align.center);
        //Buttons
        btnPlay = new TextButton("Jugar", skin);
        btnReturn = new TextButton("Regresar", skin);
        //Construct for btnArrows
        for(int i = 0; i < btnArrows.length; i+=2) {
            btnArrows[i] = new Button(shadeSkin, "left");
            btnArrows[i+1] = new Button(shadeSkin, "right");
        }
        //Checkboxes
        cbExtraFruit = new CheckBox("Fruta extra", shadeSkin);
        cbRightHand = new CheckBox("Mano derecha", shadeSkin);
        cbLeftHand = new CheckBox("Mano izquierda", shadeSkin);
        cbBothHands = new CheckBox("Ambas manos", shadeSkin);
        cbFreeTime = new CheckBox("Libre", shadeSkin);
        //Tables
        upperArrowsTable = new Table();
        lowerArrowsTable = new Table();
        timeArrowsTable = new Table();
        extraArrowsTable = new Table();
        repsArrowsTable = new Table();
        handCheckTable = new Table();
        timeTable = new Table();
    }
    @Override
    public void uiConstruct() {
        //Listeners
        btnPlay.addListener(new Listener(BTN_PLAY).btnAcceptListener());
        makeArrowsListeners();
        btnReturn.addListener(new Listener(BTN_RETURN));
        fieldExtra.addListener(new Listener(FIELD_EXTRA));
        cbFreeTime.addListener(new Listener(CB_FREE_TIME));
        cbExtraFruit.addListener(new Listener(CB_EXTRA_FRUIT));
        cbLeftHand.addListener(new Listener(CB_LEFT_HAND));
        cbRightHand.addListener(new Listener(CB_RIGHT_HAND));
        cbBothHands.addListener(new Listener(CB_BOTH_HANDS));
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
    }

    @Override
    protected void tableOrganization() {
        final int fieldWidth = 200;
        final int fieldHeight = 50;
        final int btnWidth = 150;
        final int btnHeight = 50;
        //Table Interns
        parentTable.add(lblError).padBottom(0).colspan(3);
        parentTable.row();
        parentTable.add(lblMaxStep);
        parentTable.add(new Actor()); //Not null member for blank space
        timeTable.add(lblTime).padRight(25);
        timeTable.add(cbFreeTime);
        parentTable.add(timeTable).padLeft(70);
        parentTable.row();
        parentTable.add(fieldMaxStep).width(fieldWidth).height(fieldHeight);
            upperArrowsTable.add(btnArrows[0]); //Up
            upperArrowsTable.row();
            upperArrowsTable.add(btnArrows[1]); //Down
        parentTable.add(upperArrowsTable).left();
        parentTable.add(fieldTime).width(fieldWidth).height(fieldHeight).left();
            timeArrowsTable.add(btnArrows[2]); //Up
            timeArrowsTable.row();
            timeArrowsTable.add(btnArrows[3]); //Down
        parentTable.add(timeArrowsTable).left();
        parentTable.row();
        parentTable.add(lblMinStep);
        parentTable.add(new Actor()); //Not null member for blank space
        parentTable.add(cbExtraFruit).padLeft(10);
        parentTable.row();
        parentTable.add(fieldMinStep).width(fieldWidth).height(fieldHeight);
            lowerArrowsTable.add(btnArrows[4]); //Up
            lowerArrowsTable.row();
            lowerArrowsTable.add(btnArrows[5]); //Down
        parentTable.add(lowerArrowsTable).left().padRight(10);
        parentTable.add(fieldExtra).width(fieldWidth).height(fieldHeight).left();
            extraArrowsTable.add(btnArrows[6]);
            extraArrowsTable.row();
            extraArrowsTable.add(btnArrows[7]);
        parentTable.add(extraArrowsTable).left();
        parentTable.row();
        parentTable.add(lblReps);
        parentTable.row().padBottom(20);
        parentTable.add(fieldReps).width(fieldWidth).height(fieldHeight);
            repsArrowsTable.add(btnArrows[8]);
            repsArrowsTable.row();
            repsArrowsTable.add(btnArrows[9]);
        parentTable.add(repsArrowsTable).left();
        handCheckTable.add(cbLeftHand);
        handCheckTable.row();
        handCheckTable.add(cbRightHand).padTop(10).padLeft(5);
        handCheckTable.row();
        handCheckTable.add(cbBothHands).padTop(10).center().colspan(2);
        parentTable.add(handCheckTable);
        parentTable.row().padBottom(20);
        parentTable.add(btnPlay).width(btnWidth).height(btnHeight).colspan(3);
        parentTable.row();
        parentTable.add(btnReturn).width(btnWidth).height(btnHeight).left();
        //parentTable.debug();
    }

    public void reset() {
        fieldMaxStep.setText("10");
        fieldMinStep.setText("0");
        fieldReps.setText("Libre");
        fieldExtra.setText("");
        cbExtraFruit.setChecked(false);
        cbRightHand.setChecked(false);
        cbLeftHand.setChecked(false);
        cbBothHands.setChecked(false);
    }

    private class Listener extends ChangeListener {
        BtnListeners btnListeners;
        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
        }

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            Gdx.input.setOnscreenKeyboardVisible(false);
            switch (btnListeners) {
                case BTN_RETURN:
                    btnReturnListener();
                    break;
                case FIELD_EXTRA:
                    fieldExtraListener();
                    break;
                case CB_FREE_TIME:
                    cbFreeTimeListener();
                    break;
                case CB_EXTRA_FRUIT:
                    cbExtraFruitListener();
                    break;
                case CB_LEFT_HAND:
                    cbLeftHandListener();
                    break;
                case CB_RIGHT_HAND:
                    cbRightHandListener();
                    break;
                case CB_BOTH_HANDS:
                    cbBothHandsListener();
                    break;
            }
        }

        protected class BtnAcceptListener extends ChangeListener{
            public BtnAcceptListener() {}
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                if(!fieldTime.getText().equalsIgnoreCase("") && !fieldTime.getText().equalsIgnoreCase("")) {
                    try {
                        boolean isAllCBChecked = cbBothHands.isChecked() || cbLeftHand.isChecked() || cbRightHand.isChecked();
                        boolean notReachingMaxSteps = true;

                        GameHandler.maxStep = Integer.parseInt(fieldMaxStep.getText().trim());
                        GameHandler.minStep = Integer.parseInt(fieldMinStep.getText().trim());
                        GameHandler.numHouseSteps = GameHandler.maxStep - GameHandler.minStep;
                        if(fieldTime.getText().equals("Libre"))
                            GameHandler.limitSessionTime = new Time(0, 0);
                        else
                            GameHandler.limitSessionTime = Time.parseTime(fieldTime.getText().trim());
                        if(fieldReps.getText().equalsIgnoreCase("Libre"))
                            GameHandler.sessionReps = 0;
                        else
                            GameHandler.sessionReps = Integer.parseInt(fieldReps.getText().trim());
                        if(!fieldExtra.getText().equals("")) {
                            GameHandler.extraStep = Integer.parseInt(fieldExtra.getText().trim());
                        }
                        GameHandler.countsToWin = GameHandler.numHouseSteps + GameHandler.extraStep;
                        if(cbExtraFruit.isChecked())
                            notReachingMaxSteps = (GameHandler.maxStep + GameHandler.extraStep) < GameHandler.LADDER_MAX_STEPS;
                        //Start the game
                        if(fieldCheck(fieldMaxStep, fieldMinStep, lblError) && isAllCBChecked && notReachingMaxSteps)
                            GameHandler.currentScreen = GameHandler.Screens.GameScreen;
                        else if(!isAllCBChecked)
                            lblError.setText("Seleccione que manos se usaran");
                        else if(!notReachingMaxSteps)
                            lblError.setText("No puede colocar tantos escalones extras");
                    }
                    catch (NumberFormatException ex) {
                        lblError.setText("Inserte numeros porfavor");
                    }
                    catch (TimeFormatException e) {
                        if(e.getExId() == TimeFormatException.ExId.NEGATIVE_TIME)
                            lblError.setText("No puede insertar numeros negativos");
                        else if(e.getExId() == TimeFormatException.ExId.SECONDS_LIMIT)
                            lblError.setText("No puede insertar más de 59 segundos");
                        else if(e.getExId() == TimeFormatException.ExId.GENERIC) {
                            if(e.getMessage().equals("Cannot parse a null string") || e.getMessage().equals("Cannot parse an empty string"))
                                lblError.setText("Coloque un tiempo porfavor");
                            else if(e.getMessage().equals("Have to put a 0 before the delimiter"))
                                lblError.setText("Coloque un 0 antes de \":\"");
                            else if(e.getMessage().equals("Missing the delimiter (:)"))
                                lblError.setText("No se olvide de colocar el \":\"");
                            else if(e.getMessage().equals("Cannot parse a no number time"))
                                lblError.setText("Coloque solo numeros porfavor");
                        }
                    }

                }
            }
        }
        public BtnAcceptListener btnAcceptListener(){
            return new BtnAcceptListener();
        }

        private void btnReturnListener() {
            MainMenuScreen.mainMenuState = MainMenuState.LOGIN;
        }

        private void fieldExtraListener() {
            cbExtraFruit.setChecked(!fieldExtra.getText().equals(""));
        }

        private void cbFreeTimeListener() {
            if(cbFreeTime.isChecked())
                fieldTime.setText("Libre");
            else
                fieldTime.setText(initStringTime);
        }

        private void cbExtraFruitListener() {
            if(cbExtraFruit.isChecked()) {
                fieldExtra.setDisabled(false);
                if(fieldExtra.getText().toLowerCase().trim().equals("") || fieldExtra.getText().equals("0")) {
                    fieldExtra.setText("1");
                }
            }
            else {
                fieldExtra.setText("");
                fieldExtra.setDisabled(true);
            }
        }

        private void cbLeftHandListener() {
            if(cbLeftHand.isChecked()) {
                if(cbRightHand.isChecked())
                    cbRightHand.setChecked(false);
                else if(cbBothHands.isChecked())
                    cbBothHands.setChecked(false);
                GameHandler.playerWorkingHand = GameHandler.LEFT_HAND;
            }
        }

        private void cbRightHandListener() {
            if(cbRightHand.isChecked()) {
                if(cbLeftHand.isChecked())
                    cbLeftHand.setChecked(false);
                else if(cbBothHands.isChecked())
                    cbBothHands.setChecked(false);
                GameHandler.playerWorkingHand = GameHandler.RIGHT_HAND;
            }
        }

        private void cbBothHandsListener() {
            if(cbBothHands.isChecked()) {
                if (cbLeftHand.isChecked())
                    cbLeftHand.setChecked(false);
                else if (cbRightHand.isChecked())
                    cbRightHand.setChecked(false);
                GameHandler.playerWorkingHand = GameHandler.BOTH_HANDS;
            }
        }
    }

    private void makeArrowsListeners() {
        //Making the listeners of the arrow buttons
        for(int i = 0; i < btnArrows.length; i+=2) {
            int finalI = i; //In order to avoid memory leaks
            btnArrows[i].addListener(new ChangeListener() { //Up
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int counter;
                    Time time;
                    try {
                        if(finalI < 2) {
                            counter = Integer.parseInt(fieldMaxStep.getText().trim());
                            counter++;
                            if (counter >= GameHandler.NUMBER_OF_STEPS_IN_LADDER)
                                counter = GameHandler.NUMBER_OF_STEPS_IN_LADDER;
                            fieldMaxStep.setText(String.valueOf(counter));
                        }
                        else if (finalI < 4) {
                            if(fieldTime.getText().equals("Libre")) {
                                time = new Time(0, 1);
                                cbFreeTime.setChecked(false);
                            }
                            else {
                                time = Time.parseTime(fieldTime.getText().trim());
                                time.addSecond();
                            }
                            fieldTime.setText(time.toString());
                        }
                        else if (finalI < 6){
                            counter = Integer.parseInt(fieldMinStep.getText().trim());
                            counter++;
                            fieldMinStep.setText(String.valueOf(counter));
                        }
                        else if (finalI < 8){
                            counter = Integer.parseInt(fieldExtra.getText().trim());
                            counter++;
                            if(cbExtraFruit.isChecked())
                                fieldExtra.setText(String.valueOf(counter));
                        }
                        else {
                            if(fieldReps.getText().trim().equalsIgnoreCase("Libre"))
                                counter = 0;
                            else
                                counter = Integer.parseInt(fieldReps.getText().trim());
                            counter++;
                            fieldReps.setText(String.valueOf(counter));
                        }
                        lblError.setText("");
                    }
                    catch (NumberFormatException exception) {
                        lblError.setText("Please select a number");
                    }
                    catch (TimeFormatException e) {
                        fieldTime.setText(initStringTime);
                    }
                    fieldCheck(fieldMaxStep, fieldMinStep, lblError);
                }
            });
            btnArrows[i+1].addListener(new ChangeListener() { //Down
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int counter;
                    Time time;
                    try {
                        if(finalI < 2) {
                            counter = Integer.parseInt(fieldMaxStep.getText().trim());
                            counter--;
                            if(counter <= 1)
                                counter = 1;
                            else if(counter >= GameHandler.NUMBER_OF_STEPS_IN_LADDER)
                                counter = GameHandler.NUMBER_OF_STEPS_IN_LADDER;
                            fieldMaxStep.setText(String.valueOf(counter));

                        }
                        else if (finalI < 4) {
                            time = Time.parseTime(fieldTime.getText().trim());
                            time.subtractSecond();
                            if(time.equals(new Time(0, 0))) {
                                fieldTime.setText("Libre");
                                cbFreeTime.setChecked(true);
                            }
                            else
                                fieldTime.setText(String.valueOf(time.toString()));
                        }
                        else if (finalI < 6){
                            counter = Integer.parseInt(fieldMinStep.getText().trim());
                            counter--;
                            if(counter <= 0)
                                counter = 0;
                            fieldMinStep.setText(String.valueOf(counter));
                        }
                        else if(finalI < 8){
                            counter = Integer.parseInt(fieldExtra.getText().trim());
                            counter--;
                            if(counter <= 1)
                                counter = 1;
                            if(cbExtraFruit.isChecked())
                                fieldExtra.setText(String.valueOf(counter));
                        }
                        else {
                            if(fieldReps.getText().trim().equalsIgnoreCase("Libre"))
                                counter = 0;
                            else
                                counter = Integer.parseInt(fieldReps.getText().trim());
                            counter--;
                            if (counter <= 0) {
                                fieldReps.setText("Libre");
                            }
                            else
                                fieldReps.setText(String.valueOf(counter));
                        }
                        lblError.setText("");
                    }
                    catch (NumberFormatException exception) {
                        lblError.setText("Porfavor coloque un numero valido");
                    }
                    catch (TimeFormatException e) {
                        fieldTime.setText(initStringTime);
                    }
                    fieldCheck(fieldMaxStep, fieldMinStep, lblError);
                }
            });
        }
    }

    private boolean fieldCheck(TextField fieldMaxStep, TextField fieldMinStep, Label lblError) {
        int fieldMaxCounts, fieldMinCounts;
        try {
            fieldMaxCounts = Integer.parseInt(fieldMaxStep.getText().trim());
            fieldMinCounts = Integer.parseInt(fieldMinStep.getText().trim());

            if(fieldMaxCounts > GameHandler.NUMBER_OF_STEPS_IN_LADDER) {
                lblError.setText("Escoja un valor menor a 15");
                return false;
            }
            if(fieldMinCounts >= fieldMaxCounts) {
                lblError.setText("El escalon inferior no puede ser mayor o igual al superior");
                fieldMinCounts = fieldMaxCounts - 1;
                if(fieldMinCounts <= 0)
                    fieldMinCounts = 0;

                fieldMinStep.setText(String.valueOf(fieldMinCounts));
                return false;
            }
            else
                return true;
        }
        catch (NumberFormatException exception) {
            lblError.setText("Porfavor pon numeros");
            return false;
        }
    }

}
