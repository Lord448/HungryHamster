package ca.crit.hungryhamster.menus.demo;

import static ca.crit.hungryhamster.menus.demo.DemoUI.BtnListeners.BTN_EXIT;
import static ca.crit.hungryhamster.menus.demo.DemoUI.BtnListeners.BTN_RETURN;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ca.crit.hungryhamster.GameHandler;
import ca.crit.hungryhamster.menus.Menus;
import ca.crit.hungryhamster.menus.main.MainMenuScreen;
import ca.crit.hungryhamster.menus.main.stages.MainMenuState;
import ca.crit.hungryhamster.resources.text.GameText;

public class DemoUI extends Menus {
    enum BtnListeners {
        BTN_RETURN,
        BTN_EXIT
    }

    private final TextButton btnReturn, btnExit;
    private final Label lblThanksForPlay;

    public DemoUI(Skin skin, Stage stage, GameText titleText) {
        this.skin = skin;
        this.stage = stage;
        this.titleText = titleText;
        btnReturn = new TextButton("Regresar", skin);
        btnExit = new TextButton("Salir", skin);
        lblThanksForPlay = new Label("Gracias por jugar!", skin);
    }

    @Override
    public void uiConstruct() {
        //Listeners
        btnReturn.addListener(new Listener(BTN_RETURN));
        btnExit.addListener(new Listener(BTN_EXIT));
        //Tables Characteristics
        parentTable.setPosition(0, -50);
        //------------------
        //Table Organization
        //------------------
        tableOrganization();
        //Stage
        stage.addActor(parentTable);
    }

    @Override
    protected void tableOrganization() {
        parentTable.add(lblThanksForPlay).colspan(2).padBottom(100);
        parentTable.row();
        parentTable.add(btnExit).width(100).height(50).padRight(50);
        parentTable.add(btnReturn).width(100).height(50);
        //parentTable.debug();
    }

    private class Listener extends ChangeListener {
        private final BtnListeners btnListeners;

        public Listener(BtnListeners btnListeners) {
            this.btnListeners = btnListeners;
        }

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            switch (btnListeners) {
                case BTN_RETURN:
                    btnReturnListener();
                    break;
                case BTN_EXIT:
                    btnExitListener();
                    break;
            }
        }

        private void btnReturnListener() {
            GameHandler.currentScreen = GameHandler.Screens.MenuScreen;
            MainMenuScreen.mainMenuState = MainMenuState.INIT;
            //TODO Solve reset bug
            GameHandler.disposeAll();
        }

        private void btnExitListener() {
            System.exit(0);
        }
    }
}
