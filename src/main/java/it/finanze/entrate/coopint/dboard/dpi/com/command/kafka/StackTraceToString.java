package it.finanze.entrate.coopint.dboard.dpi.com.command.kafka;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceToString {


    public static String convert(Throwable e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
