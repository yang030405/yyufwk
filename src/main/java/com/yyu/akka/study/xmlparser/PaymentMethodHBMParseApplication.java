package com.yyu.akka.study.xmlparser;

import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

import com.yyu.akka.study.mapreduce.messages.Result;
import com.yyu.akka.study.xmlparser.actor.AggregateActor;

public class PaymentMethodHBMParseApplication {

    public static void main(String[] args) throws Exception {
        ActorSystem _system = ActorSystem.create("PaymentMethodHBMParser");
        try {
            long start = System.currentTimeMillis();

            Timeout timeout = new Timeout(Duration.parse("5 seconds"));
            ActorRef aggregateActor = _system.actorOf(new Props(AggregateActor.class), "AggregateActor");

            String xmlFilePath = "/Users/yangyu/Desktop/test.xml";
            aggregateActor.tell(xmlFilePath);

            Future<Object> future = Patterns.ask(aggregateActor, new Result(), timeout);
            Object o = Await.result(future, timeout.duration());

            System.out.println(o.toString());

            List<Map<String, String>> result = (List<Map<String, String>>) o;
            System.out.println(result);

            long end = System.currentTimeMillis();

            System.out.println("done. cost " + (end - start) + " ms."); // 2500 +-
        }
        finally {
            _system.shutdown();
        }
    }
}
