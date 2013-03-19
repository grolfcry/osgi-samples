package ru.proto.samp.tr.go;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import ru.proto.samp.tr.Translate;

import java.util.Map;

/**
 * User: fmv
 * Date: 14.03.13
 * Time: 14:31
 */
@Component
@Provides
public class GoogleTranslate implements Translate {

    private static final String message = "Сервис стал платным :(";

    @Override
    public String translate(String text) {
        return message;
    }

    @Override
    public String translate(String text, String from, String to) {
        return message;
    }

    @Override
    public Map<String, String> getSupportedLangs() {
        throw new RuntimeException(message);
    }
}
