package org.example.abstractfactory.abstractfactoryv1.factory;

public abstract class Item implements HTMLable {
    protected String caption;

    public Item(String caption) {
        this.caption = caption;
    }
}
