package util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

public class FileHandler {

    private static final String DIRECTORY_NAME = "weatherfiles";
    
    private Path pathToExternalDirectory;
    
    public FileHandler() {        
        Path pathToRunningJar = this.fetchAbsolutePathToRunningJar();
        this.pathToExternalDirectory = Path.of(pathToRunningJar.toString(), DIRECTORY_NAME);
        this.setupExternalDirectory();        
    }
    
    private Path fetchAbsolutePathToRunningJar() {
        try {
            return FileSystems.getDefault().getPath(
                new File(FileHandler.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getPath()
                );
        } catch (URISyntaxException e) {
            Butler.log(LogReason.ERROR, "Could not fetch path to running jar.");
        }
        return null;
    }
    
    private void setupExternalDirectory() {
        File fileHandle = this.pathToExternalDirectory.toFile();
        if (!fileHandle.exists() && !fileHandle.isDirectory()) {
            if (fileHandle.mkdir()) {
                Butler.log(LogReason.INFO, String.format("Directory %s successfully created.", DIRECTORY_NAME));
            } else {
                Butler.log(LogReason.ERROR, String.format("Failed to create directory %s.", DIRECTORY_NAME));
            }
        }        
    }
    
    public void createFile(final String filename) {
        File target = this.pathToExternalDirectory.toFile();
        try {
            if (Path.of(this.pathToExternalDirectory.toString(), filename).toFile().createNewFile()) {
                Butler.log(LogReason.INFO, String.format("File %s created.", filename));
            } else {
                Butler.log(LogReason.INFO, String.format("File %s allready exists.", filename));
            }               
        } catch (IOException e) {
            Butler.log(LogReason.ERROR, String.format("Could not create file %s at path %s", filename, this.pathToExternalDirectory.toString()));
        }
        
    }
    
    public void deleteFile(final String filename) {
        File target = this.pathToExternalDirectory.toFile();
        if (Path.of(this.pathToExternalDirectory.toString(), filename).toFile().delete()) {
            Butler.log(LogReason.INFO, String.format("File %s deleted.", filename));
        }        
    }
    
    public String getAbsolutePathOfRootDirectory() {
        return this.pathToExternalDirectory.toString();
    }
    
    /**
     * Warning: This method deletes the external directory with all its
     * content without asking.
     */
    public void tearDown() {
        FileHandler.deleteRecursive(this.pathToExternalDirectory.toFile());
        this.pathToExternalDirectory.toFile().delete();
        Butler.log(LogReason.INFO, String.format("Deleted %s and all its content.", DIRECTORY_NAME));
    }
    
    private static void deleteRecursive(File directory) {
        Arrays.stream(directory.listFiles())
            .forEach(file -> {
                if (file.isDirectory()) {
                    FileHandler.deleteRecursive(file);
                }
                Butler.log(LogReason.INFO, String.format("Deleted %s", file.toPath().getFileName().toString()));
                file.delete();                
            });
    }
        
}
