package br.com.anima.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import br.com.anima.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
		config.width = 1366;
        config.height = 768;
        config.resizable = false;   
        config.title = "Anima Wishes v1.1 BUILD 25";
        config.addIcon("icons/anima.fw.png", FileType.Internal);
		new LwjglApplication(new GameManager(), config);
	}
}
