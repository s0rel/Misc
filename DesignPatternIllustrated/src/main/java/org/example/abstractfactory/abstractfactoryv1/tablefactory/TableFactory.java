package org.example.abstractfactory.abstractfactoryv1.tablefactory;

import org.example.abstractfactory.abstractfactoryv1.factory.Factory;
import org.example.abstractfactory.abstractfactoryv1.factory.Link;
import org.example.abstractfactory.abstractfactoryv1.factory.Page;
import org.example.abstractfactory.abstractfactoryv1.factory.Tray;

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
