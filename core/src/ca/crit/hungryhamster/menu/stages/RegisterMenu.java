package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ca.crit.hungryhamster.main.GameText;

public class RegisterMenu extends Menus{
    private final String TAG = "RegisterMenu";
    private final Skin skin;
    private final GameText titleText;
    public RegisterMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        super.stage = stage;
        this.titleText = titleText;
    }

    @Override
    public void uiConstruct() {

    }

    @Override
    protected void tableOrganization() {

    }
}
