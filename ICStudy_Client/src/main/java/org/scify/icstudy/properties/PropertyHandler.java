package org.scify.icstudy.properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import java.io.File;


public class PropertyHandler {
    private PropertiesConfiguration config;

    public PropertyHandler() {
        File file;
        String propFileName = "config.properties";
        file = new File(propFileName);
        String pathToProps = file.getAbsolutePath();
        System.out.println(pathToProps);
        try {
            config = new PropertiesConfiguration(pathToProps);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String name) {
        try {
            return config.getProperty(name).toString();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        }
    }


    public void setValue(String name, String value) {
        try {
            config.setProperty(name, value);
            config.save();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}