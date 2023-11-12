package ca.crit.hungryhamster.menus.resume;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.time.Time;

//TODO Resume Menu
public class ResumeMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners{

    }
    /**
     * ---------------------------------------------------------------------
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblCurrentDate;
    private final Label lblPutFileName;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField fieldFileName;
    /**
     * ---------------------------------------------------------------------
     *                               BUTTONS
     * ---------------------------------------------------------------------
     */
    private final TextButton btnSaveFile;
    /**
     * ---------------------------------------------------------------------
     *                              TABLES
     * ---------------------------------------------------------------------
     */
    private final Table infoTable;
    private final Table performanceTable;
    private final Table saveFileTable;
    /**
     * ---------------------------------------------------------------------
     *                               INTERNS
     * ---------------------------------------------------------------------
     */
    private List<Label> playerInfo = new ArrayList<Label>();
    private List<Label> playerPerformance = new ArrayList<Label>();
    private final Dialog dgNoFileName;
    private final Skin shadeSkin;
    public ResumeMenu(Skin skin, Skin shadeSkin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.shadeSkin = shadeSkin;
        this.stage = stage;
        this.titleText = titleText;
        //Labels
        lblPutFileName = new Label("Coloca el nombre del archivo", skin);
        lblCurrentDate = new Label("Fecha: " + GameHandler.currentDate, skin);
        lblInfoConstruct();
        lblPerformanceConstruct();
        //TextField
        fieldFileName = new TextField("", skin);
        fieldFileName.setAlignment(Align.center);
        //Tables
        infoTable = new Table();
        performanceTable = new Table();
        saveFileTable = new Table();
        //Buttons
        btnSaveFile = new TextButton("Guardar Archivo", skin);
        //Dialog
        dgNoFileName = new Dialog("Sample", shadeSkin);
    }

    @Override
    public void uiConstruct() {
        //Listeners
        //Single Objects
        lblCurrentDate.setPosition(GameHandler.NATIVE_RES_WIDTH-100, GameHandler.NATIVE_RES_HEIGHT-20);
        lblPutFileName.setPosition((float) (GameHandler.NATIVE_RES_WIDTH/2)-105, 210);
        //Tables Characteristics
        infoTable.setBackground(skin.getDrawable("dialogDim"));
        performanceTable.setBackground(skin.getDrawable("dialogDim"));
        parentTable.setPosition(0, 20);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
        stage.addActor(lblCurrentDate);
        stage.addActor(lblPutFileName);
    }

    @Override
    protected void tableOrganization() {
        /** Information Table **/
        for(Label lblInfo : playerInfo) {
            infoTable.add(lblInfo).left();
            infoTable.row();
        }
        //infoTable.debug();
        parentTable.add(infoTable).padBottom(35);
        parentTable.row();

        /** Performance Table **/
        for(Label lblInfo : playerPerformance) {
            performanceTable.add(lblInfo).left();
            performanceTable.row();
        }
        //performanceTable.debug();
        parentTable.add(performanceTable).padBottom(60);
        parentTable.row();

        /** Save file Table **/
        saveFileTable.add(fieldFileName).width(300).height(50).padBottom(10);
        saveFileTable.row();
        saveFileTable.add(btnSaveFile).width(130).height(50);

        parentTable.add(saveFileTable);
        parentTable.debug();
    }

    private void lblInfoConstruct() {
        //User Information Labels
        /** DISPLAY INFORMATION **/
        Label lblPlayerName = new Label("Nombre: " + GameHandler.playerName, skin);
        Label lblPlayerID = new Label("ID: " + GameHandler.playerID, skin);
        Label lblPlayerAge = new Label("Edad: " + GameHandler.playerAge, skin);
        Label lblPlayerGender = new Label("Sexo: " + GameHandler.playerGender, skin);
        String hand;
        switch (GameHandler.playerWorkingHand) {
            case GameHandler.RIGHT_HAND:
                hand = "Mano derecha";
                break;
            case GameHandler.LEFT_HAND:
                hand = "Mano izquierda";
                break;
            case GameHandler.BOTH_HANDS:
                hand = "Ambas Manos";
                break;
            default:
                if(GameHandler.DEBUG_MODE == GameHandler.DEBUG_RESUME)
                    hand = "Mano derecha";
                else
                    hand = null;
                break;
        }
        Label lblPlayerWorkingHand = new Label(hand, skin);
        Label lblPlayerLastNumStep = new Label("Maximo escalon: " + GameHandler.playerLastNumSteps, skin);
        //List
        playerInfo.add(lblPlayerName);
        playerInfo.add(lblPlayerAge);
        playerInfo.add(lblPlayerID);
        playerInfo.add(lblPlayerGender);
        playerInfo.add(lblPlayerWorkingHand);
        playerInfo.add(lblPlayerLastNumStep);
    }

    private void lblPerformanceConstruct() {
        /** PERFORMANCE INFORMATION **/
        Label lblLimitSessionTime;
        if(GameHandler.limitSessionTime.equals(new Time(0, 0)))
            lblLimitSessionTime = new Label("Sin tiempo limite en la sesion", skin);
        else
            lblLimitSessionTime = new Label("Tiempo limite de la sesion: " + GameHandler.limitSessionTime, skin);
        Label lblSessionTime = new Label("Tiempo de la sesion: " + GameHandler.sessionTime, skin);
        Label lblMeanTimeStep = new Label("Tiempo promedio por escalon: " + GameHandler.meanSessionTimeStep, skin);
        Label lblSessionCompReps = new Label("Repeticiones completas: " + GameHandler.sessionReps, skin);
        Label lblSessionNoCompReps = new Label("Repeticiones incompletas: " + GameHandler.sessionUncompletedReps, skin);
        //List
        playerPerformance.add(lblLimitSessionTime);
        playerPerformance.add(lblSessionTime);
        playerPerformance.add(lblMeanTimeStep);
        playerPerformance.add(lblSessionCompReps);
        playerPerformance.add(lblSessionNoCompReps);
    }

    private class Listener extends ChangeListener {
        BtnListeners btnListeners;
        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {

        }
    }
}
