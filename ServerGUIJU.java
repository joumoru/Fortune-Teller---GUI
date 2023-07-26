import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUIJU {

    private JFrame frameJU;
    private JTextField statusTextFieldJU;
    private JTextField positiveWordCountTextFieldJU;
    private JTextField negativeWordCountTextFieldJU;

    private ServerBackendJU serverJU;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ServerGUIJU window = new ServerGUIJU();
                window.frameJU.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ServerGUIJU() {
        initializeJU();
    }

    public void updatePositiveWordCountJU(int count) {
        positiveWordCountTextFieldJU.setText(Integer.toString(count));
    }

    public void updateNegativeWordCountJU(int count) {
        negativeWordCountTextFieldJU.setText(Integer.toString(count));
    }

    private void initializeJU() {
        frameJU = new JFrame("Server GUI");
        frameJU.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameJU.setSize(400, 200);
        frameJU.setLocationRelativeTo(null);

        JPanel mainPanelJU = new JPanel(new GridBagLayout());
        frameJU.getContentPane().add(mainPanelJU, BorderLayout.CENTER);

        GridBagConstraints gbcJU = new GridBagConstraints();
        gbcJU.insets = new Insets(5, 5, 5, 5);

        gbcJU.gridx = 0;
        gbcJU.gridy = 0;
        mainPanelJU.add(new JLabel("Status:"), gbcJU);

        gbcJU.gridx = 1;
        statusTextFieldJU = new JTextField(20);
        statusTextFieldJU.setText("Not connected");
        statusTextFieldJU.setEditable(false);
        mainPanelJU.add(statusTextFieldJU, gbcJU);

        gbcJU.gridx = 0;
        gbcJU.gridy = 1;
        mainPanelJU.add(new JLabel("Positive word count:"), gbcJU);

        gbcJU.gridx = 1;
        positiveWordCountTextFieldJU = new JTextField(20);
        positiveWordCountTextFieldJU.setEditable(false);
        mainPanelJU.add(positiveWordCountTextFieldJU, gbcJU);

        gbcJU.gridx = 0;
        gbcJU.gridy = 2;
        mainPanelJU.add(new JLabel("Negative word count:"), gbcJU);

        gbcJU.gridx = 1;
        negativeWordCountTextFieldJU = new JTextField(20);
        negativeWordCountTextFieldJU.setEditable(false);
        mainPanelJU.add(negativeWordCountTextFieldJU, gbcJU);

        JPanel buttonPanelJU = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        frameJU.getContentPane().add(buttonPanelJU, BorderLayout.SOUTH);

        JButton startButtonJU = new JButton("Start");
        buttonPanelJU.add(startButtonJU);

        startButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the server
                serverJU = new ServerBackendJU(ServerGUIJU.this);
                serverJU.initialize();

                // Update status text field
                statusTextFieldJU.setText("Connected");
            }
        });
    }
}
