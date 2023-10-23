package it.finanze.entrate.coopint.dboard.dpi.utils;

import java.util.List;

public class ParamUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.equals("") || s.equals("null");
    }

    public static boolean isNullOrEmpty(List<?> s) {
        return s == null || s.isEmpty();
    }

}