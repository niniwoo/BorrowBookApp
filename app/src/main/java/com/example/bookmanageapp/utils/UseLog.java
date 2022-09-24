package com.example.bookmanageapp.utils;

import android.util.Log;

public class UseLog {
    private static final String TAG = "CSIS";
    private static final boolean ISDEBUG = true;

    public static final void i(String msg) {
        if (ISDEBUG) {
            Log.i(TAG, makeLogBody(msg));
        }
    }

    public static final void v(String msg) {
        if (ISDEBUG) {
            Log.v(TAG, makeLogBody(msg));
        }
    }

    public static final void w(String msg) {
        if (ISDEBUG) {
            Log.w(TAG, makeLogBody(msg));
        }
    }

    public static final void d(String msg) {
        if (ISDEBUG) {
            Log.d(TAG, makeLogBody(msg));
        }
    }

    public static final void e(String msg) {
        if (ISDEBUG) {
            Log.e(TAG, makeLogBody(msg));
        }
    }

    private static String makeLogBody(String logMsg) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("] ");
        sb.append(logMsg);
        return sb.toString();
    }
}
