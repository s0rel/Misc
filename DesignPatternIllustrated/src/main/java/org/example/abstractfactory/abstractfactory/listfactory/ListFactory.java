package org.example.abstractfactory.abstractfactory.listfactory;

import org.example.abstractfactory.abstractfactory.factory.Factory;
import org.example.abstractfactory.abstractfactory.factory.Link;
import org.example.abstractfactory.abstractfactory.factory.Page;
import org.example.abstractfactory.abstractfactory.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
