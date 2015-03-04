package com.yyu.akka.study.xmlparser.actor;

import java.util.List;
import java.util.Map;

import akka.actor.UntypedActor;

import com.yyu.akka.study.xmlparser.action.PropertyParseAction;

public class PropertyActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PropertyParseAction) {
            PropertyParseAction parser = (PropertyParseAction)message;
            List<Map<String, String>> fieldsInfo = parser.getFiledsInfo();
            getSender().tell(fieldsInfo);
        } else {
            unhandled(message);
        }
    }

}
