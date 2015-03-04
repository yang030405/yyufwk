package com.yyu.akka.study.mapreduce.actors;

import java.util.HashMap;
import java.util.List;

import akka.actor.UntypedActor;

import com.yyu.akka.study.mapreduce.messages.MapData;
import com.yyu.akka.study.mapreduce.messages.ReduceData;
import com.yyu.akka.study.mapreduce.messages.WordCount;

public class ReduceActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        ActorHelper.sleep1Second();
        if (message instanceof MapData) {
            ActorHelper.printInfo(ReduceActor.class);
            MapData mapData = (MapData) message;
            // reduce the incoming data and forward the result to Master actor
            getSender().tell(reduce(mapData.getDataList()));
        }
        else {
            unhandled(message);
        }
    }

    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String, Integer> reducedMap = new HashMap<String, Integer>();
        for (WordCount wordCount : dataList) {
            if (reducedMap.containsKey(wordCount.getWord())) {
                Integer value = (Integer)reducedMap.get(wordCount.getWord());
                value++;
                reducedMap.put(wordCount.getWord(), value);
            } else {
                reducedMap.put(wordCount.getWord(), Integer.valueOf(1));
            }
        }
        return new ReduceData(reducedMap);
    }

}
