package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import jdk.tools.jmod.Main;

public class LoginMenu extends Menus{
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
    /**
     * ---------------------------------------------------------------------
     *                           CHANGE LISTENERS
     * ---------------------------------------------------------------------
     */
    private btnNextListener btnNextListener; //TODO It results null
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
        final int btnWidth = 130;
        //Listeners
        btnNewPatient.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenu.menuState = MenuState.REGISTER;
            }
        });
        /*
        btnNext.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!Objects.equals(idField.getText(), "")) {
                    //TODO Protect more the variable
                    //TODO Search for the ID in database
                    GameHandler.playerID = idField.getText();
                    PrintTag.Print(TAG, "ID: " + GameHandler.playerID);
                    MainMenu.menuState = MenuState.CONFIG;
                }
                else {
                    lblError.setText("Coloca un No. de Carnet");
                }
            }
        });
         */
        btnNext.addListener(btnNextListener);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });
        btnPatients.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenu.menuState = MenuState.PATIENTS;
            }
        });
        //Table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, 50);
        Table btnTable = new Table();
        btnTable.setFillParent(true);
        btnTable.setPosition(0, -150);

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

        //Stage
        stage.addActor(table);
        stage.addActor(btnTable);
    }

    private class btnNextListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            if(!Objects.equals(idField.getText(), "")) {
                //TODO Protect more the variable
                //TODO Search for the ID in database
                GameHandler.playerID = idField.getText();
                PrintTag.Print(TAG, "ID: " + GameHandler.playerID);
                MainMenu.menuState = MenuState.CONFIG;
            }
            else {
                lblError.setText("Coloca un No. de Carnet");
            }
        }
    }
}
