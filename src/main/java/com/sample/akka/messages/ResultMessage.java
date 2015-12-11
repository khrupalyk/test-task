package com.sample.akka.messages;

/**
 * Created by root on 12/11/15.
 */
public class ResultMessage {
    private final String[] lines;
    private final int chunkIndex;
    private final long totalChunks;

    public ResultMessage(String[] lines, int chunkIndex, long totalChunks) {
        this.lines = lines;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
    }

    public String[] getLines() {
        return lines;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public long getTotalChunks() {
        return totalChunks;
    }
}
