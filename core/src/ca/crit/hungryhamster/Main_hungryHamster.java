package ca.crit.hungryhamster;


import com.badlogic.gdx.Game;

import ca.crit.hungryhamster.main.GameScreen;
import ca.crit.hungryhamster.menus.resume.ResumeScreen;
import ca.crit.hungryhamster.resources.GameSounds;
import ca.crit.hungryhamster.menus.main.MainMenuScreen;

public class Main_hungryHamster extends Game {
	private GameSounds gameSounds;
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private ResumeScreen resumeScreen;

	@Override
	public void create () {

		mainMenuScreen = new MainMenuScreen();
		gameScreen = new GameScreen();
		resumeScreen = new ResumeScreen();

		switch (GameHandler.DEBUG_MODE) {
			case GameHandler.DEBUG_MENU:
			case GameHandler.DEBUG_NONE:
				setScreen(mainMenuScreen);
				break;
			case GameHandler.DEBUG_GAME:
				GameHandler.maxStep = 10;
				GameHandler.minStep = 0;
				GameHandler.numHouseSteps = GameHandler.maxStep - GameHandler.minStep;
				setScreen(gameScreen);
			break;
			case GameHandler.DEBUG_RESUME:
				setScreen(resumeScreen);
			case GameHandler.DEBUG_DB:
			break;
		}

		//Game sounds
		gameSounds = new GameSounds();
		gameSounds.create();
	}

	@Override
	public void render () {
		super.render();
		switch (GameHandler.currentScreen) {
			case MenuScreen:
				setScreen(mainMenuScreen);
				GameHandler.currentScreen = GameHandler.Screens.IsSet;
				break;
			case GameScreen:
				setScreen(gameScreen);
				GameHandler.currentScreen = GameHandler.Screens.IsSet;
				break;
			case ResumeScreen:
				setScreen(resumeScreen);
				GameHandler.currentScreen = GameHandler.Screens.IsSet;
				break;
		}
	}

	@Override
	public void dispose () {
		gameSounds.dispose();
		gameScreen.dispose();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
		mainMenuScreen.resize(width, height);
	}
}

