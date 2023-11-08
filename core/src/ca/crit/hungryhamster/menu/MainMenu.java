package ca.crit.hungryhamster.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Objects;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Background;
import ca.crit.hungryhamster.main.GameText;
import ca.crit.hungryhamster.menu.stages.InitialMenu;
import ca.crit.hungryhamster.menu.stages.LoginMenu;
import ca.crit.hungryhamster.menu.stages.MenuState;
import ca.crit.hungryhamster.time.Time;
import ca.crit.hungryhamster.time.TimeFormatException;

public class MainMenu implements Screen{
    public static MenuState menuState;
    //SCREEN
    private final Camera camera;
    private final Viewport viewport;
    private final Viewport uiViewport;
    //GRAPHICS
    private final SpriteBatch batch;
    private final Background background;
    private final Skin skin;
    private final Skin shadeSkin;
    private final String mainSkinDir = "UISkin/uiskin.json";
    private final String shadeSkinDir = "ShadeUISkin/uiskin.json";
    private final Stage patientsStage, loginStage, registerStage, configStage;
    private final GameText titleText, patientsText, whoPlaysText, registerText, configText;
    private final InitialMenu initialMenu;
    private final LoginMenu loginMenu;

    public MainMenu() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT, camera);
        uiViewport = new StretchViewport(GameHandler.NATIVE_RES_WIDTH, GameHandler.NATIVE_RES_HEIGHT, new OrthographicCamera());
        loginStage = new Stage(uiViewport);
        patientsStage = new Stage(uiViewport);
        registerStage = new Stage(uiViewport);
        configStage = new Stage(uiViewport);
        batch = new SpriteBatch();
        background = new Background();
        skin = new Skin(Gdx.files.internal(mainSkinDir));
        shadeSkin = new Skin(Gdx.files.internal(shadeSkinDir));
        titleText = new GameText("Hungry Hamster", 10, 115);
        patientsText = new GameText("Pacientes", 22, 115);
        whoPlaysText = new GameText("¿Quién juega?", 17, 115);
        whoPlaysText.setScales(0.15f, 0.38f);
        registerText = new GameText("Registro", 23, 115);
        configText = new GameText("Configura", 20, 125);
        menuState = MenuState.INIT;
        //Experimental
        initialMenu = new InitialMenu(skin, new Stage(uiViewport), titleText);
        loginMenu = new LoginMenu(skin, new Stage(uiViewport), whoPlaysText);
    }

    @Override
    public void show() {
        //loginMenuConstruct();
        registerMenuConstruct();

        configMenuConstruct();

        initialMenu.uiConstruct();
        loginMenu.uiConstruct();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        background.renderDynamicBackground(deltaTime, batch);
        background.renderStaticBackground(batch);
        switch (menuState) {
            case INIT:
                initialMenu.render(batch);
            break;
            case PATIENTS:
                patientsText.draw(batch);
            break;
            case LOGIN:
                //whoPlaysText.draw(batch);
                loginMenu.render(batch);
            break;
            case REGISTER:
                registerText.draw(batch);
            break;
            case CONFIG:
                configText.draw(batch);
            break;
        }
        batch.end();

        switch (menuState) {
            case INIT:
                initialMenu.stageRender(deltaTime);
            break;
            case PATIENTS:
                Gdx.input.setInputProcessor(patientsStage);
                patientsStage.draw();
                patientsStage.act(deltaTime);
                break;
            case LOGIN:
                //Gdx.input.setInputProcessor(loginStage);
                //loginStage.draw();
                //loginStage.act(deltaTime);
                loginMenu.stageRender(deltaTime);
            break;
            case REGISTER:
                Gdx.input.setInputProcessor(registerStage);
                registerStage.draw();
                registerStage.act(deltaTime);
            break;
            case CONFIG:
                Gdx.input.setInputProcessor(configStage);
                configStage.draw();
                configStage.act(deltaTime);
            break;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        initialMenu.dispose();
        loginMenu.dispose();
    }

    private void loginMenuConstruct() {
        final int btnWidth = 130;
        //Labels
        Label lblID = new Label("No. Carnet:", skin);
        Label lblError = new Label("", skin);
        //Text Fields
        TextField idField = new TextField("", skin);
        //Buttons
        TextButton btnNewPatient = new TextButton("Nuevo paciente", skin);
        TextButton btnNext = new TextButton("Siguiente", skin);
        TextButton btnExit = new TextButton("Salir", skin);
        TextButton btnPatients = new TextButton("Pacientes", skin);
        //Listeners
        btnNewPatient.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuState = MenuState.REGISTER;
            }
        });
        btnNext.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!Objects.equals(idField.getText(), "")) {
                    //Protect more the variable
                    //Search for the ID in database
                    GameHandler.playerID = idField.getText();
                    System.out.println("ID: " + GameHandler.playerID);
                    menuState = MenuState.CONFIG;
                }
                else {
                    lblError.setText("Coloca un No. de Carnet");
                }
            }
        });
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });
        btnPatients.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuState = MenuState.PATIENTS;
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
        loginStage.addActor(table);
        loginStage.addActor(btnTable);
    }

    private void registerMenuConstruct() {
        final int lblPadRight = 100;
        final int fieldPadRight = 100;
        final int fieldHeight = 50, fieldWidth = 300;
        //Labels
        Label lblName = new Label("Nombre:", skin);
        Label lblAge = new Label("Edad:", skin);
        Label lblID = new Label("No.Carnet:", skin);
        Label lblGender = new Label("Sexo:", skin);
        Label lblError = new Label("", skin);
        lblError.setColor(Color.BLACK);
        //Text Fields
        TextField fieldName = new TextField("", skin);
        TextField fieldAge = new TextField("", skin);
        TextField fieldID = new TextField("", skin);
        //Buttons
        TextButton btnAccept = new TextButton("Aceptar", skin);
        TextButton btnReturn = new TextButton("Regresar", skin);
        TextButton btnMale = new TextButton("Masculino", skin, "toggle");
        TextButton btnFemale = new TextButton("Femenino", skin, "toggle");
        //Listeners
        btnAccept.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
                menuState = MenuState.CONFIG;
            }
        });
        btnReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Reset values
                fieldName.setText("");
                fieldAge.setText("");
                fieldID.setText("");
                btnMale.setChecked(false);
                btnFemale.setChecked(false);
                GameHandler.playerGender = null;
                menuState = MenuState.LOGIN;
            }
        });
        btnMale.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                GameHandler.playerGender = "Male";
                btnFemale.setChecked(false);
            }
        });
        btnFemale.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                GameHandler.playerGender = "Female";
                btnMale.setChecked(false);
            }
        });
        //Table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(-20, 0);
        Table btnTable = new Table();
        btnTable.setFillParent(true);
        btnTable.setPosition(0, -190);
        //Table Interns
        table.add(lblName).height(50).width(75).padLeft(lblPadRight).right();
        table.add(fieldName).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        table.row();
        table.add(lblAge).height(50).width(75).padLeft(lblPadRight).right();
        table.add(fieldAge).colspan(2).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        table.row();
        table.add(lblID).height(50).width(75).padLeft(lblPadRight).right().padRight(10);
        table.add(fieldID).height(fieldHeight).width(fieldWidth).colspan(2).padRight(fieldPadRight).left();
        table.row().padBottom(50);
        table.add(lblGender).height(50).width(75).padLeft(lblPadRight).right();
        table.add(btnMale).height(50).width(100).padLeft(20);
        table.add(btnFemale).height(50).width(100).padRight(120);
        //table.debug();

        btnTable.add(btnReturn).height(50).width(150).padRight(150);
        btnTable.add(btnAccept).height(50).width(150).right();
        //btnTable.debug();

        lblError.setAlignment(Align.center);
        lblError.setPosition((float) GameHandler.NATIVE_RES_WIDTH/2, (float) GameHandler.NATIVE_RES_HEIGHT/2+150);
        //Stage
        registerStage.addActor(table);
        registerStage.addActor(btnTable);
        registerStage.addActor(lblError);
    }
    private boolean fieldCheck(TextField fieldMaxStep, TextField fieldMinStep, Label lblError) {
        int fieldMaxCounts, fieldMinCounts;
        try {
            fieldMaxCounts = Integer.parseInt(fieldMaxStep.getText().trim());
            fieldMinCounts = Integer.parseInt(fieldMinStep.getText().trim());

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
    private void configMenuConstruct() {
        final int fieldWidth = 200;
        final int fieldHeight = 50;
        final int btnWidth = 150;
        final int btnHeight = 50;
        final String initStringTime = "1:00";
        //Labels
        Label lblMaxStep = new Label("Escalon superior", skin);
        Label lblMinStep = new Label("Escalon inferior", skin);
        Label lblTime = new Label("Tiempo", skin);
        Label lblReps = new Label("Repeticiones", skin);
        Label lblError = new Label("", skin);
        //Text Fields
        TextField fieldMaxStep = new TextField("10", skin);
        TextField fieldMinStep = new TextField("0", skin);
        TextField fieldTime = new TextField(initStringTime, skin);
        TextField fieldReps = new TextField("Libre", skin);
        TextField fieldExtra = new TextField("", skin);
        fieldMaxStep.setAlignment(Align.center);
        fieldMinStep.setAlignment(Align.center);
        fieldTime.setAlignment(Align.center);
        fieldReps.setAlignment(Align.center);
        fieldExtra.setAlignment(Align.center);
        //Buttons
        TextButton btnPlay = new TextButton("Jugar", skin);
        TextButton btnReturn = new TextButton("Regresar", skin);
        Button[] btnArrows = new Button[10];
        for(int i = 0; i < btnArrows.length; i+=2) { //Construct for btnArrows
            btnArrows[i] = new Button(shadeSkin, "left");
            btnArrows[i+1] = new Button(shadeSkin, "right");
        }
        //Checkboxes
        CheckBox cbExtraFruit = new CheckBox("Fruta extra", shadeSkin);
        CheckBox cbRightHand = new CheckBox("Mano derecha", shadeSkin);
        CheckBox cbLeftHand = new CheckBox("Mano izquierda", shadeSkin);
        CheckBox cbBothHands = new CheckBox("Ambas manos", shadeSkin);
        CheckBox cbFreeTime = new CheckBox("Libre", shadeSkin);
        //Tables
        Table mainTable = new Table();
        Table upperArrowsTable = new Table();
        Table lowerArrowsTable = new Table();
        Table timeArrowsTable = new Table();
        Table extraArrowsTable = new Table();
        Table repsArrowsTable = new Table();
        Table handCheckTable = new Table();
        Table timeTable = new Table();
        //Tables characteristics
        mainTable.setFillParent(true);
        mainTable.setPosition(0, 0);
        //Trying to rotate
        /*
        for(Button i : btnArrows) {
            batch.flush();
            i.setTransform(true);
            i.rotateBy(-90);
            batch.flush();

        }
        Not working try by the atlas or json file
         */

        //Listeners
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!fieldTime.getText().equalsIgnoreCase("") && !fieldTime.getText().equalsIgnoreCase("")) {
                    try {
                        boolean isAllCBChecked = cbBothHands.isChecked() || cbLeftHand.isChecked() || cbRightHand.isChecked();
                        boolean notReachingMaxSteps = true;

                        GameHandler.maxStep = Integer.parseInt(fieldMaxStep.getText().trim());
                        GameHandler.minStep = Integer.parseInt(fieldMinStep.getText().trim());
                        GameHandler.numHouseSteps = GameHandler.maxStep - GameHandler.minStep;
                        if(fieldTime.getText().equals("Libre"))
                            GameHandler.sessionTime = new Time(0, 0);
                        else
                            GameHandler.sessionTime = Time.parseTime(fieldTime.getText().trim());
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
                            GameHandler.startGame = true;
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
        });
        //Making the listeners of the arrow buttons
        for(int i = 0; i < btnArrows.length; i+=2) {
            int finalI = i; //In order to avoid memory leaks
            final boolean[] isTime = new boolean[1];
            btnArrows[i].addListener(new ChangeListener() { //Up
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int counter;
                    Time time;
                    try {
                        if(finalI < 2) {
                            counter = Integer.parseInt(fieldMaxStep.getText().trim());
                            counter++;
                            if (counter >= 32)
                                counter = 32;
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
        btnReturn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuState = MenuState.LOGIN;
            }
        });
        fieldExtra.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cbExtraFruit.setChecked(!fieldExtra.getText().equals(""));
            }
        });
        //Checkboxes
        cbFreeTime.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(cbFreeTime.isChecked())
                    fieldTime.setText("Libre");
                else
                    fieldTime.setText(initStringTime);
            }
        });
        cbExtraFruit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        });
        cbLeftHand.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(cbLeftHand.isChecked()) {
                    if(cbRightHand.isChecked())
                        cbRightHand.setChecked(false);
                    else if(cbBothHands.isChecked())
                        cbBothHands.setChecked(false);
                    GameHandler.playerWorkingHand = GameHandler.LEFT_HAND;
                }
            }
        });
        cbRightHand.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(cbRightHand.isChecked()) {
                    if(cbLeftHand.isChecked())
                        cbLeftHand.setChecked(false);
                    else if(cbBothHands.isChecked())
                        cbBothHands.setChecked(false);
                    GameHandler.playerWorkingHand = GameHandler.RIGHT_HAND;
                }
            }
        });
        cbBothHands.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(cbBothHands.isChecked()) {
                    if (cbLeftHand.isChecked())
                        cbLeftHand.setChecked(false);
                    else if (cbRightHand.isChecked())
                        cbRightHand.setChecked(false);
                    GameHandler.playerWorkingHand = GameHandler.BOTH_HANDS;
                }
            }
        });
        //Table Interns
        mainTable.add(lblError).padBottom(0).colspan(3);
        mainTable.row();
        mainTable.add(lblMaxStep);
        mainTable.add(new Actor()); //Not null member for blank space
            timeTable.add(lblTime).padRight(25);
            timeTable.add(cbFreeTime);
        mainTable.add(timeTable).padLeft(70);
        mainTable.row();
        mainTable.add(fieldMaxStep).width(fieldWidth).height(fieldHeight);
            upperArrowsTable.add(btnArrows[0]); //Up
            upperArrowsTable.row();
            upperArrowsTable.add(btnArrows[1]); //Down
        mainTable.add(upperArrowsTable).left();
        mainTable.add(fieldTime).width(fieldWidth).height(fieldHeight).left();
            timeArrowsTable.add(btnArrows[2]); //Up
            timeArrowsTable.row();
            timeArrowsTable.add(btnArrows[3]); //Down
        mainTable.add(timeArrowsTable).left();
        mainTable.row();
        mainTable.add(lblMinStep);
        mainTable.add(new Actor()); //Not null member for blank space
        mainTable.add(cbExtraFruit).padLeft(10);
        mainTable.row();
        mainTable.add(fieldMinStep).width(fieldWidth).height(fieldHeight);
            lowerArrowsTable.add(btnArrows[4]); //Up
            lowerArrowsTable.row();
            lowerArrowsTable.add(btnArrows[5]); //Down
        mainTable.add(lowerArrowsTable).left().padRight(10);
        mainTable.add(fieldExtra).width(fieldWidth).height(fieldHeight).left();
            extraArrowsTable.add(btnArrows[6]);
            extraArrowsTable.row();
            extraArrowsTable.add(btnArrows[7]);
        mainTable.add(extraArrowsTable).left();
        mainTable.row();
        mainTable.add(lblReps);
        mainTable.row().padBottom(20);
        mainTable.add(fieldReps).width(fieldWidth).height(fieldHeight);
            repsArrowsTable.add(btnArrows[8]);
            repsArrowsTable.row();
            repsArrowsTable.add(btnArrows[9]);
        mainTable.add(repsArrowsTable).left();
            handCheckTable.add(cbLeftHand);
            handCheckTable.row();
            handCheckTable.add(cbRightHand).padTop(10).padLeft(5);
            handCheckTable.row();
            handCheckTable.add(cbBothHands).padTop(10).center().colspan(2);
        mainTable.add(handCheckTable);
        mainTable.row().padBottom(20);
        mainTable.add(btnPlay).width(btnWidth).height(btnHeight).colspan(3);
        mainTable.row();
        mainTable.add(btnReturn).width(btnWidth).height(btnHeight).left();
        //mainTable.debug();
        //Stage
        configStage.addActor(mainTable);
    }
}