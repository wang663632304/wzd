package org.yftx.wzd.utils;

import android.util.Log;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-19
 */
public class Logger {
    public static int LOG_LEVEL = 9;
    public static int LOG_DEBUG = 3;
    public static int LOG_ERROR = 4;
    private static String tag = "wzd";

    public static void d( String msg) {
        if (LOG_LEVEL > LOG_DEBUG)
            Log.d(tag, msg);
    }


    public static void e( String msg) {
        if (LOG_LEVEL > LOG_ERROR)
            Log.e(tag, msg);
    }
}
