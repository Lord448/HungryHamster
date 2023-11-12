package ca.crit.hungryhamster.menus.resume;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Wizard;
import ca.crit.hungryhamster.menus.MenusScreen;

public class ResumeScreen extends MenusScreen {
    /**
     *   CONSTANTS
     */
    private final String TAG = "ResumeScreen";
    private final Texture treeHouse;
    /**
     *   CHARACTER
     */
    private final Wizard wizard;
    /**
     *   MENUS
     */
    private final Stage stage;
    private final ResumeMenu resumeMenu;
    public ResumeScreen() {
        treeHouse = new Texture("Background/tree_house.png");
        wizard = new Wizard(GameHandler.WORLD_WIDTH/2 - 25 , 2, 26, 25, 1/10f);
        stage = new Stage(uiViewport);
        resumeMenu = new ResumeMenu(skin, stage);
    }

    @Override
    public void show() {
        resumeMenu.uiConstruct();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        superClassRender(delta, batch);
        //TreeHouse render
        batch.draw(treeHouse, (float) GameHandler.WORLD_WIDTH/2 - 27, 0, GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT+30);
        //Characters
        wizard.render(batch);
        batch.end();
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

    }
}
