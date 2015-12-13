package com.sample.akka;

import akka.actor.UntypedActor;
import com.sample.akka.messages.WriteResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by root on 12.12.15.
 */
public class DataWriter extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof WriteResult) {
            WriteResult writeResult = (WriteResult)message;
            FileWriter fileWriter = new FileWriter(new File(writeResult.getFileName()));
            writeResult.getResult().forEach((k, v) -> {
                try {
                    fileWriter.write(k + ":" + String.format("%.3f", v) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
            System.out.println("Saved result to: " + writeResult.getFileName());
            context().system().shutdown();
        }

    }
}
