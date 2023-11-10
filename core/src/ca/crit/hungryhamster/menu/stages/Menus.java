package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.resources.GameText;

public abstract class Menus {
    protected String TAG;
    protected Skin skin;
    protected Stage stage;
    protected final Viewport uiViewport;
    protected GameText titleText;
    protected Table parentTable = new Table();
    protected Table btnTable = new Table();
    public Menus(){
        parentTable.setFillParent(true);
        parentTable.setPosition(0, 0);
        btnTable.setFillParent(true);
        btnTable.setPosition(0, 0);
        uiViewport = new StretchViewport(GameHandler.NATIVE_RES_WIDTH, GameHandler.NATIVE_RES_HEIGHT, new OrthographicCamera());;
        stage = new Stage(uiViewport);
    }
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
    public void update(int width, int height, boolean centerCamera) {
        uiViewport.update(width, height, centerCamera);
    }
    public void dispose() {
        titleText.dispose();
    }
    public Stage getStage() {
        return stage;
    }
}
