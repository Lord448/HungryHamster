package ca.crit.hungryhamster.menus.resume;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.text.GameText;

//TODO Resume Menu
public class ResumeMenu extends Menus {
    /**
     * ---------------------------------------------------------------------
     *                         BUTTONS WITH LISTENERS
     * ---------------------------------------------------------------------
     */
    enum BtnListeners{

    }
    /**
     * ---------------------------------------------------------------------
     *                                LABELS
     * ---------------------------------------------------------------------
     */
    /**
     * ---------------------------------------------------------------------
     *                             TEXT FIELDS
     * ---------------------------------------------------------------------
     */
    /**
     * ---------------------------------------------------------------------
     *                               BUTTONS
     * ---------------------------------------------------------------------
     */

    public ResumeMenu(Skin skin, Stage stage) {
        this.skin = skin;
        this.stage = stage;
    }

    @Override
    public void uiConstruct() {
        //Listeners
        //Tables Characteristics
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
    }

    @Override
    protected void tableOrganization() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private class Listener extends ChangeListener {
        BtnListeners btnListeners;
        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
        }
        @Override
        public void changed(ChangeEvent event, Actor actor) {

        }
    }
}
