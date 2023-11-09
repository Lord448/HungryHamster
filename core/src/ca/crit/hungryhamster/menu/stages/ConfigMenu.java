package ca.crit.hungryhamster.menu.stages;

import static ca.crit.hungryhamster.menu.stages.ConfigMenu.BtnListeners.BTN_PLAY;
import static ca.crit.hungryhamster.menu.stages.ConfigMenu.BtnListeners.BTN_RETURN;

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

import ca.crit.hungryhamster.resources.GameText;

public class ConfigMenu extends Menus{
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
    //Labels
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
    //Text Fields
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
    //Buttons
    private final TextButton btnPlay;
    private final TextButton btnReturn;
    private final Button[] btnArrows = new Button[10];
    /**
     * ---------------------------------------------------------------------
     *                             CHECKBOXES
     * ---------------------------------------------------------------------
     */
    //Checkboxes
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
    private final Skin shadeSkin;
    public ConfigMenu(Skin skin, Skin shadeSkin, Stage stage, GameText titleText) {
        final String initStringTime = "1:00";
        this.skin = skin;
        //this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        this.shadeSkin = shadeSkin;
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
        btnPlay.addListener(null); //TODO Implement in a efficient way the class
        btnReturn.addListener(new Listener(BTN_RETURN));
        //Tables characteristics
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
    }

    @Override
    protected void tableOrganization() {
        final int fieldWidth = 200;
        final int fieldHeight = 50;
        final int btnWidth = 150;
        final int btnHeight = 50;
    }

    private class Listener extends ChangeListener {
        BtnListeners btnListeners;
        BtnAcceptListener btnAcceptListener;
        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
            if(btnListeners == BTN_PLAY) {
                btnAcceptListener = new BtnAcceptListener();
            }

        }

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            switch (btnListeners) {
                case BTN_PLAY:
                    break;
                case ARROW_BUTTONS:
                    break;
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
            public BtnAcceptListener() {

            }
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        }

        public BtnAcceptListener btnAcceptListener(){
            return new BtnAcceptListener();
        }

        private void btnReturnListener() {

        }

        private void fieldExtraListener() {

        }

        private void cbFreeTimeListener() {

        }

        private void cbExtraFruitListener() {

        }

        private void cbLeftHandListener() {

        }

        private void cbRightHandListener() {

        }

        private void cbBothHandsListener() {

        }

        private class ArrowsListener extends ChangeListener{

            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        }
    }
}
