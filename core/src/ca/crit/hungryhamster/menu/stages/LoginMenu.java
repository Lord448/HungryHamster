package ca.crit.hungryhamster.menu.stages;

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
    public enum btn{
        btnNewPatient,
        btnNext,
        btnExit,
        btnPatients
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
    private Table table;
    private Table btnTable;

    public LoginMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        TAG = "LoginMenu";
        lblID = new Label("No. Carnet:", skin);
        lblError = new Label("", skin);
        idField = new TextField("", skin);
        btnNewPatient = new TextButton("Nuevo paciente", skin);
        btnNext = new TextButton("Siguiente", skin);
        btnExit = new TextButton("Salir", skin);
        btnPatients = new TextButton("Pacientes", skin);
    }
    @Override
    public void uiConstruct() {
        //Listeners
        btnNewPatient.addListener(new Listener(btn.btnNewPatient));
        btnNext.addListener(new Listener(btn.btnNext));
        btnExit.addListener(new Listener(btn.btnExit));
        btnPatients.addListener(new Listener(btn.btnPatients));

        //Tables
        table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 50);
        btnTable = new Table();
        btnTable.setFillParent(true);
        btnTable.setPosition(0, -150);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(table);
        stage.addActor(btnTable);
    }
    @Override
    protected void tableOrganization() {
        final int btnWidth = 130;

        //Table Interns
        table.add(lblError).padBottom(10).colspan(2);
        table.row();
        table.add(lblID).width(95).height(50).padBottom(10).left();
        table.add(idField).width(300).height(50).padBottom(10).left();
        table.row();
        table.add(btnNext).width(150).height(50).colspan(2);
        table.row();
        //table.debug();

        //btnTable interns
        btnTable.add(btnExit).width(btnWidth).height(50);
        btnTable.add(btnPatients).width(btnWidth).height(50).padRight(25).padLeft(25);
        btnTable.add(btnNewPatient).width(btnWidth).height(50).right();
        //buttonsTable.debug();
    }

    private class Listener extends ChangeListener {
        private final btn button;
        public Listener(btn button) {
            this.button = button;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            switch (button) {
                case btnNewPatient:
                    btnNewPatientListener();
                    break;
                case btnNext:
                    btnNextListener();
                    break;
                case btnExit:
                    btnExitListener();
                    break;
                case btnPatients:
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