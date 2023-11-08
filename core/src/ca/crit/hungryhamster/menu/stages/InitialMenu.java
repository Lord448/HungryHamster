package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ca.crit.hungryhamster.main.GameText;
import ca.crit.hungryhamster.menu.MainMenu;

public class InitialMenu extends Menus {
    private final String TAG = "InitialMenu";
    private final Skin skin;
    private final GameText titleText;

    public InitialMenu(Skin skin, Stage stage, String titleText) {
        this.skin = skin;
        super.stage = stage;
        this.titleText = new GameText(titleText, 10, 115);
    }
    public InitialMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        super.stage = stage;
        this.titleText = titleText;
    }
    @Override
    public void render(SpriteBatch batch) {
        titleText.draw(batch);
    }
    @Override
    public void uiConstruct() {
        //Buttons
        TextButton btnPlay = new TextButton("Jugar", skin);
        TextButton btnFinish  = new TextButton("Salir", skin);
        //Listeners
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenu.menuState = MenuState.LOGIN;
            }
        });
        btnFinish.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });
        //Table
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0, -25);
        //Table interns
        table.row().padBottom(20);
        table.add(btnPlay).width(200).height(60).padBottom(20);
        table.row();
        table.add(btnFinish).width(200).height(60);
        //table.debug();
        //Stage
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        titleText.dispose();
    }
}
