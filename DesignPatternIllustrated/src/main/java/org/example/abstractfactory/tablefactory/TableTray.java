package org.example.abstractfactory.tablefactory;

import org.example.abstractfactory.factory.Item;
import org.example.abstractfactory.factory.Tray;

import java.util.Iterator;

public class TableTray extends Tray {
    public TableTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<td>");
        sb.append("<table width=\"100%\" border=\"1\"><tr>");
        sb.append("<td bgcolor=\"#cccccc\" align=\"center\" colspan=\"" + super.getTraySize() + "\"><b>" + caption + "</b></td>");
        sb.append("</tr>\n");
        sb.append("<tr>\n");
        Iterator it = super.getTray().iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            sb.append(item.makeHTML());
        }
        sb.append("</tr></table>");
        sb.append("</td>");
        return sb.toString();
    }
}
