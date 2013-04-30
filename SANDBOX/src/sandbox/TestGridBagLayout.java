// with a thread
package sandbox;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestGridBagLayout {

    public static void main(String[] args) {
        new TestGridBagLayout();
    }

    public TestGridBagLayout() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new ExamplePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    protected class ExamplePane extends JPanel {

        public ExamplePane() {
            setLayout(new GridBagLayout());

            JPanel buttonPane = new JPanel(new GridBagLayout());

            JButton btnOkay = new JButton("Ok");
            JButton btnCancel = new JButton("Cancel");

            JTextArea textArea = new JTextArea(5, 20);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            buttonPane.add(btnOkay, gbc);
            gbc.gridy++;
            gbc.insets = new Insets(50, 0, 0, 0);
            buttonPane.add(btnCancel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(100, 100, 100, 100);
            add(buttonPane, gbc);

            gbc.insets = new Insets(150, 100, 150, 100);
            gbc.gridx++;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            add(new JScrollPane(textArea), gbc);                
        }            
    }        
}