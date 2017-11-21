package ru.simplechat.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ivan on 19.11.2017.
 */
public class PropertyLoader {

    private static Properties prop = new Properties();

    public void loadProperty() {

        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Properties getProperties() {
        if (prop.isEmpty()) {
            PropertyLoader pl = new PropertyLoader();
            pl.loadProperty();
        }
        return prop;
    }
}
