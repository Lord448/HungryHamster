package ca.crit.hungryhamster.menus.resume;

import static ca.crit.hungryhamster.menus.resume.ResumeMenu.BtnListeners.BTN_DG_NO;
import static ca.crit.hungryhamster.menus.resume.ResumeMenu.BtnListeners.BTN_DG_YES;
import static ca.crit.hungryhamster.menus.resume.ResumeMenu.BtnListeners.BTN_EXIT;
import static ca.crit.hungryhamster.menus.resume.ResumeMenu.BtnListeners.BTN_RESTART;
import static ca.crit.hungryhamster.menus.resume.ResumeMenu.BtnListeners.BTN_SAVE_FILE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.csv.CSVWriter;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.text.PrintTag;
import ca.crit.hungryhamster.resources.time.Time;

//TODO Resume Menu
public class ResumeMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners{
        BTN_SAVE_FILE,
        BTN_RESTART,
        BTN_EXIT,
        BTN_DG_YES,
        BTN_DG_NO,
    }
    /**
     * ---------------------------------------------------------------------
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblCurrentDate;
    private final Label lblPutFileName;
    private final Label lblDialogMessageSave;
    private final Label lblDialogMessageRestart;
    private final Label lblDialogMessageExit;
    private final Label lblTimeFormat;
    private final Label lblPlayerName;
    private final Label lblPlayerID;
    private final Label lblPlayerAge;
    private final Label lblPlayerGender;
    /**
     * ---------------------------------------------------------------------
     *                          PERFORMANCE LABELS
     * ---------------------------------------------------------------------
     */
    private Label lblLimitSessionTime;
    private Label lblSessionTime;
    private Label lblMeanTimeStep;
    private Label lblSessionCompReps;
    private Label lblSessionNoCompReps;
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
    private final TextButton btnRestart;
    private final TextButton btnExit;
    private final TextButton btnDgSaveYes;
    private final TextButton btnDgSaveNo;
    private final TextButton btnDgResetYes;
    private final TextButton btnDgResetNo;
    private final TextButton btnDgOutYes;
    private final TextButton btnDgOutNo;
    /**
     * ---------------------------------------------------------------------
     *                              TABLES
     * ---------------------------------------------------------------------
     */
    private final Table infoTable;
    private final Table performanceTable;
    private final Table saveFileTable;
    private final Table dialogSaveTable;
    private final Table dialogExitTable;
    private final Table dialogOutTable;
    /**
     * ---------------------------------------------------------------------
     *                               INTERNS
     * ---------------------------------------------------------------------
     */
    private List<Label> playerInfo = new ArrayList<Label>();
    private List<Label> playerPerformance = new ArrayList<Label>();
    private final Dialog dgSaveFile;
    private final Dialog dgRestart;
    private final Dialog dgExit;
    private final Skin shadeSkin;
    private final CheckBox cbSeparateTimeCells;
    private final CSVWriter csvWriter;
    public ResumeMenu(Skin skin, Skin shadeSkin, Stage stage, GameText titleText) {
        TAG = "ResumeMenu";
        this.skin = skin;
        this.shadeSkin = shadeSkin;
        this.stage = stage;
        this.titleText = titleText;
        this.cbSeparateTimeCells = new CheckBox("Tiempo en una sola celda", skin);
        csvWriter = new CSVWriter();
        //Labels
        lblPutFileName = new Label("Nombre del archivo", skin);
        lblCurrentDate = new Label("Fecha: " + GameHandler.currentDate, skin);
        lblDialogMessageSave = new Label("", skin);
        lblDialogMessageSave.setAlignment(Align.center);
        lblDialogMessageRestart = new Label("", skin);
        lblDialogMessageRestart.setAlignment(Align.center);
        lblDialogMessageExit = new Label("", skin);
        lblTimeFormat = new Label(GameHandler.timeFormat, skin);
        lblTimeFormat.setAlignment(Align.center);
        lblDialogMessageExit.setAlignment(Align.center);
        lblPlayerName = new Label("Nombre: " + GameHandler.playerName, skin);
        lblPlayerID = new Label("ID: " + GameHandler.playerID, skin);
        lblPlayerAge = new Label("Edad: " + GameHandler.playerAge, skin);
        lblPlayerGender = new Label("Sexo: " + GameHandler.playerGender, skin);
        lblInfoConstruct();
        lblPerformanceConstruct();
        //TextField
        fieldFileName = new TextField("", skin);
        fieldFileName.setAlignment(Align.center);
        //Tables
        infoTable = new Table();
        performanceTable = new Table();
        saveFileTable = new Table();
        dialogSaveTable = new Table();
        dialogExitTable = new Table();
        dialogOutTable = new Table();
        //Buttons
        btnSaveFile = new TextButton("Guardar Archivo", skin);
        btnRestart = new TextButton("Reiniciar", skin);
        btnExit = new TextButton("Salir", skin);
        btnDgSaveYes = new TextButton("Si", shadeSkin);
        btnDgSaveNo = new TextButton("No", shadeSkin);
        btnDgResetYes = new TextButton("Si", skin);
        btnDgResetNo = new TextButton("No", skin);
        btnDgOutYes = new TextButton("Si", skin);
        btnDgOutNo = new TextButton("No", skin);
        //Dialog
        dgSaveFile = new Dialog("Advertencia", skin);
        dgRestart = new Dialog("Reiniciar", skin);
        dgExit = new Dialog("Salir", skin);
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnSaveFile.addListener(new Listener(BTN_SAVE_FILE));
        btnRestart.addListener(new Listener(BTN_RESTART));
        btnExit.addListener(new Listener(BTN_EXIT));
        btnDgSaveYes.addListener(new Listener(BTN_DG_YES, dgSaveFile));
        btnDgSaveNo.addListener(new Listener(BTN_DG_NO, dgSaveFile));
        btnDgResetYes.addListener(new Listener(BTN_DG_YES, dgRestart));
        btnDgResetNo.addListener(new Listener(BTN_DG_NO, dgRestart));
        btnDgOutYes.addListener(new Listener(BTN_DG_YES, dgExit));
        btnDgOutNo.addListener(new Listener(BTN_DG_NO, dgExit));
        //Single Objects
        dialogConfigs(dgSaveFile);
        dialogConfigs(dgRestart);
        dialogConfigs(dgExit);
        btnRestart.setSize(90, 40);
        btnRestart.setPosition(GameHandler.NATIVE_RES_WIDTH - (btnRestart.getWidth()+20), 20);
        btnExit.setSize(90, 40);
        btnExit.setPosition(btnExit.getWidth()-70, 20);
        lblCurrentDate.setPosition(15, GameHandler.NATIVE_RES_HEIGHT-40);
        lblPutFileName.setPosition((float) (GameHandler.NATIVE_RES_WIDTH/2)-70, 200);

        //Tables Characteristics
        infoTable.setBackground(skin.getDrawable("dialogDim"));
        performanceTable.setBackground(skin.getDrawable("dialogDim"));
        dialogSaveTable.setFillParent(true);
        dialogExitTable.setFillParent(true);
        dialogOutTable.setFillParent(true);
        parentTable.setPosition(0, 0);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
        stage.addActor(btnRestart);
        stage.addActor(btnExit);
        stage.addActor(lblCurrentDate);
        stage.addActor(lblPutFileName);
        stage.addActor(dialogSaveTable);
        stage.addActor(dialogExitTable);
        stage.addActor(dialogOutTable);
    }

    public void setLabelsText()
    {
        if(!GameHandler.limitSessionTime.equals(new Time(0, 0)))
            lblLimitSessionTime.setText("Tiempo limite de la sesion: " + GameHandler.limitSessionTime);
        lblSessionTime.setText("Tiempo de la sesion: " + GameHandler.sessionTime);
        if(GameHandler.avgRepTimeStep == null || GameHandler.avgRepTimeStep.isEmpty())
            lblMeanTimeStep.setText("No hay tiempo promedio por escalon");
        else
            lblMeanTimeStep.setText("Tiempo promedio por escalon: " + GameHandler.avgSessionTimeStep);
        lblSessionCompReps.setText("Repeticiones completas: " + GameHandler.sessionReps);
        lblSessionNoCompReps.setText("Repeticiones incompletas: " + GameHandler.sessionUncompletedReps);

        lblPlayerName.setText("Nombre: " + GameHandler.playerName);
        lblPlayerID.setText("ID: " + GameHandler.playerID);
        lblPlayerAge.setText("Edad: " + GameHandler.playerAge);
        lblPlayerGender.setText("Sexo: " + GameHandler.playerGender);

        try {
            //TODO Try when DB implemented
            /*
            fieldFileName.setText(GameHandler.playerName.replaceAll("\\s", "")+
                    GameHandler.playerID.replaceAll("\\s", "")+
                    GameHandler.filenameF.format(GameHandler.calendar.getTime()));
             */
            if(GameHandler.saveOnDir)
                fieldFileName.setText(GameHandler.playerID.replaceAll("\\s", "") +
                                  "/" +
                                  GameHandler.filenameF.format(GameHandler.calendar.getTime()));
            else
                fieldFileName.setText(GameHandler.playerID.replaceAll("\\s", "") +
                        "-" +
                        GameHandler.filenameF.format(GameHandler.calendar.getTime()));
        }
        catch (NullPointerException ex) {
            fieldFileName.setText("");
        }
    }

    @Override
    protected void tableOrganization() {
        /** Information Table **/
        for(Label lblInfo : playerInfo) {
            infoTable.add(lblInfo).left();
            infoTable.row();
        }
        //infoTable.debug();
        parentTable.add(infoTable).padBottom(20);
        parentTable.row();

        /** Time Format label **/
        parentTable.add(lblTimeFormat).padBottom(20);
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
        saveFileTable.add(fieldFileName).width(300).height(50).padBottom(5);
        saveFileTable.row();
        saveFileTable.add(cbSeparateTimeCells).padBottom(10);
        saveFileTable.row();
        saveFileTable.add(btnSaveFile).width(130).height(50);

        parentTable.add(saveFileTable);

        /** Dialogs Tables **/
        dialogSaveTable.add(dgSaveFile);
        dialogExitTable.add(dgRestart);
        dialogOutTable.add(dgExit);
        //parentTable.debug();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        GameHandler.calendar = Calendar.getInstance();
        lblCurrentDate.setText("Fecha: " + GameHandler.dateF.format(GameHandler.calendar.getTime()) +
                "\n" + "Hora: " + GameHandler.timeF.format(GameHandler.calendar.getTime()));
    }

    private void lblInfoConstruct() {
        //User Information Labels
        /** DISPLAY INFORMATION **/
        lblPlayerName.setText("Nombre: " + GameHandler.playerName);
        lblPlayerID.setText("ID: " + GameHandler.playerID);
        lblPlayerAge.setText("Edad: " + GameHandler.playerAge);
        lblPlayerGender.setText("Sexo: " + GameHandler.playerGender);
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
        if(!GameHandler.limitSessionTime.equals(new Time(0, 0)))
            lblLimitSessionTime = new Label("Tiempo limite de la sesion: " + GameHandler.limitSessionTime, skin);
        else
            lblLimitSessionTime = new Label("Sin tiempo limite en la sesion", skin);
        lblSessionTime = new Label("Tiempo de la sesion: " + GameHandler.sessionTime, skin);
        lblMeanTimeStep = new Label("Tiempo promedio por escalon: " + GameHandler.avgSessionTimeStep, skin);
        lblSessionCompReps = new Label("Repeticiones completas: " + GameHandler.sessionReps, skin);
        lblSessionNoCompReps = new Label("Repeticiones incompletas: " + GameHandler.sessionUncompletedReps, skin);
        //List
        playerPerformance.add(lblLimitSessionTime);
        playerPerformance.add(lblSessionTime);
        playerPerformance.add(lblMeanTimeStep);
        playerPerformance.add(lblSessionCompReps);
        playerPerformance.add(lblSessionNoCompReps);
    }

    private void dialogConfigs(Dialog dialog) {
        if(dialog == null)
            return;
        dialog.setModal(true);
        dialog.setResizable(false);
        if(dialog.getTitleLabel().getText().toString().equals("Advertencia")) {
            dialog.getContentTable().add(lblDialogMessageSave).colspan(2);
            dialog.getContentTable().row();
            dialog.getContentTable().add(btnDgSaveYes).width(50);
            dialog.getContentTable().add(btnDgSaveNo).width(50);
            //dialog.getContentTable().debug();
        }
        else if(dialog.getTitleLabel().getText().toString().equals("Reiniciar")) {
            dialog.getContentTable().add(lblDialogMessageRestart).colspan(2);
            dialog.getContentTable().row();
            dialog.getContentTable().add(btnDgResetYes).width(50);
            dialog.getContentTable().add(btnDgResetNo).width(50);
        }
        else if(dialog.getTitleLabel().getText().toString().equals("Salir")) {
            dialog.getContentTable().add(lblDialogMessageExit).colspan(2);
            dialog.getContentTable().row();
            dialog.getContentTable().add(btnDgOutYes).width(50);
            dialog.getContentTable().add(btnDgOutNo).width(50);
        }
        dialog.setVisible(false);
        dialog.hide();
    }

    private class Listener extends ChangeListener {
        private final BtnListeners btnListeners;
        private final Dialog dialog;
        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
            this.dialog = null;
        }
        public Listener(BtnListeners btnListeners, Dialog dialog) {
            this.btnListeners = btnListeners;
            this.dialog = dialog;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            switch (btnListeners) {
                case BTN_SAVE_FILE:
                    btnSaveFileListener();
                    break;
                case BTN_RESTART:
                    btnRestartListener();
                    break;
                case BTN_EXIT:
                    btnExitListener();
                    break;
                case BTN_DG_YES:
                    btnDgYesListener();
                    break;
                case BTN_DG_NO:
                    btnDgNoListener();
                    break;
            }
        }

        private void btnSaveFileListener() {
            if(fieldFileName.getText().equals("")) {
                lblDialogMessageSave.setText(
                        "El archivo no tiene nombre\n" +
                        "Usar el siguiente?\n"+
                        GameHandler.dataFileName);
            }
            else {
                lblDialogMessageSave.setText(
                        "Esta seguro de querer guardar\n el archivo con el nombre:\n " +
                        fieldFileName.getText() + ".csv");
            }
            if(!dgSaveFile.isVisible())
                dgSaveFile.setVisible(true);
            dgSaveFile.show(stage);
        }

        private void btnRestartListener() {
            if(!dgRestart.isVisible())
                dgRestart.setVisible(true);
            lblDialogMessageRestart.setText("Esta seguro que desea reiniciar?");
            dgRestart.show(stage);
        }

        private void btnExitListener() {
            if(!dgExit.isVisible())
                dgExit.setVisible(true);
            lblDialogMessageExit.setText("Esta seguro que desea salir?");
            dgExit.show(stage);
        }

        private void btnDgYesListener() {
            if(dialog == null)
                return;
            if(dialog.getTitleLabel().getText().toString().equals("Advertencia")) {
                PrintTag.print(TAG, "Generando archivo CSV");
                csvWriter.setSeparateCells(cbSeparateTimeCells.isChecked());
                if(fieldFileName.getText().equals(""))
                    csvWriter.writeCSV("Sample");
                else
                    csvWriter.writeCSV(fieldFileName.getText());
            }
            else if(dialog.getTitleLabel().getText().toString().equals("Reiniciar")) {
                PrintTag.print(TAG, "Reiniciando");
                GameHandler.currentScreen = GameHandler.Screens.MenuScreen;
                GameHandler.disposeAll();
            }
            else if(dialog.getTitleLabel().getText().toString().equals("Salir")) {
                System.exit(0);
            }
            dialog.hide();
            dialog.cancel();
        }

        private void btnDgNoListener() {
            /*
            if(dialog == null)
                return;
            if(dialog.getTitleLabel().getText().toString().equals("Advertencia")) {
                PrintTag.print(TAG, "Corresponde NO Adv");
            }
            else if(dialog.getTitleLabel().getText().toString().equals("Reiniciar")) {
                PrintTag.print(TAG, "Corresponde NO Reinicar");
            }
            else if(dialog.getTitleLabel().getText().toString().equals("Salir")) {
                PrintTag.print(TAG, "Corresponde NO Salir");
            }
             */
            dialog.cancel();
            dialog.hide();
        }

        private void btnDgResetYesListener() {

        }

        private void btnDgResetNoListener() {

        }
    }
}
