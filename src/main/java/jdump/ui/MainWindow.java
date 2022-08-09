package jdump.ui;

import jdump.Info;
import jdump.dump.Configuration;
import jdump.dump.Dumps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow {

    final Configuration.Mutable arguments = new Configuration.Mutable();

    public static void create() {
        SwingUtilities.invokeLater(() -> new MainWindow().renderMainWindow());
    }

    private void renderMainWindow() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = createFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel vmLabel = new JLabel("jdump " + Info.getVersion() + " running on " + Info.getJVMInfo());
        vmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(vmLabel);


        JPanel buttons = new JPanel();
        addButton(buttons, "Dump heap", e -> {
            arguments.wantHeapDumpForAll();
            dump();
        });
        addButton(buttons, "Dump threads", e -> {
            arguments.wantThreadDumpForAll();
            dump();
        });

        addButton(buttons, "Dump n second JFR", e -> {
            arguments.wantJfrForAll();
            dump();
        });
        buttons.add(jfrIntervalTextField());
        JLabel secondLabel = new JLabel("s");
        buttons.add(secondLabel);

        addButton(buttons, "Dump NMT", e -> {
            arguments.wantNmtForAll();
            dump();
        });

        addButton(buttons, "Dump all", e -> {
            arguments.wantHeapDumpForAll();
            arguments.wantThreadDumpForAll();
            arguments.wantJfrForAll();
            arguments.wantNmtForAll();
            dump();
        });

        mainPanel.add(buttons);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("jdump");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Dumps.detach();
            }
        });
        return frame;
    }

    private JTextField jfrIntervalTextField() {
        JTextField jfrInterval = new JTextField(3);
        jfrInterval.setText(Long.toString(arguments.jfrDuration().toSeconds()));
        jfrInterval.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {}

            @Override
            public void focusLost(FocusEvent focusEvent) {
                arguments.jfrDuration(Long.parseLong(jfrInterval.getText()));
            }
        });
        return jfrInterval;
    }

    private void dump() {
        Dumps.handle(arguments.makeImmutable());
    }

    private void addButton(JPanel buttons, String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        buttons.add(button);
    }
}
