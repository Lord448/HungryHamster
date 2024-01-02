package ca.crit.hungryhamster.menus.main;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.MenusScreen;
import ca.crit.hungryhamster.menus.main.stages.ConfigMenu;
import ca.crit.hungryhamster.menus.main.stages.PatientsMenu;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.menus.main.stages.InitialMenu;
import ca.crit.hungryhamster.menus.main.stages.LoginMenu;
import ca.crit.hungryhamster.menus.main.stages.MainMenuState;
import ca.crit.hungryhamster.menus.main.stages.RegisterMenu;

public class MainMenuScreen extends MenusScreen {
    public static MainMenuState mainMenuState;
    private final InitialMenu initialMenu;
    private final LoginMenu loginMenu;
    private final RegisterMenu registerMenu;
    private final ConfigMenu configMenu;
    private final PatientsMenu patientsMenu;

    public MainMenuScreen() {
        //Title Texts
        GameText titleText = new GameText("Hungry Hamster", 10, 115);
        GameText patientsText = new GameText("Pacientes", 22, 115);
        GameText whoPlaysText = new GameText("¿Quién juega?", 17, 115);
        whoPlaysText.setScales(0.15f, 0.38f);
        GameText registerText = new GameText("Registro", 23, 115);
        GameText configText = new GameText("Configura", 20, 125);
        //Menus
        initialMenu = new InitialMenu(skin, new Stage(uiViewport), titleText);
        patientsMenu = new PatientsMenu(skin, new Stage(uiViewport), patientsText);
        loginMenu = new LoginMenu(skin, new Stage(uiViewport), whoPlaysText);
        registerMenu = new RegisterMenu(skin, new Stage(uiViewport), registerText);
        configMenu = new ConfigMenu(skin, shadeSkin, new Stage(uiViewport), configText);
        mainMenuState = MainMenuState.INIT;
        initialMenu.uiConstruct();
        patientsMenu.uiConstruct();
        loginMenu.uiConstruct();
        registerMenu.uiConstruct();
        configMenu.uiConstruct();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();
        superClassRender(deltaTime, batch);
        switch (mainMenuState) {
            case INIT:
                initialMenu.render(batch);
            break;
            case PATIENTS:
                patientsMenu.render(batch);
            break;
            case LOGIN:
                loginMenu.render(batch);
            break;
            case REGISTER:
                registerMenu.render(batch);
            break;
            case CONFIG:
                configMenu.render(batch);
            break;
        }
        batch.end();

        switch (mainMenuState) {
            case INIT:
                initialMenu.stageRender(deltaTime);
            break;
            case PATIENTS:
                patientsMenu.stageRender(deltaTime);
                break;
            case LOGIN:
                loginMenu.stageRender(deltaTime);
            break;
            case REGISTER:
                registerMenu.stageRender(deltaTime);
            break;
            case CONFIG:
                configMenu.stageRender(deltaTime);
            break;
        }

        if(GameHandler.resetConfigMenu) {
            configMenu.reset();
            GameHandler.resetConfigMenu = false;
        }
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
        batch.dispose();
        skin.dispose();
        initialMenu.dispose();
        patientsMenu.dispose();
        loginMenu.dispose();
        registerMenu.dispose();
        configMenu.dispose();
    }
}