package com.sample.akka;

import akka.actor.UntypedActor;
import com.sample.akka.core.ProcessingContext;
import com.sample.akka.messages.ResultMessage;
import com.sample.akka.messages.WriteResult;

import java.io.*;
import java.util.*;

/**
 * Created by root on 12/11/15.
 */
public class DataResolver extends UntypedActor {

    private double startTime = 0.0;
    private int receivedLines = 0;
    private Map<String, Double> result = new TreeMap<>();

    private ProcessingContext context;

    public DataResolver(ProcessingContext context) {
        this.context = context;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof ResultMessage) {
            ResultMessage resultMessage = (ResultMessage) message;

            if (resultMessage.getChunkIndex() == 1)
                startTime = System.currentTimeMillis();

            String[] lines = resultMessage.getLines();

            for (String line : lines) {
                if(!line.trim().isEmpty()) {
                    String arr[] = line.split(":");
                    String key = arr[0];
                    double value = Double.valueOf(arr[1]);
                    result.put(key, result.getOrDefault(key, 0.0) + value);
                }
            }

            receivedLines += lines.length;

            if (receivedLines == context.getLinesNumber()) {
                System.out.println("Total Time: " + (System.currentTimeMillis() - startTime));
                context.getDataWriter().tell(new WriteResult(result, context.getResultFileName()), getSelf());
            }
        }

    }

}
