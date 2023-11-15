package ca.crit.hungryhamster.menus.demo;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Wizard;
import ca.crit.hungryhamster.menus.MenusScreen;
import ca.crit.hungryhamster.resources.text.GameText;

public class DemoScreen extends MenusScreen {
    private final String TAG = "DemoScreen";
    private final Wizard wizard;
    private final DemoUI demoUI;
    GameText titleText;
    public DemoScreen() {
        wizard = new Wizard(GameHandler.WORLD_WIDTH/2 - 30 , 2, 26, 25, 1/10f);
        titleText = new GameText("Gracias por jugar!", 0, 0); //TODO Adjust position
        demoUI = new DemoUI(skin, new Stage(uiViewport), titleText);
    }

    @Override
    public void show() {
        demoUI.uiConstruct();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        demoUI.render(batch);
        titleText.draw(batch);
        superClassRender(delta, batch);
        wizard.render(batch);
        batch.end();
        demoUI.stageRender(delta);
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
        demoUI.dispose();
        wizard.dispose();
        batch.dispose();
    }
}
