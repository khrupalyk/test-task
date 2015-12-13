package com.sample.akka.messages;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by root on 12.12.15.
 */
public class WriteResult {
    private final Map<String, Double> result;
    private final String fileName;

    public WriteResult(Map<String, Double> result, String fileName) {
        this.result = result;
        this.fileName = fileName;
    }

    public Map<String, Double> getResult() {
        return result;
    }

    public String getFileName() {
        return fileName;
    }
}
