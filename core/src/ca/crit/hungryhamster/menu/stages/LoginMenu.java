package ca.crit.hungryhamster.menu.stages;

import static ca.crit.hungryhamster.menu.stages.LoginMenu.BtnListeners.BTN_EXIT;
import static ca.crit.hungryhamster.menu.stages.LoginMenu.BtnListeners.BTN_NEW_PATIENT;
import static ca.crit.hungryhamster.menu.stages.LoginMenu.BtnListeners.BTN_NEXT;
import static ca.crit.hungryhamster.menu.stages.LoginMenu.BtnListeners.BTN_PATIENTS;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Objects;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.GameText;
import ca.crit.hungryhamster.main.PrintTag;
import ca.crit.hungryhamster.menu.MainMenu;

public class LoginMenu extends Menus{
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
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
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblID;
    private final Label lblError;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField idField;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT BUTTONS
     * ---------------------------------------------------------------------
     */
    private final TextButton btnNewPatient;
    private final TextButton btnNext;
    private final TextButton btnExit;
    private final TextButton btnPatients;

    public LoginMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        TAG = "LoginMenu";
        //Labels
        lblID = new Label("No. Carnet:", skin);
        lblError = new Label("", skin);
        //Text Fields
        idField = new TextField("", skin);
        //Buttons
        btnNewPatient = new TextButton("Nuevo paciente", skin);
        btnNext = new TextButton("Siguiente", skin);
        btnExit = new TextButton("Salir", skin);
        btnPatients = new TextButton("Pacientes", skin);
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnNewPatient.addListener(new Listener(BTN_NEW_PATIENT));
        btnNext.addListener(new Listener(BTN_NEXT));
        btnExit.addListener(new Listener(BTN_EXIT));
        btnPatients.addListener(new Listener(BTN_PATIENTS));

        //Tables
        parentTable = new Table();
        parentTable.setFillParent(true);
        parentTable.setPosition(0, 50);
        btnTable = new Table();
        btnTable.setFillParent(true);
        btnTable.setPosition(0, -150);
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
        parentTable.add(lblID).width(95).height(50).padBottom(10).left();
        parentTable.add(idField).width(300).height(50).padBottom(10).left();
        parentTable.row();
        parentTable.add(btnNext).width(150).height(50).colspan(2);
        parentTable.row();
        //parentTable.debug();

        //btnTable interns
        btnTable.add(btnExit).width(btnWidth).height(50);
        btnTable.add(btnPatients).width(btnWidth).height(50).padRight(25).padLeft(25);
        btnTable.add(btnNewPatient).width(btnWidth).height(50).right();
        //buttonsTable.debug();
    }

    private class Listener extends ChangeListener {
        private final BtnListeners btnListeners;
        public Listener(BtnListeners button) {
            this.btnListeners = button;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {
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
            MainMenu.menuState = MenuState.REGISTER;
            lblError.setText("");
        }

        private void btnNextListener() {
            if (!Objects.equals(idField.getText(), "")) {
                //TODO Protect more the variable
                //TODO Search for the ID in database
                GameHandler.playerID = idField.getText();
                PrintTag.Print(TAG, "ID: " + GameHandler.playerID);
                MainMenu.menuState = MenuState.CONFIG;
                lblError.setText("");
            } else {
                lblError.setText("Coloca un No. de Carnet");
            }
        }

        private void btnExitListener() {
            System.exit(0);
        }

        private void btnPatientsListener() {
            MainMenu.menuState = MenuState.PATIENTS;
            lblError.setText("");
        }
    }
}