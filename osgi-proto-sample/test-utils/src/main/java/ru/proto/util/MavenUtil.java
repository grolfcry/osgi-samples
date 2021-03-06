package ru.proto.util;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * User: fmv
 * Date: 15.03.13
 * Time: 18:34
 */
public class MavenUtil {

    public static String version() {
        return getProperty("version");
    }
    public static String groupId() {
        return getProperty("groupId");
    }
    public static String artifactId() {
        return getProperty("artifactId");
    }
    public static String finalName(){
        return artifactId()+"-"+version()+".jar";
    }
    public static String mvnLink(){
            return new MavenArtifactUrlReference().groupId(groupId()).version(version()).getURL();
    }
    public static MavenArtifactProvisionOption mvnBundle(String moduleArtifactId){
        return CoreOptions.mavenBundle().groupId(groupId()).artifactId(moduleArtifactId).version(versionAsThisProject());
    }
    public static String packageReference(){
        return  "reference:file:"+ addModuleIfNeed() +"target/" + finalName();
    }
    public static String classesReference(){
        return  "reference:file:"+ addModuleIfNeed() +"target/classes";
    }

    private static String addModuleIfNeed() {
        String modulePath = artifactId()+"/";
        if (System.getProperty("user.dir").endsWith(artifactId())){
            modulePath ="";
        }
        return modulePath;
    }

    private static String getProperty(String key) {
        String property = getProperties().getProperty(key);
        if (property==null){
            throw new RuntimeException(
                    "Could not resolve version. Do you have a dependency info for key = "+key+ " in your maven project?");

        }
        return property;
    }

    public static String getArtifactVersion(final String groupId, final String artifactId) {
            return getProperty(groupId + "/" + artifactId + "/version");
    }

    private static Properties getProperties() {
        final Properties dependencies = new Properties();
        try {
            dependencies.load(new FileInputStream(
                    getFileFromClasspath("META-INF/maven/dependencies.properties")));
        } catch (IOException e) {
            throw new RuntimeException("Could not load dependency information generated by maven. Please use depends-maven-plugin", e);
        }
        return dependencies;
    }

    private static File getFileFromClasspath(final String filePath) throws FileNotFoundException {
        try {
            URL fileURL = MavenUtil.class.getClassLoader().getResource(filePath);
            if (fileURL == null) {
                if (fileURL == null) {
                    // try the TCCL for getResource
                    fileURL = Thread.currentThread().getContextClassLoader().getResource(filePath);
                }
            }
            if (fileURL == null) {
                throw new FileNotFoundException("File [" + filePath
                        + "] could not be found in classpath");
            }
            return new File(fileURL.toURI());
        } catch (URISyntaxException e) {
            throw new FileNotFoundException("File [" + filePath + "] could not be found: "
                    + e.getMessage());
        }
    }

    public static MavenArtifactUrlReference.VersionResolver versionAsThisProject() {
        return new MavenArtifactUrlReference.VersionResolver() {
            public String getVersion(final String groupId, final String artifactId) {
                return version();
            }

        };
    }
}
