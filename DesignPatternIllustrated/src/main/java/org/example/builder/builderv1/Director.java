package org.example.builder.builderv1;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.buildTitle("Greeting");
        builder.makeString("从早上到下午");
        builder.buildItems(new String[]{
                "早上好。",
                "下午好。"
        });
        builder.makeString("晚上");
        builder.buildItems(new String[]{
                "晚上好。",
                "晚安。",
                "再见。"
        });
        builder.close();
    }
}
