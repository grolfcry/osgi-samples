package ru.proto.samp.tr;

import java.util.Map;

/**
 * User: fmv
 * Date: 14.03.13
 * Time: 14:19
 */
public interface Translate {
    public String translate(String text);
    public String translate(String text, String from, String to);
    public Map<String,String> getSupportedLangs();
}
