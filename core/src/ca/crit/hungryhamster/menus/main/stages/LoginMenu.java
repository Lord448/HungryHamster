package ca.crit.hungryhamster.menus.main.stages;

import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_ARR_LOW;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_ARR_UP;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_EXIT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_FEMALE;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_MALE;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_NEW_PATIENT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_NEXT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_PATIENTS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.Objects;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.menus.main.MainMenuScreen;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.text.PrintTag;

public class LoginMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                          BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners {
        BTN_NEW_PATIENT,
        BTN_NEXT,
        BTN_EXIT,
        BTN_PATIENTS,
        BTN_MALE,
        BTN_FEMALE,
        BTN_ARR_UP,
        BTN_ARR_LOW
    }

    /**
     * ---------------------------------------------------------------------
     *                               LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblID;
    private final Label lblError;
    private final Label lblName;
    private final Label lblAge;
    private final Label lblGender;
    /**
     * ---------------------------------------------------------------------
     *                            TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField idField;
    private final TextField nameField;
    private final TextField ageField;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT BUTTONS
     * ---------------------------------------------------------------------
     */
    private final TextButton btnNewPatient;
    private final TextButton btnNext;
    private final TextButton btnExit;
    private final TextButton btnPatients;
    private final TextButton btnMale;
    private final TextButton btnFemale;
    /**
     * ---------------------------------------------------------------------
     *                                INTERNS
     * ---------------------------------------------------------------------
     */
    private final Button[] btnArrows = new Button[2];

    public LoginMenu(Skin skin, Skin shadeSkin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        TAG = "LoginMenu";
        //Labels
        lblID = new Label("No. Carnet:", skin);
        lblName = new Label("Nombre:", skin);
        lblAge = new Label("Edad:", skin);
        lblGender = new Label("Sexo:", skin);
        lblError = new Label("", skin);
        //Text Fields
        idField = new TextField("", skin);
        nameField = new TextField("", skin);
        ageField = new TextField("", skin);
        ageField.setAlignment(Align.center);
        ageField.setText("10");
        //Buttons
        btnNewPatient = new TextButton("Nuevo paciente", skin);
        btnNext = new TextButton("Siguiente", skin);
        btnExit = new TextButton("Salir", skin);
        btnPatients = new TextButton("Pacientes", skin);
        btnMale = new TextButton("Masculino", skin, "toggle");
        btnFemale = new TextButton("Femenino",  skin, "toggle");
        //Construct btn arrows
        for(int i = 0; i < btnArrows.length; i+=2) {
            btnArrows[i] = new Button(shadeSkin, "left");
            btnArrows[i+1] = new Button(shadeSkin, "right");
        }
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnNewPatient.addListener(new Listener(BTN_NEW_PATIENT));
        btnNewPatient.setDisabled(true); //TODO Enable when add DB
        btnNext.addListener(new Listener(BTN_NEXT));
        btnExit.addListener(new Listener(BTN_EXIT));
        btnMale.addListener(new Listener(BTN_MALE));
        btnFemale.addListener(new Listener(BTN_FEMALE));
        btnPatients.addListener(new Listener(BTN_PATIENTS));
        btnPatients.setDisabled(true); //TODO Enable when add DB

        //Listeners for arrows
        for(int i = 0; i < btnArrows.length; i+=2) {
            btnArrows[i].addListener(new Listener(BTN_ARR_UP));
            btnArrows[i+1].addListener(new Listener(BTN_ARR_LOW));
        }
        //Tables
        parentTable.setPosition(0, 10);
        btnTable.setPosition(0, -230);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
        stage.addActor(btnTable);
    }

    @Override
    protected void tableOrganization() {
        final int btnWidth = 130;

        Table genderTable = new Table();
        Table ageTable = new Table();
        Table arrowsTable = new Table();

        //Table Interns
        parentTable.add(lblError).colspan(2);
        parentTable.row();
        parentTable.add(lblID).width(95).height(50).right();
        parentTable.add(idField).width(300).height(50).left();
        parentTable.row();
        parentTable.add(lblName).width(95).height(50).right();
        parentTable.add(nameField).width(300).height(50).left();
        parentTable.row();
        parentTable.add(lblGender).width(95).height(50).right();
            genderTable.add(btnMale).width(100).height(40).padRight(30);
            genderTable.add(btnFemale).width(100).height(40);
        parentTable.add(genderTable);
        parentTable.row().padBottom(10);
        parentTable.add(lblAge).width(95).height(50).center();
            ageTable.add(ageField).width(150).height(50).left();
                arrowsTable.add(btnArrows[0]);
                arrowsTable.row();
                arrowsTable.add(btnArrows[1]);
            ageTable.add(arrowsTable);
        parentTable.add(ageTable).left();
        parentTable.row();
        parentTable.add(btnNext).width(150).height(50).colspan(2);
        parentTable.row();

        //parentTable.debug();
        //arrowsTable.debug();
        //ageTable.debug();

        //btnTable interns
        btnTable.add(btnExit).width(btnWidth).height(50);
        btnTable.add(btnPatients).width(btnWidth).height(50).padRight(25).padLeft(25);
        btnTable.add(btnNewPatient).width(btnWidth).height(50).right();
        //btnTable.debug();
    }

    private class Listener extends ChangeListener {
        private final BtnListeners btnListeners;
        public Listener(BtnListeners button) {
            this.btnListeners = button;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            lblError.setText("");
            Gdx.input.setOnscreenKeyboardVisible(false);
            switch (btnListeners) {
                case BTN_NEW_PATIENT:
                    btnNewPatientListener();
                    break;
                case BTN_NEXT:
                    btnNextListener();
                    break;
                case BTN_EXIT:
                    btnExitListener();
                    break;
                case BTN_PATIENTS:
                    btnPatientsListener();
                    break;
                case BTN_MALE:
                    btnMaleListener();
                    break;
                case BTN_FEMALE:
                    btnFemaleListener();
                    break;
                case BTN_ARR_UP:
                    btnUpperArrowListener();
                    break;
                case BTN_ARR_LOW:
                    btnLowerArrowListener();
                    break;
            }
        }
        /**
         * ---------------------------------------------------------------------
         *                             LISTENERS
         * ---------------------------------------------------------------------
         */

        private void btnNewPatientListener() {
            MainMenuScreen.mainMenuState = MainMenuState.REGISTER;
            lblError.setText("");
        }

        private void btnNextListener() {
            //Checking the ID
            if (!Objects.equals(idField.getText(), "")) {
                GameHandler.playerID = idField.getText();
            }
            else {
                lblError.setText("Coloca un No. de Carnet");
                return;
            }
            //Checking the Name
            if (!Objects.equals(nameField.getText(), "")) {
                //Checking that the name does not have any number
                for(int i = 0; i < nameField.getText().length(); i++) {
                    char charToCheck = nameField.getText().charAt(i);
                    if(charToCheck > 0x30 && charToCheck < 0x39) {
                        lblError.setText("No se pueden colocar numeros en el nombre!");
                        return;
                    }
                }
                GameHandler.playerName = nameField.getText();
            }
            else {
                lblError.setText("Coloca un nombre");
                return;
            }
            //Checking patient gender
            if(btnMale.isChecked()) {
                GameHandler.playerGender = "Male";
            }
            else if (btnFemale.isChecked()) {
                GameHandler.playerGender = "Female";
            }
            else {
                lblError.setText("Necesita colocar el sexo del paciente");
                return;
            }
            //Checking the age
            try {
                GameHandler.playerAge = Integer.parseInt(ageField.getText());
                if (GameHandler.playerAge >= 20) {
                    lblError.setText("No se puede registrar un paciente mayor a 20 anos");
                    return;
                }
                else if(GameHandler.playerAge <= 0) {
                    lblError.setText("La edad no puede ser menor o igual a 0");
                    return;
                }
            }
            catch (NumberFormatException ex) {
                lblError.setText("La edad tiene que ser un numero");
                ageField.setText("10");
                return;
            }

            MainMenuScreen.mainMenuState = MainMenuState.CONFIG;
            Gdx.input.setOnscreenKeyboardVisible(false);
            //Returning to initial conditions
            lblError.setText("");
            btnMale.setChecked(false);
            btnFemale.setChecked(false);
        }

        private void btnExitListener() {
            System.exit(0);
        }

        private void btnPatientsListener() {
            MainMenuScreen.mainMenuState = MainMenuState.PATIENTS;
            lblError.setText("");
        }
        private void btnMaleListener() {
            if(btnMale.isChecked())
                btnFemale.setChecked(false);
        }
        private void btnFemaleListener() {
            if (btnFemale.isChecked())
                btnMale.setChecked(false);
        }
        private void btnUpperArrowListener() {
            try {
                int counter = Integer.parseInt(ageField.getText().trim());
                counter++;
                if (counter < 20) {
                    ageField.setText(String.valueOf(counter));
                }
                else {
                    lblError.setText("No se puede registrar un paciente mayor a 20 anos");
                }
            }
            catch (NumberFormatException ex) {
                lblError.setText("La edad tiene que ser un numero");
                ageField.setText("10");
            }
        }

        private void btnLowerArrowListener() {
            try {
                int counter = Integer.parseInt(ageField.getText().trim());
                counter--;
                if (counter > 0) {
                    ageField.setText(String.valueOf(counter));
                }
                else {
                    lblError.setText("La edad no puede ser menor o igual a 0");
                }
            }
            catch (NumberFormatException ex) {
                lblError.setText("La edad tiene que ser un numero");
                ageField.setText("10");
            }
        }
    }
}