package ca.crit.hungryhamster.menus.resume;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Wizard;
import ca.crit.hungryhamster.menus.MenusScreen;
import ca.crit.hungryhamster.resources.text.GameText;
import ca.crit.hungryhamster.resources.time.Timer;

public class ResumeScreen extends MenusScreen {
    /**
     *   CONSTANTS
     */
    private final String TAG = "ResumeScreen";
    /**
     *   CHARACTERS & OBJECTS
     */
    private final Wizard wizard;
    /**
     *   MENUS & GRAPHICS
     */
    private final ResumeMenu resumeMenu;
    public ResumeScreen() {
        wizard = new Wizard(GameHandler.WORLD_WIDTH/2 - 30 , 2, 26, 25, 1/10f);
        GameText titleText = new GameText("Resumen", 0, 100); //TODO Adjust position

        titleText.setScales(0.15f, 0.38f);
        resumeMenu = new ResumeMenu(skin, shadeSkin, new Stage(uiViewport), titleText);
        resumeMenu.uiConstruct();

    }

    @Override
    public void show() {
        resumeMenu.setLabelsText();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        resumeMenu.render(batch); //TODO Appear the Text

        superClassRender(delta, batch);

        //Characters
        wizard.render(batch);
        batch.end();
        resumeMenu.stageRender(delta);
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
        resumeMenu.dispose();
        wizard.dispose();
        batch.dispose();
    }
}
