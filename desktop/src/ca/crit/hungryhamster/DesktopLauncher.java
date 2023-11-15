package ca.crit.hungryhamster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import javax.management.monitor.Monitor;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		if(GameHandler.DEBUG_MODE == GameHandler.DEBUG_DEMO)
			GameHandler.init(0, GameHandler.DESKTOP_ENV);
		else
			GameHandler.init(0, GameHandler.DESKTOP_ENV);
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//Only valid when 2 monitors connected
		Graphics.Monitor[] monitors = Lwjgl3ApplicationConfiguration.getMonitors();
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode(monitors[1]);
		int posX = monitors[1].virtualX + displayMode.width/2 - 640/2;
		int posY = monitors[1].virtualY + displayMode.height/2 - 480/2;
		config.setWindowedMode(480, 640);
		config.setWindowPosition(posX, posY);

		config.setForegroundFPS(60);
		config.setTitle("HungryHamster");
		new Lwjgl3Application(new Main_hungryHamster(), config);
	}
}
