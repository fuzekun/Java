package observer.buttonTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingObserverExample {
    JFrame jFrame;

    public static void main(String[] args) {
        SwingObserverExample example = new SwingObserverExample();
        example.go();
    }
    public void go() {
        jFrame = new JFrame();

        JButton button = new JButton("should I do it ? ");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getContentPane().add(BorderLayout.AFTER_LAST_LINE, new TextArea("Don't do it , you might regret it!"));
                System.out.println("Don't do it , you might regret it!");
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jFrame.getContentPane().add(BorderLayout.AFTER_LAST_LINE, new TextArea("Don't do it , you might regret it!"));

                System.out.println("Come on, just do it!");
            }
        });
        jFrame.getContentPane().add(BorderLayout.CENTER, button);
        jFrame.setVisible(true);
        jFrame.setSize(500, 500);
    }
}
