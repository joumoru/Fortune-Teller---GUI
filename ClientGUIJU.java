import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUIJU {

    private JFrame frameJU;
    private JTextField textFieldJU;
    private JTextArea textAreaJU;
    private JTextField usernameFieldJU;
    private JPasswordField passwordFieldJU;

    private ClientBackendJU backendJU;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ClientGUIJU windowJU = new ClientGUIJU();
                windowJU.frameJU.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ClientGUIJU() {
        backendJU = new ClientBackendJU("localhost", 9999);
        backendJU.connectJU(); // Changed this line
        initializeLoginJU();
    }



    private void initializeLoginJU() {
        frameJU = new JFrame("Joshua's Fortune Teller");
        frameJU.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameJU.setSize(400, 200);
        frameJU.setLocationRelativeTo(null);

        JPanel loginPanelJU = new JPanel(new GridBagLayout());
        frameJU.getContentPane().add(loginPanelJU, BorderLayout.CENTER);

        GridBagConstraints gbcJU = new GridBagConstraints();
        gbcJU.insets = new Insets(5, 5, 5, 5);

        gbcJU.gridx = 0;
        gbcJU.gridy = 0;
        loginPanelJU.add(new JLabel("Username:"), gbcJU);

        gbcJU.gridx = 1;
        usernameFieldJU = new JTextField(20);
        loginPanelJU.add(usernameFieldJU, gbcJU);

        gbcJU.gridx = 0;
        gbcJU.gridy = 1;
        loginPanelJU.add(new JLabel("Password:"), gbcJU);

        gbcJU.gridx = 1;
        passwordFieldJU = new JPasswordField(20);
        loginPanelJU.add(passwordFieldJU, gbcJU);

        JPanel buttonPanelJU = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        frameJU.getContentPane().add(buttonPanelJU, BorderLayout.SOUTH);

        JButton loginButtonJU = new JButton("Login");
        buttonPanelJU.add(loginButtonJU);

        JButton signUpButtonJU = new JButton("Sign Up");
        buttonPanelJU.add(signUpButtonJU);

        // Add button action listeners
        loginButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to send username and password to the server for verification.
                // If the login is successful, show the main application panel.
                frameJU.getContentPane().removeAll();
                frameJU.setSize(600, 300);
                initializeMainPanelJU();
                frameJU.revalidate();
                String usernameJU = usernameFieldJU.getText();
                textAreaJU.append("Hello, " + usernameJU + ". Are you having a good day? \n");

                // Set up the backend connection
                setupBackendJU();
            }
        });

        signUpButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to
                // send username and password to the server for storage.
                // If the sign-up is successful, show the main application panel.
                frameJU.getContentPane().removeAll();
                frameJU.setSize(400, 300);
                initializeMainPanelJU();
                frameJU.revalidate();

                // Set up the backend connection
                setupBackendJU();
            }
        });
    }

    /*private void speakTextJU(String text) {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral
                    ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizerJU = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizerJU.allocate();
            synthesizerJU.resume();

            synthesizerJU.speakPlainText(text, null);
            synthesizerJU.waitEngineState(Synthesizer.QUEUE_EMPTY);

            synthesizerJU.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void initializeMainPanelJU() {
        JPanel panelJU = new JPanel();
        frameJU.getContentPane().add(panelJU, BorderLayout.CENTER);
        panelJU.setLayout(new BorderLayout(0, 0));

        textAreaJU = new JTextArea();
        textAreaJU.setEditable(false);
        textAreaJU.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textAreaJU.setForeground(Color.BLUE);
        textAreaJU.setBackground(Color.LIGHT_GRAY);
        panelJU.add(new JScrollPane(textAreaJU), BorderLayout.CENTER);

        JPanel inputPanelJU = new JPanel();
        panelJU.add(inputPanelJU, BorderLayout.SOUTH);
        inputPanelJU.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        textFieldJU = new JTextField();
        textFieldJU.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanelJU.add(textFieldJU);
        textFieldJU.setColumns(20);

        JButton sendButtonJU = new JButton("Send");
        inputPanelJU.add(sendButtonJU);

        JButton readOutLoudButtonJU = new JButton("Read Out Loud");
        inputPanelJU.add(readOutLoudButtonJU);

        JButton clearButtonJU = new JButton("Clear");
        inputPanelJU.add(clearButtonJU);

        // Add button action listeners
        sendButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to send the message to the server.
                String messageJU = textFieldJU.getText();
                backendJU.sendMessageJU(messageJU); // Changed this line
                textFieldJU.setText("");

                textAreaJU.append("You: " + messageJU + "\n");
            }
        });


        clearButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldJU.setText("");
                textAreaJU.setText("");
            }
        });

        readOutLoudButtonJU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textJU = textAreaJU.getText();
                //speakTextJU(textJU);
            }
        });

        frameJU.revalidate();
    }

    private void setupBackendJU() {
        backendJU = new ClientBackendJU("localhost", 9999);
        backendJU.addMessageListenerJU(new ClientBackendJU.MessageListenerJU() {
            @Override
            public void onMessageReceived(String message) {
                SwingUtilities.invokeLater(() -> textAreaJU.append(message + "\n"));
            }
        });


        backendJU.connectJU();

        backendJU.addMessageListenerJU(new ClientBackendJU.MessageListenerJU() {
            @Override
            public void onMessageReceived(String message) {
                //textAreaJU.append("You: " + message + "\n");
            }
        });

    }
}
