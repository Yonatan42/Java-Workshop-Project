package com.yoni.javaworkshopprojectserver.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {

    public static boolean LOGGING_ENABLED = true;

    private static final String PREFIX = "[StoreIt] ";

    public static void log(String tag, String message){
        if(!LOGGING_ENABLED) return;
        logInternal(tag, message, false);
    }

    public static void logFormat(String tag, String formattedMessage, Object... params){
        if(!LOGGING_ENABLED) return;
        logInternal(tag, String.format(formattedMessage, params), false);
    }

    public static void logError(String tag, String message){
        logError(tag, message, null);
    }

    public static void logError(String tag, Throwable t){
        logError(tag, null, t);
    }

    public static void logError(String tag, String message, Throwable t){
        if(!LOGGING_ENABLED) return;
        String msg = null;
        if(message != null){
            msg = message;
        }
        if(t != null) {
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);
            t.printStackTrace(printer);
            msg = (msg == null ? "" : msg+"\n")
                    + writer.toString();
            printer.close();
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logInternal(tag, msg, true);
    }

    private static void logInternal(String tag, String message, boolean isError){
        PrintStream out = isError ? System.err : System.out;
        out.println(PREFIX+tag+": "+message);
    }

}
