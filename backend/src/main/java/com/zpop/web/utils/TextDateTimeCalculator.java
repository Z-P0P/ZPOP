package com.zpop.web.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class TextDateTimeCalculator {

    public static String getTextDateTime(Date date) {
        StringBuilder dateTime = new StringBuilder();

        dateTime.append(getWeekInfo(date));

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String textDateTime = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG_FORMAT, Locale.KOREAN);
        dateTime.append(textDateTime);

        return dateTime.toString();
    }

    public static String getWeekInfo(Date date){
        boolean isThisWeek = isDateInCurrentWeek(date);
        if(isThisWeek)
            return "이번주 ";
      
        boolean isNextWeek = isDateInNextWeek(date);
        if(isNextWeek)
            return "다음주 ";
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DATE));
        
        return month + "." + day + " ";
    }

    private static boolean isDateInCurrentWeek(Date date) {
        Date monday = getThisMonday();
        Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);
        return date.after(monday) && date.before(nextMonday);
    }

    private static boolean isDateInNextWeek(Date date) {
        Date monday = getThisMonday();
        Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);
        Date monWeekAfterNext = new Date(nextMonday.getTime()+7*24*60*60*1000);
        return date.after(nextMonday) && date.before(monWeekAfterNext);
    }

    private static Date getThisMonday() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
