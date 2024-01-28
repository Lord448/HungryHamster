package ca.crit.hungryhamster.menus.main.stages;

import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_EXIT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_NEW_PATIENT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_NEXT;
import static ca.crit.hungryhamster.menus.main.stages.LoginMenu.BtnListeners.BTN_PATIENTS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import org.w3c.dom.Text;

import java.util.Objects;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.menus.main.MainMenuScreen;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.text.PrintTag;

public class LoginMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     * BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners {
        BTN_NEW_PATIENT,
        BTN_NEXT,
        BTN_EXIT,
        BTN_PATIENTS
    }

    /**
     * ---------------------------------------------------------------------
     * LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblID;
    private final Label lblError;
    private final Label lblName;
    private final Label lblAge;
    private final Label lblGender;
    /**
     * ---------------------------------------------------------------------
     * TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField idField;
    private final TextField nameField;
    private final TextField ageField;
    //private final TextField genderField; //TODO
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

    public LoginMenu(Skin skin, Stage stage, GameText titleText) {
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
        //Buttons
        btnNewPatient = new TextButton("Nuevo paciente", skin);
        btnNext = new TextButton("Siguiente", skin);
        btnExit = new TextButton("Salir", skin);
        btnPatients = new TextButton("Pacientes", skin);
        btnMale = new TextButton("Masculino", skin);
        btnFemale = new TextButton("Femenino",  skin);
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnNewPatient.addListener(new Listener(BTN_NEW_PATIENT));
        btnNext.addListener(new Listener(BTN_NEXT));
        btnExit.addListener(new Listener(BTN_EXIT));
        btnPatients.addListener(new Listener(BTN_PATIENTS));
        btnPatients.setDisabled(true);

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

        //Table Interns
        parentTable.add(lblError).padBottom(10).colspan(2);
        parentTable.row();
        parentTable.add(lblID).width(95).height(50).padBottom(10).right();
        parentTable.add(idField).width(300).height(50).padBottom(10).left();
        parentTable.row();
        parentTable.add(lblName).width(95).height(50).padBottom(10).right();
        parentTable.add(nameField).width(300).height(50).padBottom(10).left();
        parentTable.row();
        parentTable.add(lblGender).width(95).height(50).padBottom(10).right();
        parentTable.add(btnMale);
        parentTable.add(btnFemale);
        parentTable.row();
        parentTable.add(lblAge).width(95).height(50).padBottom(10).right();
        parentTable.add(ageField).width(100).height(50).padBottom(10).left();
        parentTable.row();
        parentTable.add(btnNext).width(150).height(50).colspan(2);
        parentTable.row();
        //parentTable.debug();

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
            if (!Objects.equals(idField.getText(), "")) {
                if(GameHandler.playerGender == null)
                //Checking the nameField
                for(int i = 0; i < nameField.getText().length(); i++) {
                    char charToCheck = nameField.getText().charAt(i);
                    if(charToCheck > 0x30 && charToCheck < 0x39) {
                        lblError.setText("No se pueden colocar numeros en el nombre!");
                        return;
                    }
                }
                GameHandler.playerName = nameField.getText();
                GameHandler.playerID = idField.getText();

                PrintTag.print(TAG, "ID: " + GameHandler.playerID);
                MainMenuScreen.mainMenuState = MainMenuState.CONFIG;
                Gdx.input.setOnscreenKeyboardVisible(false);
                lblError.setText("");
            } else {
                lblError.setText("Coloca un No. de Carnet");
            }
        }

        private void btnExitListener() {
            System.exit(0);
        }

        private void btnPatientsListener() {
            MainMenuScreen.mainMenuState = MainMenuState.PATIENTS;
            lblError.setText("");
        }
    }
}