package org.example.builder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// TODO: buildString不起作用
public class FrameBuilder extends Builder implements ActionListener {
    private final JFrame frame = new JFrame();

    private Box box = new Box(BoxLayout.Y_AXIS);


    @Override
    protected void buildTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    protected void buildString(String string) {
        box.add(new JLabel(string));
    }

    @Override
    protected void buildItems(String[] items) {
        Box innerBox = new Box(BoxLayout.Y_AXIS);
        for (String item : items) {
            JButton button = new JButton(item);
            button.addActionListener(this);
            innerBox.add(button);
        }
        box.add(innerBox);
    }

    @Override
    protected void close() {
        frame.getContentPane().add(box);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public JFrame getResult() {
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
