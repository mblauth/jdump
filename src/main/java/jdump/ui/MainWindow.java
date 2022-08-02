package jdump.ui;

import jdump.Info;
import jdump.dump.Dumps;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainWindow {
    public static void create() {
        SwingUtilities.invokeLater(MainWindow::renderMainWindow);
    }

    private static void renderMainWindow() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("jdump");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JLabel vmLabel = new JLabel(Info.getJVMInfo());
        mainPanel.add(vmLabel);

        JPanel buttons = new JPanel();
        addButton(buttons, "Dump heap and threads", e -> { Dumps.heapDump(); Dumps.threadDump(); });
        addButton(buttons, "Dump heap", e -> Dumps.heapDump());
        addButton(buttons, "Dump threads", e -> Dumps.threadDump());

        mainPanel.add(buttons);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addButton(JPanel buttons, String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        buttons.add(button);
    }
}
