package com.yyu.akka.study.xmlparser.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

import com.yyu.akka.study.xmlparser.action.ColumnParseAction;
import com.yyu.akka.study.xmlparser.action.PropertyParseAction;
import com.yyu.akka.study.xmlparser.message.Result;

public class AggregateActor extends UntypedActor {
    
    private List<Map<String, String>> fieldsInfo = new ArrayList<Map<String, String>>();
    
    private ActorRef columnActor = getContext().actorOf(new Props(ColumnActor.class).withRouter(new RoundRobinRouter(5)), "column");
    private ActorRef propertyActor = getContext().actorOf(new Props(PropertyActor.class).withRouter(new RoundRobinRouter(5)), "property");
    
    @SuppressWarnings("unchecked")
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String filePath = (String) message;
            columnActor.tell(new ColumnParseAction(filePath), getSelf());
            propertyActor.tell(new PropertyParseAction(filePath), getSelf());
        } else if (message instanceof List) {
            fieldsInfo.addAll((List<Map<String, String>>)message);
        } else if (message instanceof Result) {
            System.out.println("result size = " + fieldsInfo.size());
            getSender().tell(fieldsInfo);
        } else {
            unhandled(message);
        }
    }
}
