package com.zpop.web.util;

import java.util.Date;

public final class TIME_CONST {
	

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

        if (diffTime < TIME_CONST.SEC)
        {
            // sec
            msg = diffTime + "초전";
        }
        else if ((diffTime /= TIME_CONST.SEC) < TIME_CONST.MIN)
        {
            // min
            msg = diffTime + "분전";
        }
        else if ((diffTime /= TIME_CONST.MIN) < TIME_CONST.HOUR)
        {
            // hour
            msg = (diffTime ) + "시간전";
        }
        else if ((diffTime /= TIME_CONST.HOUR) < TIME_CONST.DAY)
        {
            // day
            msg = (diffTime ) + "일전";
        }
        else if ((diffTime /= TIME_CONST.DAY) < TIME_CONST.MONTH)
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