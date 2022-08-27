package org.example.abstractfactory.tablefactory;

import org.example.abstractfactory.factory.Item;
import org.example.abstractfactory.factory.Page;

import java.util.Iterator;

public class TablePage extends Page {
    public TablePage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
        sb.append("<html><head><title>" + title + "</title></head>\n");
        sb.append("<body>\n");
        sb.append("<h1>" + title + "</h1>\n");
        sb.append("<table width=\"80%\" border=\"3\">\n");
        Iterator it = content.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            sb.append("<tr>" + item.makeHTML() + "</tr>");
        }
        sb.append("</table>\n");
        sb.append("<hr><address>" + author + "</address>");
        sb.append("</body></html>\n");
        return sb.toString();
    }
}
