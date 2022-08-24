package org.example.abstractfactory.abstractfactory.tablefactory;

import org.example.abstractfactory.abstractfactory.factory.Factory;
import org.example.abstractfactory.abstractfactory.factory.Link;
import org.example.abstractfactory.abstractfactory.factory.Page;
import org.example.abstractfactory.abstractfactory.factory.Tray;

public class TableFactory extends Factory {
    public Link createLink(String caption, String url) {
        return new TableLink(caption, url);
    }

    public Tray createTray(String caption) {
        return new TableTray(caption);
    }

    public Page createPage(String title, String author) {
        return new TablePage(title, author);
    }
}
