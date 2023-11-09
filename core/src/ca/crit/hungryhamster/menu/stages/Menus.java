package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import ca.crit.hungryhamster.main.GameText;

public abstract class Menus {
    protected String TAG;
    protected Skin skin;
    protected Stage stage;
    protected GameText titleText;
    protected Table parentTable;
    protected Table btnTable;
    public abstract void uiConstruct();
    protected abstract void tableOrganization();
    public void stageRender(float deltaTime) {
        Gdx.input.setInputProcessor(stage);
        stage.draw();
        stage.act(deltaTime);
    }
    public void render(SpriteBatch batch) {
        titleText.draw(batch);
    }
    public void dispose() {
        titleText.dispose();
    }
    public Stage getStage() {
        return stage;
    }
}