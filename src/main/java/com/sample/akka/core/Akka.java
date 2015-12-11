package com.sample.akka.core;

import akka.actor.ActorSystem;

/**
 * Created by root on 12/11/15.
 */
public class Akka {
    public static final ActorSystem system = ActorSystem.apply("application");
}
