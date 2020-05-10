package br.com.anima.desktop;

import br.com.anima.Igniter;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = true;
        config.width = Igniter.WIDTH;
        config.height = Igniter.HEIGHT;
        config.resizable = true;
        config.title = "Anima Wishes v1.1 BUILD 25";
        config.addIcon("icons/anima.fw.png", FileType.Internal);
        new LwjglApplication(new Igniter(), config);
    }
}
