package com.yyu.fwk.mp3;

import java.io.File;
import java.text.DecimalFormat;

public abstract class AbstractMP3NameChanger {
    private String mp3FolderPath;
    private String newNamePrefix;
    private DecimalFormat df = new DecimalFormat("###000.##");
    
    public AbstractMP3NameChanger(String mp3FolderPath, String newNamePrefix) {
        this.mp3FolderPath = mp3FolderPath;
        this.newNamePrefix = newNamePrefix;
    }
    
    public void doChange() {
        File mp3Folder = new File(mp3FolderPath);
        
        File[] mp3s = mp3Folder.listFiles();
        for (File mp3 : mp3s) {
            String mp3Name = mp3.getName();
            if (skip(mp3)) {
                continue;
            }
            String num = extractMp3Number(mp3Name);
            num = df.format(Long.valueOf(num));
            String newName = newNamePrefix + "_" + num + ".mp3";
            
            File newFile = new File(mp3.getParent() + "/" + newName);
            mp3.renameTo(newFile);
        }
    }
    
    public abstract boolean skip(File file);
    public abstract String extractMp3Number(String mp3Name);
    
    public static void main(String[] args) {
//        String s = "[有声下吧www.ysx8.com]周建龙《雍正皇帝》01.mp3";
//        String s1 = "》";
//        System.out.println(s.replace(".mp3", "").substring(s.indexOf(s1) + 1));
        
        ZangAoNameChanger c = new ZangAoNameChanger("/Users/yangyu/Music/YongZhengHuangDi", "YongZhengHuangDi");
        c.doChange();
        System.out.println("done");
    }
}

class ZangAoNameChanger extends AbstractMP3NameChanger {

    public ZangAoNameChanger(String mp3FolderPath, String newNamePrefix) {
        super(mp3FolderPath, newNamePrefix);
    }

    @Override
    public boolean skip(File file) {
        String fileName = file.getName();
        return (fileName.contains("DS_Store")) || (fileName.contains(".txt"));
    }

    @Override
    public String extractMp3Number(String mp3Name) {
        mp3Name = mp3Name.replace(".mp3", "");
        return mp3Name.substring(mp3Name.indexOf("》") + 1);
    }
    
    
}