package com.yuanlai.agent.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Calendar;

public class DateUtils {


    public static String GETDATETIME(String args) {
        if(StringUtils.isEmpty(args)) {
            return null;
        } else {
            String[] buf1 = new String[]{"YYYY", "YY", "MM", "DD", "HH", "MI", "SS"};
            String[] buf2 = new String[]{"yyyy", "yy", "MM", "dd", "HH", "mm", "ss"};
            String str = args.trim();

            for(int calendar = 0; calendar < buf1.length; ++calendar) {
                str = StringUtils.replace(str, buf1[calendar], buf2[calendar]);
            }

            Calendar var5 = Calendar.getInstance();
            return DateFormatUtils.format(var5.getTime(), str);
        }
    }

    public static String GETDATETIME() {
        return GETDATETIME("yyyyMMDDHHmmss");
    }

    public static String GETDATE() {
        return GETDATETIME("yyyyMMdd");
    }
}
