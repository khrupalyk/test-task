package com.sample.akka.messages;

/**
 * Created by root on 12/11/15.
 */
public class DataMessage {
    private final String bigDataFilePath;
    private final int chunkIndex;
    private final long totalChunks;

    public DataMessage(String bigDataFilePath, int chunkIndex, long totalChunks) {
        this.bigDataFilePath = bigDataFilePath;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
    }

    public String getBigDataFilePath() {
        return bigDataFilePath;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public long getTotalChunks() {
        return totalChunks;
    }
}
