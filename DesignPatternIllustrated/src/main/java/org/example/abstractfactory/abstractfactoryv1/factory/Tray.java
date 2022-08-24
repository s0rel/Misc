package org.example.abstractfactory.abstractfactoryv1.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Tray extends Item {
    private List<Item> tray = new ArrayList<>();

    public Tray(String caption) {
        super(caption);
    }

    public List<Item> getTray() {
        return tray;
    }

    public int getTraySize() {
        return tray.size();
    }

    public void add(Item item) {
        tray.add(item);
    }
}
