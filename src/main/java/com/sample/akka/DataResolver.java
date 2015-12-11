package com.sample.akka;

import akka.actor.UntypedActor;
import com.sample.akka.core.ProcessingContext;
import com.sample.akka.messages.ResultMessage;

import java.io.*;
import java.util.*;

/**
 * Created by root on 12/11/15.
 */
public class DataResolver extends UntypedActor {

    private double startTime = 0.0;
    private int receivedLines = 0;
    private Map<String, Double> result = new TreeMap<>();

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof ResultMessage) {
            ResultMessage resultMessage = (ResultMessage) message;

            String[] lines = resultMessage.getLines();

            for (String line : lines) {
                String arr[] = line.split(":");
                String key = arr[0];
                double value = Double.valueOf(arr[1]);
                result.put(key, result.getOrDefault(key, 0.0) + value);
            }

            receivedLines += lines.length;

            if (receivedLines == ProcessingContext.getInstance().getLinesNumber()) {
                save();
                context().system().shutdown();
            }

            if (resultMessage.getChunkIndex() == 1) startTime = System.currentTimeMillis();
            if (resultMessage.getChunkIndex() == resultMessage.getTotalChunks()) {
                System.out.println("Total Time: " + (System.currentTimeMillis() - startTime));
            }
        }


    }

    private void save() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("src/main/resources/result.txt"));
        result.forEach((k, v) -> {
            try {
                fileWriter.write(k + ":" + v + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.close();
    }
}
