package org.example.abstractfactory.listfactory;

import org.example.abstractfactory.factory.Item;
import org.example.abstractfactory.factory.Tray;

import java.util.Iterator;

public class ListTray extends Tray {
    public ListTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<li>\n");
        sb.append(caption + "\n");
        sb.append("<ul>\n");
        Iterator it = super.getTray().iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            sb.append(item.makeHTML());
        }
        sb.append("</ul>\n");
        sb.append("</li>\n");
        return sb.toString();
    }
}
