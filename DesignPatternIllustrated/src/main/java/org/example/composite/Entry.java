package org.example.composite;

public abstract class Entry {
    protected Entry parent;

    public abstract String getName();

    public abstract int getSize();

    protected abstract void printList(String prefix);

    public Entry add(Entry entry) throws FileTreatmentException {
        throw new FileTreatmentException();
    }

    public void printList() {
        printList("");
    }

    public String toString() {
        return getName() + "(" + getSize() + ")";
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        Entry entry = this;
        do {
            fullName.insert(0, "/" + entry.getName());
            entry = entry.parent;
        } while (entry != null);
        return fullName.toString();
    }
}
