package org.example.strategy.answer.question2;

// TODO: 重构为泛型
public class Main {
    public static void main(String[] args) {
        String[] data = {
                "Dumpty", "Bowman", "Carroll", "Elfland", "Alice",
        };
        SortAndPrint sap = new SortAndPrint(data, new SelectionSorter());
        sap.execute();
    }
}
