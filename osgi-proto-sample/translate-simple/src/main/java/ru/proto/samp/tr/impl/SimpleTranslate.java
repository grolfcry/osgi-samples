package ru.proto.samp.tr.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import ru.proto.samp.tr.Translate;

import java.util.HashMap;
import java.util.Map;

/**
 * User: fmv
 * Date: 14.03.13
 * Time: 14:21
 */
@Component
@Provides
public class SimpleTranslate implements Translate {

    private final Map<String, String> langs = new HashMap<String, String>();

    public SimpleTranslate() {
        langs.put("ru", "en");
        langs.put("en", "ru");
    }

    @Override
    public String translate(String text) {
        return "Привет )";
    }

    @Override
    public String translate(String text, String from, String to) {
        if (to.equals("ru")) {
            return translate(text);
        }
        if (to.equals("en")) {
            return "Hello )";
        }
        return "undef";
    }

    @Override
    public Map<String, String> getSupportedLangs() {
        return langs;
    }
}
