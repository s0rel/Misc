package org.example.builder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

// TODO: 重构写入
public class HTMLBuilder extends Builder {
    private String filename;
    private OutputStreamWriter writer;

    @Override
    protected void buildTitle(String title) {
        filename = title + ".html";
        try {
            writer = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
            writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
            writer.write("<html><head><title>" + title + "</title></head><body>");
            writer.write("<h1>" + title + "</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void buildString(String str) {
        try {
            writer.write("<p>" + str + "</p>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void buildItems(String[] items) {
        try {
            writer.write("<ul>");
            for (String item : items) {
                writer.write("<li>" + item + "</li>");
            }
            writer.write("</ul>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void close() {
        try {
            writer.write("</body></html>");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return filename;
    }
}
