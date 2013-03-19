package ru.proto.samp;

import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import ru.proto.samp.core.TranslateAggregator;
import ru.proto.samp.tr.Translate;

import javax.inject.Inject;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    @Inject
    TranslateAggregator aggregator;

    @Inject
    EventBus bus;

    @Override
    protected void init(VaadinRequest request) {

        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        final Container container = new BeanItemContainer<String>(
                String.class);
        fillContainer(container);
        ComboBox transBox = new ComboBox("translators");
        transBox.setContainerDataSource(container);
        transBox.addFocusListener(new FieldEvents.FocusListener() {
            @Override
            public void focus(FieldEvents.FocusEvent event) {
                fillContainer(container);
            }
        });
        Button button = new Button("Refresh");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Refreshed"));
                fillContainer(container);
            }
        });


        layout.addComponent(button);
        layout.addComponent(transBox);
    }

    private void fillContainer(Container container) {
        container.removeAllItems();
        for(Translate tr: aggregator.getTranslators()){
            container.addItem(tr.getClass().getSimpleName());
        }
    }

}
