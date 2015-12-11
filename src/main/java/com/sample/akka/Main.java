package com.sample.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.sample.akka.core.Akka;
import com.sample.akka.core.ProcessingContext;
import com.sample.akka.messages.StartProcess;

import java.io.IOException;

/**
 * Created by root on 12/11/15.
 */
public class Main {

    static String bigdataFilePath = "src/main/resources/bigdata.txt";

    public static void main(String[] args) throws IOException, InterruptedException {


        ActorRef dataProcessingActor = Akka.system.actorOf(Props.create(DataProcessing.class, ProcessingContext.getInstance()));

        dataProcessingActor.tell(new StartProcess(bigdataFilePath), null);
    }



}
