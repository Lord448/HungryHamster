package ca.crit.hungryhamster.menus.main.stages;

import static ca.crit.hungryhamster.menus.main.stages.RegisterMenu.BtnListeners.BTN_ACCEPT;
import static ca.crit.hungryhamster.menus.main.stages.RegisterMenu.BtnListeners.BTN_RETURN;
import static ca.crit.hungryhamster.menus.main.stages.RegisterMenu.BtnListeners.BTN_MALE;
import static ca.crit.hungryhamster.menus.main.stages.RegisterMenu.BtnListeners.BTN_FEMALE;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.menus.main.MainMenu;

public class RegisterMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners {
        BTN_ACCEPT,
        BTN_RETURN,
        BTN_MALE,
        BTN_FEMALE
    }
    /**
     * ---------------------------------------------------------------------
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    private final Label lblName;
    private final Label lblAge;
    private final Label lblID;
    private final Label lblGender;
    private final Label lblError;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    private final TextField fieldName;
    private final TextField fieldAge;
    private final TextField fieldID;
    /**
     * ---------------------------------------------------------------------
     *                             TEXT BUTTONS
     * ---------------------------------------------------------------------
     */
    private final TextButton btnAccept;
    private final TextButton btnReturn;
    private final TextButton btnMale;
    private final TextButton btnFemale;
    public RegisterMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        super.stage = stage;
        this.titleText = titleText;
        TAG = "RegisterMenu";
        //Labels
        lblName = new Label("Nombre:", skin);
        lblAge = new Label("Edad:", skin);
        lblID = new Label("No.Carnet:", skin);
        lblGender = new Label("Sexo:", skin);
        lblError = new Label("", skin);
        //Text Fields
        fieldName = new TextField("", skin);
        fieldAge = new TextField("", skin);
        fieldID = new TextField("", skin);
        //Buttons
        btnAccept = new TextButton("Aceptar", skin);
        btnReturn = new TextButton("Regresar", skin);
        btnMale = new TextButton("Masculino", skin, "toggle");
        btnFemale = new TextButton("Femenino", skin, "toggle");
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnAccept.addListener(new Listener(BTN_ACCEPT));
        btnReturn.addListener(new Listener(BTN_RETURN));
        btnMale.addListener(new Listener(BTN_MALE));
        btnFemale.addListener(new Listener(BTN_FEMALE));
        //Tables
        parentTable.setPosition(-20, 0);
        btnTable.setPosition(0, -190);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
        stage.addActor(btnTable);
        stage.addActor(lblError);
    }

    @Override
    protected void tableOrganization() {
        final int lblPadRight = 100;
        final int fieldPadRight = 100;
        final int fieldHeight = 50, fieldWidth = 300;

        //Table Interns
        parentTable.add(lblName).height(50).width(75).padLeft(lblPadRight).right();
        parentTable.add(fieldName).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        parentTable.row();
        parentTable.add(lblAge).height(50).width(75).padLeft(lblPadRight).right();
        parentTable.add(fieldAge).colspan(2).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        parentTable.row();
        parentTable.add(lblID).height(50).width(75).padLeft(lblPadRight).right().padRight(10);
        parentTable.add(fieldID).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        parentTable.row().padBottom(50);
        parentTable.add(lblGender).height(50).width(75).padLeft(lblPadRight).right();
        parentTable.add(btnMale).height(50).width(100).padLeft(20);
        parentTable.add(btnFemale).height(50).width(100).padRight(120);
        //parentTable.debug();

        btnTable.add(btnReturn).height(50).width(150).padRight(150);
        btnTable.add(btnAccept).height(50).width(150).right();
        //btnTable.debug();

        lblError.setAlignment(Align.center);
        lblError.setPosition((float) GameHandler.NATIVE_RES_WIDTH/2, (float) GameHandler.NATIVE_RES_HEIGHT/2+150);
    }

    private class Listener extends ChangeListener {
        BtnListeners btnListeners;

        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
        }

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            switch (btnListeners) {
                case BTN_ACCEPT:
                    btnAcceptListener();
                    break;
                case BTN_RETURN:
                    btnReturnListener();
                    break;
                case BTN_MALE:
                    btnMaleListener();
                    break;
                case BTN_FEMALE:
                    btnFemaleListener();
                    break;
            }
        }
        
        private void btnAcceptListener() {
            //Name Check
            if(fieldName.getText() != null && !fieldName.getText().equals("") && !fieldName.getText().equals(" "))
                GameHandler.playerName = fieldName.getText().trim().toLowerCase();
            else {
                lblError.setText("Porfavor ingresa un nombre");
                return;
            }
            //Age Check
            if(fieldAge.getText() != null && !fieldAge.getText().equals("")) {
                try {
                    GameHandler.playerAge = Integer.parseInt(fieldAge.getText().trim());
                }
                catch (NumberFormatException ex) {
                    lblError.setText("Porfavor ingresa un numero en la edad");
                    return;
                }
            }
            else {
                lblError.setText("Porfavor ingresa la edad");
                return;
            }
            //ID Check
            if(fieldID.getText() != null && !fieldID.getText().equals(""))
                GameHandler.playerID = fieldName.getText().trim().toLowerCase();
            else {
                lblError.setText("Porfavor ingresa un ID");
                return;
            }
            //Gender Check
            if(GameHandler.playerGender == null) {
                lblError.setText("Porfavor selecciona un genero");
                return;
            }
            //Connect to database and send info
            //Reset values
            fieldName.setText("");
            fieldAge.setText("");
            fieldID.setText("");
            btnMale.setChecked(false);
            btnFemale.setChecked(false);
            GameHandler.playerGender = null;
            //Check if the profile exists
            //TODO Perform write of the profile on a CSV File or database file
            MainMenu.menuState = MenuState.CONFIG;
        }

        private void btnReturnListener() {
            //Reset values
            fieldName.setText("");
            fieldAge.setText("");
            fieldID.setText("");
            btnMale.setChecked(false);
            btnFemale.setChecked(false);
            GameHandler.playerGender = null;
            MainMenu.menuState = MenuState.LOGIN;
        }

        private void btnMaleListener() {
            GameHandler.playerGender = "Male";
            btnFemale.setChecked(false);
        }

        private void btnFemaleListener() {
            GameHandler.playerGender = "Female";
            btnMale.setChecked(false);
        }
    }
}
