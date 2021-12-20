package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils;

public class TextColorUtils {
    public static final String PATTERN_COLORFUL_STRING = "<html><font color=%s>%s</font></html>";
    public static final String PATTERN_COLORFUL_STRING_WITH_SUP = "<html><font color=%s>%s </font><sup><em><font color=%s>%s</font></em></sup></html>";

    public static String getColorString(String baseString, String color){
        return String.format(PATTERN_COLORFUL_STRING, color, baseString);
    }

    public static String getColorStringWithSupText(String baseString, String color, String supText, String supColor){
        return String.format(PATTERN_COLORFUL_STRING_WITH_SUP, color, baseString, supColor, supText);
    }
}
