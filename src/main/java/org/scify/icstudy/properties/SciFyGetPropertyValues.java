package org.scify.icstudy.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


public class SciFyGetPropertyValues {
    String result = "";
    public String getIpValue() throws IOException {
        File file;
        try {
            String propFileName = "config.properties";
            /*URL url = getClass().getResource(propFileName);
            System.out.println(url.toURI().getPath());*/
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            file = new File(s+"/..");
            String pathToProps = file.getCanonicalPath() + "/src/main/resources/" + propFileName;
            System.out.println(pathToProps);
            PropertiesConfiguration config = new PropertiesConfiguration(pathToProps);
            result = config.getProperty("ip").toString();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }


    public void setIpValue(String ip) throws IOException {
        File file;
        try {
            String propFileName = "config.properties";
            /*URL url = getClass().getResource(propFileName);
            System.out.println(url.toURI().getPath());*/
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            file = new File(s+"/..");
            String pathToProps = file.getCanonicalPath() + "/src/main/resources/" + propFileName;
            System.out.println(pathToProps);
            PropertiesConfiguration config = new PropertiesConfiguration(pathToProps);
            config.setProperty("ip", ip);
            config.save();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}