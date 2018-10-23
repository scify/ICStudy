package org.scify.icstudy.properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import java.io.File;
import java.io.IOException;


public class PropertyHandler {
    private PropertiesConfiguration config;

    public PropertyHandler() {
        File file;
        String propFileName = "config.properties";
        file = new File(propFileName);
        // Init file, if it does not exist
        try {
            System.out.println("No settings file found. Initializing as " + file.getAbsolutePath());
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pathToProps = file.getAbsolutePath();
        System.out.println("Using settings file: " + pathToProps);
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
            System.err.println(String.format("Exception when getting value for key  %s:\n%s", name, e.toString()));
            return null;
        }
    }


    public void setValue(String name, String value) {
        try {
            config.setProperty(name, value);
            config.save();
        } catch (Exception e) {
            System.err.println(String.format("Exception when setting value for key  %s:\n%s", name, e.toString()));
        }
    }

}