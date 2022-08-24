package org.example.builder.builderv1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Director director;
        BuilderEnum builderEnum = BuilderEnum.FRAME;
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
            case FRAME:
                FrameBuilder frameBuilder = new FrameBuilder();
                director = new Director(frameBuilder);
                director.construct();
                JFrame frame = frameBuilder.getResult();
                frame.setVisible(true);
                break;
            default:
                break;
        }
    }
}
