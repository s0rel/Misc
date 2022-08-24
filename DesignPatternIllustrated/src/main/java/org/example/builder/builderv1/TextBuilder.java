package org.example.builder.builderv1;

public class TextBuilder extends Builder {
    private StringBuffer buffer = new StringBuffer();

    @Override
    protected void buildTitle(String title) {
        buffer.append("==============================\n");
        buffer.append("『" + title + "』\n");
        buffer.append("\n");
    }

    @Override
    protected void buildString(String str) {
        buffer.append('■' + str + "\n");
        buffer.append("\n");
    }

    @Override
    protected void buildItems(String[] items) {
        for (String item : items) {
            buffer.append("　・" + item + "\n");
        }
        buffer.append("\n");
    }

    protected void close() {
        buffer.append("==============================\n");
    }

    public String getResult() {
        return buffer.toString();
    }
}
