package com.sample.akka.messages;

/**
 * Created by root on 12/11/15.
 */
public class StartProcess {
    private final String fileLocation;

    public StartProcess(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
