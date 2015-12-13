package com.sample.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import com.sample.akka.core.ProcessingContext;
import com.sample.akka.messages.DataMessage;
import com.sample.akka.messages.StartProcess;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by root on 12/11/15.
 */
public class DataProcessing extends UntypedActor {

    private ProcessingContext context;

    public DataProcessing(ProcessingContext context) {
        this.context = context;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof StartProcess) {
            StartProcess startProcess = (StartProcess) message;
            String fileLocation = startProcess.getFileLocation();

            int workerCount = Runtime.getRuntime().availableProcessors();
            ActorRef workers = getContext().system().actorOf(Props.create(FileWorker.class,context).withRouter(new RoundRobinPool(workerCount * 2)));

            long totalChunks = totalMessages(fileLocation) + 1;
            context.setLinesNumber(fileLength(fileLocation) / context.getOneBlockSize() + 1);
            System.out.println("Total mess: " + totalChunks);
            for (int i = 1; i <= totalChunks; i++) {
                workers.tell(new DataMessage(fileLocation, i, totalChunks), getSelf());
            }

        }

    }

    private long totalMessages(String bigDataFilePath) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(bigDataFilePath, "r")) {
            System.out.println("File length: " + randomAccessFile.length());
            return (randomAccessFile.length() / context.getDefaultBlocksSize());
        }
    }

    private long fileLength(String bigDataFilePath) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(bigDataFilePath, "r")) {
            return randomAccessFile.length() ;
        }
    }

}
