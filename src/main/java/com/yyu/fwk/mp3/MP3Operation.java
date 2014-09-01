package com.yyu.fwk.mp3;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.FrameBodyTIT2;
import org.farng.mp3.id3.ID3v1;

public class MP3Operation {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/yangyu/Movies/百度云同步盘/Yang's/download/鬼吹灯1精绝古城/JingJueGuCheng001.mp3";
        
//        readTag(filePath);
        readAsID3V1();
        
//        MP3File mp3 = new MP3File(filePath);
//        AbstractID3v2 tag = mp3.getID3v2Tag();
//        AbstractID3v2Frame frame = tag.getFrame("TIT2");
//        
//        ((FrameBodyTIT2) frame.getBody()).setText("New Title");
//        mp3.save();
//        
//        readTag(filePath);
    }
    
    private static void readTag(String filePath) throws Exception {
        MP3File mp3 = new MP3File(filePath);
        AbstractID3v2 tag = mp3.getID3v2Tag();
        for (Object v : tag.values()) {
            System.out.println(v);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
    
    private static void readAsID3V1() throws Exception {
        String filePath = "/Users/yangyu/Movies/百度云同步盘/Yang's/download/鬼吹灯1精绝古城/JingJueGuCheng001.mp3";
        MP3File mp3 = new MP3File(filePath);
        ID3v1 tag = mp3.getID3v1Tag();
        System.out.println("album                 = " + new String(tag.getAlbum().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("album title           = " + new String(tag.getAlbumTitle().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("artist                = " + new String(tag.getArtist().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("comment               = " + new String(tag.getComment().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("genre                 = " + tag.getGenre());
        System.out.println("identifier            = " + new String(tag.getIdentifier().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("lead artist           = " + new String(tag.getLeadArtist().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("size                  = " + tag.getSize());
        System.out.println("song comment          = " + new String(tag.getSongComment().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("song title            = " + new String(tag.getSongTitle().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("title                 = " + new String(tag.getTitle().getBytes("ISO-8859-1"), "GB2312"));
//        System.out.println("track number on album = " + new String(tag.getTrackNumberOnAlbum().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("year                  = " + new String(tag.getYear().getBytes("ISO-8859-1"), "GB2312"));
        System.out.println("year released         = " + new String(tag.getYearReleased().getBytes("ISO-8859-1"), "GB2312"));
        
        tag.setTitle("JingJueGuCheng001");
        
        mp3.setID3v1Tag(tag);
        mp3.save(filePath);
    }
    
    
    
    
}
