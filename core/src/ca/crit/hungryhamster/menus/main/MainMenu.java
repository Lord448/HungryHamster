package ca.crit.hungryhamster.menus.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Background;
import ca.crit.hungryhamster.menus.main.stages.ConfigMenu;
import ca.crit.hungryhamster.resources.GameText;
import ca.crit.hungryhamster.menus.main.stages.InitialMenu;
import ca.crit.hungryhamster.menus.main.stages.LoginMenu;
import ca.crit.hungryhamster.menus.main.stages.MenuState;
import ca.crit.hungryhamster.menus.main.stages.RegisterMenu;

public class MainMenu implements Screen{
    public static MenuState menuState;
    //SCREEN
    private final Camera camera;
    private final Viewport viewport;
    private final Viewport uiViewport;
    //GRAPHICS
    private final SpriteBatch batch;
    private final Background background;
    private final Stage patientsStage, registerStage, configStage;
    private final GameText patientsText;
    /**
     * ---------------------------------------------------------------------
     *                               MENUS
     * ---------------------------------------------------------------------
     */
    private final InitialMenu initialMenu;
    private final LoginMenu loginMenu;
    private final RegisterMenu registerMenu;
    private final ConfigMenu configMenu;

    public MainMenu() {
        final String mainSkinDir = "UISkin/uiskin.json";
        final String shadeSkinDir = "ShadeUISkin/uiskin.json";
        Skin skin = new Skin(Gdx.files.internal(mainSkinDir));
        Skin shadeSkin = new Skin(Gdx.files.internal(shadeSkinDir));

        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT, camera);
        uiViewport = new StretchViewport(GameHandler.NATIVE_RES_WIDTH, GameHandler.NATIVE_RES_HEIGHT, new OrthographicCamera());
        patientsStage = new Stage(uiViewport);
        registerStage = new Stage(uiViewport);
        configStage = new Stage(uiViewport);
        batch = new SpriteBatch();
        background = new Background();
        GameText titleText = new GameText("Hungry Hamster", 10, 115);
        patientsText = new GameText("Pacientes", 22, 115);
        GameText whoPlaysText = new GameText("¿Quién juega?", 17, 115);
        whoPlaysText.setScales(0.15f, 0.38f);
        GameText registerText = new GameText("Registro", 23, 115);
        GameText configText = new GameText("Configura", 20, 125);
        initialMenu = new InitialMenu(skin, new Stage(uiViewport), titleText);
        loginMenu = new LoginMenu(skin, new Stage(uiViewport), whoPlaysText);
        registerMenu = new RegisterMenu(skin, new Stage(uiViewport), registerText);
        configMenu = new ConfigMenu(skin, shadeSkin, new Stage(uiViewport), configText);
        menuState = MenuState.INIT;
    }

    @Override
    public void show() {
        initialMenu.uiConstruct();
        loginMenu.uiConstruct();
        registerMenu.uiConstruct();
        configMenu.uiConstruct();
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
                loginMenu.stageRender(deltaTime);
            break;
            case REGISTER:
                registerMenu.stageRender(deltaTime);
            break;
            case CONFIG:
                configMenu.stageRender(deltaTime);
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
        registerMenu.dispose();
        configMenu.dispose();
    }
}