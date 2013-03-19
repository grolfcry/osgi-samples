package ru.proto.samp.core.impl;

import org.apache.felix.ipojo.annotations.*;
import ru.proto.samp.core.TranslateAggregator;
import ru.proto.samp.tr.Translate;

import java.util.List;

/**
 * User: fmv
 * Date: 14.03.13
 * Time: 14:56
 */
@Component( name = "TranslateAggregatorService")
@Provides
public class TranslateAggregatorService implements TranslateAggregator {
    @Requires(specification = "ru.proto.samp.tr.Translate")
    private List<Translate> translators;

    @Override
    public List<Translate> getTranslators() {
        return translators;
    }

    @Validate
    public void starting() {
        System.out.println("starting");
    }

    @Invalidate
    public void stopping() {
        System.out.println("stopping");
    }
}
