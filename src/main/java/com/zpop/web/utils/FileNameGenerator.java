package com.zpop.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNameGenerator {

    private String prefix;
    private String extension;

    public FileNameGenerator(String prefix, String extension){
        this.prefix = prefix;
        this.extension = extension;
    }

    public String getFileNameWithDateTime(){
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS"); 
        String dateString = simpleDateFormat.format(currentDate);

        return String.format("%s_%s.%s",this.prefix,dateString,this.extension);
    }
}
