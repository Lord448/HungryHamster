package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Menus {
    protected Stage stage;

    public abstract void render(SpriteBatch batch);

    public abstract void uiConstruct();
    public abstract void dispose();

    public void stageRender(float deltaTime) {
        Gdx.input.setInputProcessor(stage);
        stage.draw();
        stage.act(deltaTime);
    }

    public Stage getStage() {
        return stage;
    }
}
