package org.example.builder.builderv1;

public abstract class Builder {
    private boolean initialized = false;

    public void makeTitle(String title) {
        if (!initialized) {
            buildTitle(title);
            initialized = true;
        }
    }

    public void makeString(String string) {
        if (initialized) {
            buildString(string);
        }
    }

    public void makeItems(String[] items) {
        if (initialized) {
            buildItems(items);
        }
    }

    protected abstract void buildTitle(String title);

    protected abstract void buildString(String string);

    protected abstract void buildItems(String[] items);

    protected abstract void close();
}
