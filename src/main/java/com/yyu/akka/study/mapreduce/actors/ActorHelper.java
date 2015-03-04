package com.yyu.akka.study.mapreduce.actors;

import java.util.Date;

public class ActorHelper {
    public static void printInfo(Class clazz) {
        System.out.println(clazz.getSimpleName() + " is invoked at " + new Date());
    }
    
    public static void sleep1Second() throws Exception {
        Thread.sleep(1000 * 1);
    }
}
