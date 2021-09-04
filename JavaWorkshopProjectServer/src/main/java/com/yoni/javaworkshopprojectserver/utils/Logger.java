package com.yoni.javaworkshopprojectserver.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {

    public static void Log(String tag, String message){
        LogInternal(tag, message, false);
    }

    public static void LogError(String tag, String message){
        LogError(tag, message, null);
    }

    public static void LogError(String tag, Throwable t){
        LogError(tag, null, t);
    }

    public static void LogError(String tag, String message, Throwable t){
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
        LogInternal(tag, msg, true);
    }

    private static void LogInternal(String tag, String message, boolean isError){
        PrintStream out = isError ? System.err : System.out;
        out.println(tag+": "+message);
    }

}
