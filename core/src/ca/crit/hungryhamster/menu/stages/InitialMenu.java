package ca.crit.hungryhamster.menu.stages;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ca.crit.hungryhamster.main.GameText;
import ca.crit.hungryhamster.menu.MainMenu;

public class InitialMenu extends Menus {
    private final TextButton btnPlay;
    private final TextButton btnFinish;
    public InitialMenu(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        super.stage = stage;
        this.titleText = titleText;
        TAG = "InitialMenu";
        btnPlay = new TextButton("Jugar", skin);
        btnFinish = new TextButton("Salir", skin);
    }

    @Override
    public void uiConstruct() {
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
        parentTable = new Table();
        parentTable.setFillParent(true);
        parentTable.setPosition(0, -25);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
    }
    @Override
    protected void tableOrganization() {
        //Table interns
        parentTable.row().padBottom(20);
        parentTable.add(btnPlay).width(200).height(60).padBottom(20);
        parentTable.row();
        parentTable.add(btnFinish).width(200).height(60);
        //table.debug();
    }
}
