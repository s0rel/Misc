package org.example.abstractfactory.listfactory;

import org.example.abstractfactory.factory.Item;
import org.example.abstractfactory.factory.Page;

import java.util.Iterator;

public class ListPage extends Page {
    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
        sb.append("<html><head><title>" + title + "</title></head>\n");
        sb.append("<body>\n");
        sb.append("<h1>" + title + "</h1>\n");
        sb.append("<ul>\n");
        Iterator it = content.iterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            sb.append(item.makeHTML());
        }
        sb.append("</ul>\n");
        sb.append("<hr><address>" + author + "</address>");
        sb.append("</body></html>\n");
        return sb.toString();
    }
}
