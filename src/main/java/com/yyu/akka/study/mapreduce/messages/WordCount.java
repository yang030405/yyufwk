package com.yyu.akka.study.mapreduce.messages;

public final class WordCount {
    private final String word;
    private final Integer count;

    public WordCount(String inWord, Integer inCount) {
        word = inWord;
        count = inCount;
    }

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }
}
