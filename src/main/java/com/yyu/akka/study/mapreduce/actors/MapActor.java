package com.yyu.akka.study.mapreduce.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.UntypedActor;

import com.yyu.akka.study.mapreduce.messages.MapData;
import com.yyu.akka.study.mapreduce.messages.WordCount;

public class MapActor extends UntypedActor {

    private String[] STOP_WORDS = { "a", "am", "an", "and", "are", "as", "at", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to" };
    private List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

    @Override
    public void onReceive(Object message) throws Exception {
        ActorHelper.sleep1Second();
        if (message instanceof String) {
            ActorHelper.printInfo(MapActor.class);
            String work = (String) message;
            // map the words in the sentence and send the result to MasterActor
            getSender().tell(evaluateExpression(work));
        }
        else {
            unhandled(message);
        }
    }

    private MapData evaluateExpression(String line) {
        List<WordCount> dataList = new ArrayList<WordCount>();
        StringTokenizer parser = new StringTokenizer(line);
        while (parser.hasMoreTokens()) {
            String word = parser.nextToken().toLowerCase();
            if (!STOP_WORDS_LIST.contains(word)) {
                dataList.add(new WordCount(word, Integer.valueOf(1)));
            }
        }
        return new MapData(dataList);
    }
}
