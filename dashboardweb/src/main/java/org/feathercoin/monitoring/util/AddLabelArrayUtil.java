package org.feathercoin.monitoring.util;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.PropertyModel;

public class AddLabelArrayUtil {
    public static void addLabels(ListItem component,WebMarkupContainer toAdd, String... labels){
        for (String label : labels) {
            toAdd.add(new Label(label, new PropertyModel(component.getModel(), label)));
        }
    }

    public static void addLabels(ListItem component, String... labels){
        addLabels(component,component,labels);
    }
}
