package com.sample.akka;

import akka.actor.UntypedActor;
import com.sample.akka.core.ProcessingContext;
import com.sample.akka.messages.DataMessage;
import com.sample.akka.messages.ResultMessage;

import java.io.*;

/**
 * Created by root on 12/11/15.
 */
public class FileWorker extends UntypedActor {

    private ProcessingContext context;

    private byte[] byteBuffer ;

    public FileWorker(ProcessingContext context) {
        this.context = context;
        byteBuffer  = new byte[context.getDefaultBlocksSize()];
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof DataMessage) {
            DataMessage dataMessage = (DataMessage)message;
            String[] readLines = readLines(dataMessage.getBigDataFilePath(), dataMessage.getChunkIndex());
            context.getDiagnostics().tell(new ResultMessage(readLines, dataMessage.getChunkIndex(), dataMessage.getTotalChunks()), getSelf());
        }

    }

    private String[] readLines(String bigDataFilePath, int chunkIndex) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(bigDataFilePath, "r")) {
            int seek = (chunkIndex - 1) * context.getDefaultBlocksSize();
            randomAccessFile.seek(seek);
            randomAccessFile.read(byteBuffer);
            String rawString = new String(byteBuffer);
            return rawString.split(System.getProperty("line.separator"));
        }
    }
}
