package com.yyu.akka.study.xmlparser.actor;

import java.util.List;
import java.util.Map;

import akka.actor.UntypedActor;

import com.yyu.akka.study.xmlparser.action.ColumnParseAction;

public class ColumnActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ColumnParseAction) {
            ColumnParseAction parser = (ColumnParseAction)message;
            List<Map<String, String>> fieldsInfo = parser.getFiledsInfo();
            getSender().tell(fieldsInfo);
        } else {
            unhandled(message);
        }
    }

}
