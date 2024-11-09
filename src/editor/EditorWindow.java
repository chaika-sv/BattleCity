package editor;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class EditorWindow {

    private JFrame jFrame;

    public EditorWindow(EditorPanel editorPanel) {

        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(editorPanel);
        jFrame.setResizable(false);
        jFrame.pack();                          // fit the size of the window to the size of component (gamePanel)
        jFrame.setLocationRelativeTo(null);     // center
        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
                                          @Override
                                          public void windowGainedFocus(WindowEvent e) {
                                          }

                                          @Override
                                          public void windowLostFocus(WindowEvent e) {
                                              System.out.println("BYEEE!");
                                              editorPanel.getEditor().windowFocusLost();
                                          }
                                      }

        );
    }
}
