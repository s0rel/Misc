package org.example.decorator;

import java.util.ArrayList;
import java.util.List;

public class MultiStringDisplay extends Display {
    private List<String> body = new ArrayList<>();
    private int columns = 0;

    public void add(String msg) {
        body.add(msg);
        updateColumn(msg);
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return body.size();
    }

    @Override
    public String getRowText(int row) {
        return body.get(row);
    }

    private void updateColumn(String msg) {
        if (msg.getBytes().length > columns) {
            columns = msg.length();
        }
        for (int row = 0; row < body.size(); row++) {
            int fills = columns - body.get(row).length();
            if (fills > 0) {
                body.set(row, body.get(row) + spaces(fills));
            }
        }
    }

    private String spaces(int count) {
        return " ".repeat(Math.max(0, count));
    }
}
