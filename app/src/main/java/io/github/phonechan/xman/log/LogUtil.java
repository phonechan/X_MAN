package io.github.phonechan.xman.log;

import android.util.Log;

import io.github.phonechan.xman.AppConfig;

/**
 * Created by chenfeng on 16/2/2.
 */
public class LogUtil {

    private static boolean debug = AppConfig.DEBUG;

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }
}
