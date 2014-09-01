package com.yyu.fwk.mp3;

import java.io.File;
import java.text.DecimalFormat;

public class MP3NameChanger {
    
    private String folderPath;
    private String oldNamePrefix;
    private String oldNameSuffix;
    private String newNamePrefix;
    private boolean isTestMode;
    
    public MP3NameChanger(String folderPath, String oldNamePrefix, String oldNameSuffix, String newNamePrefix) {
        this.folderPath = folderPath;
        this.oldNamePrefix = oldNamePrefix;
        this.oldNameSuffix = oldNameSuffix;
        this.newNamePrefix = newNamePrefix;
    }
    
    public void doChange() {
        doChange(null);
    }
    
    public void doChange(Skip skip) {
        boolean test = getIsTestMode();
        
        File folder = new File(folderPath);
        
        File[] mp3s = folder.listFiles();
        for (File mp3 : mp3s) {
            String mp3Name = mp3.getName();
            if (skip != null && skip.need(mp3Name)) {
                continue;
            }
            String num = getNumber(mp3Name);
            String newName = newNamePrefix + "_" + num + ".mp3";
            if (!test) {
                File newFile = new File(folderPath + "/" + newName);
                mp3.renameTo(newFile);
            }
        }
    }
    
    private String getNumber(String mp3FileName) {
        int index = mp3FileName.indexOf(oldNamePrefix);
        int strLength = oldNamePrefix.length();
        String num = mp3FileName.substring(index + strLength, mp3FileName.indexOf(oldNameSuffix));
        DecimalFormat df = new DecimalFormat("###000.##");
        num = df.format(Long.valueOf(num));
        System.out.println(num);
        return num;
    }
    
    public boolean getIsTestMode() {
        return isTestMode;
    }

    public void setIsTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }

    interface Skip {
        public boolean need(String arg);
    }
    
    
    public static void main(String[] args) {
        MP3NameChanger changer = new MP3NameChanger("", "", "", "");
        changer.setIsTestMode(true);
        changer.doChange(new Skip() {

            @Override
            public boolean need(String fileName) {
                return fileName.indexOf("DS_Store") > -1;
            }
            
        });
    }
}
