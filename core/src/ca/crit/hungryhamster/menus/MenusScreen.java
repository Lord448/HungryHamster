package ca.crit.hungryhamster.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.main.Background;

public abstract class MenusScreen implements Screen {
    protected final String mainSkinDir = "UISkin/uiskin.json";
    protected final String shadeSkinDir = "ShadeUISkin/uiskin.json";
    protected final Skin skin;
    protected final Skin shadeSkin;
    protected final Camera camera;
    protected final Viewport viewport;
    protected final Viewport uiViewport;
    protected final SpriteBatch batch;
    protected final Background background;

    protected MenusScreen() {
        skin = new Skin(Gdx.files.internal(mainSkinDir));
        shadeSkin = new Skin(Gdx.files.internal(shadeSkinDir));
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameHandler.WORLD_WIDTH, GameHandler.WORLD_HEIGHT, camera);
        uiViewport = new StretchViewport(GameHandler.NATIVE_RES_WIDTH, GameHandler.NATIVE_RES_HEIGHT, new OrthographicCamera());
        batch = new SpriteBatch();
        background = new Background();
    }

    /**
     * @implNote If override need to begin, end the batch and call the inner render
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        batch.begin();
        superClassRender(delta, batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Has to be inside the batch
     * @param delta
     * @param batch
     */
    protected void superClassRender(float delta, final SpriteBatch batch) {
        background.renderDynamicBackground(delta, batch);
        background.renderStaticBackground(batch);
    }
}
