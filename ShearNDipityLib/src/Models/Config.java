package Models;

import Utilities.LogEventDispatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config config = null;
    private static Properties properties = null;

    protected Config() {
        properties = new Properties();

        File f = new File("config");
        if (!f.exists()) {
            try {
                LogEventDispatcher.log("Creating Config File.");
                properties.setProperty("SERVER", "127.0.0.1");
                properties.store(new FileOutputStream("config"), null);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                LogEventDispatcher.log("Loading Existing Config File.");
                properties.load(new FileInputStream("config"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static Config getInstance() {

        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public String getServer() {
        return properties.getProperty("SERVER", "http://shear-n-dipity.com");
    }
}
