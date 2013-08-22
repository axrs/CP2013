package Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Config class to store persistent variables
 * <p/>
 * Created by xander on 8/22/13.
 */
public class Config {

    private static Config config = null;
    private static Properties properties = null;

    protected Config() {
        properties = new Properties();

        File f = new File("config");
        if (!f.exists()) {
            try {
                properties.setProperty("SERVER", "127.0.0.1");
                properties.store(new FileOutputStream("config"), null);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
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
