package org.example.composite;

import java.util.ArrayList;
import java.util.List;

public class Directory extends Entry {
    private String name;

    private List<Entry> entryList = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int size = 0;
        for (Entry entry : entryList) {
            size += entry.getSize();
        }
        return size;
    }

    @Override
    public Entry add(Entry entry) throws FileTreatmentException {
        entryList.add(entry);
        entry.parent = this;
        return this;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Entry entry : entryList) {
            entry.printList(prefix + "/" + name);
        }
    }
}
