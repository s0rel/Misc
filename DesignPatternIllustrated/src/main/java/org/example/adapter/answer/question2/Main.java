package org.example.adapter.answer.question2;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileProperties fileProperties = new FileProperties();
        try {
            fileProperties.readFromFile("file.txt");
            fileProperties.setValue("year", "2004");
            fileProperties.setValue("month", "4");
            fileProperties.setValue("day", "21");
            fileProperties.writeToFile("newfile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
