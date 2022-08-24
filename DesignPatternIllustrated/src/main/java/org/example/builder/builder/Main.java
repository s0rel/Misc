package org.example.builder.builder;


public class Main {
    public static void main(String[] args) {
        Director director;
        BuilderEnum builderEnum = BuilderEnum.HTML;
        switch (builderEnum) {
            case PLAIN:
                TextBuilder textbuilder = new TextBuilder();
                director = new Director(textbuilder);
                director.construct();
                String result = textbuilder.getResult();
                System.out.println(result);
                break;
            case HTML:
                HTMLBuilder htmlbuilder = new HTMLBuilder();
                director = new Director(htmlbuilder);
                director.construct();
                String filename = htmlbuilder.getResult();
                System.out.println(filename + "文件编写完成。");
                break;
            default:
                break;
        }
    }
}
