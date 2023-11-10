package ca.crit.hungryhamster.menus.main.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.resources.GameText;

public class PatientsMenu extends Menus {
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

    public PatientsMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
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
    }

    @Override
    protected void tableOrganization() {

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
