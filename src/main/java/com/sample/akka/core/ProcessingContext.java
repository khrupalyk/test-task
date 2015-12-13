package com.sample.akka.core;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.sample.akka.DataResolver;
import com.sample.akka.DataWriter;

/**
 * Created by root on 12/11/15.
 */
public class ProcessingContext {

    private final static ActorRef dataResolver = Akka.system.actorOf(Props.create(DataResolver.class, ProcessingHolder.HOLDER_INSTANCE));
    private final static ActorRef dataWriter = Akka.system.actorOf(Props.create(DataWriter.class));
    private final static int defaultBlocksSize = 44*10;
    private final static int oneBlockSize = 44;
    private final static String resultFileName = "src/main/resources/result.txt";
    private static long linesNumber = -1;


    public static class ProcessingHolder {
        public static final ProcessingContext HOLDER_INSTANCE = new ProcessingContext();
    }

    public static ProcessingContext getInstance() {
        return ProcessingHolder.HOLDER_INSTANCE;
    }

    public  ActorRef getDataResolver() {
        return dataResolver;
    }

    public int getDefaultBlocksSize() {
        return defaultBlocksSize;
    }

    public long getLinesNumber() {
        return linesNumber;
    }

    public void setLinesNumber(long messageNumber) {
        ProcessingContext.linesNumber = messageNumber;
    }

    public int getOneBlockSize() {
        return oneBlockSize;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public ActorRef getDataWriter() {
        return dataWriter;
    }
}
