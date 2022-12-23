package com.zpop.web.utils;

import java.util.Date;

public final class ElapsedTimeCalculator {
	

        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;

    public static String getElpasedTime(Date date)
    {

        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;

        if (diffTime < ElapsedTimeCalculator.SEC)
        {
            // sec
            msg = diffTime + "초전";
        }
        else if ((diffTime /= ElapsedTimeCalculator.SEC) < ElapsedTimeCalculator.MIN)
        {
            // min
            msg = diffTime + "분전";
        }
        else if ((diffTime /= ElapsedTimeCalculator.MIN) < ElapsedTimeCalculator.HOUR)
        {
            // hour
            msg = (diffTime ) + "시간전";
        }
        else if ((diffTime /= ElapsedTimeCalculator.HOUR) < ElapsedTimeCalculator.DAY)
        {
            // day
            msg = (diffTime ) + "일전";
        }
        else if ((diffTime /= ElapsedTimeCalculator.DAY) < ElapsedTimeCalculator.MONTH)
        {
            // day
            msg = (diffTime ) + "달전";
        }
        else
        {
            msg = (diffTime) + "년전";
        }
        return msg;
    }
}