import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientBackendJU {
    private Socket clientSocketJU;
    private BufferedReader inJU;
    private PrintWriter outJU;
    private String hostnameJU;
    private int portJU;
    private List<MessageListenerJU> messageListenersJU = new ArrayList<>();

    public ClientBackendJU(String hostname, int port) {
        this.hostnameJU = hostname;
        this.portJU = port;
    }

    public void connectJU() {
        try {
            clientSocketJU = new Socket(hostnameJU, portJU);

            inJU = new BufferedReader(new InputStreamReader(clientSocketJU.getInputStream()));
            outJU = new PrintWriter(clientSocketJU.getOutputStream(), true);

            // Start a new thread to listen for incoming messages
            Thread messageListenerThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = inJU.readLine()) != null) {
                        for (MessageListenerJU listener : messageListenersJU) {
                            listener.onMessageReceived(serverMessage);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            messageListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessageListenerJU(MessageListenerJU listener) {
        messageListenersJU.add(listener);
    }

    public void sendMessageJU(String message) {
        if (outJU != null) {
            outJU.println(message);
        }
    }

    public void closeConnectionJU() {
        try {
            if (inJU != null) {
                inJU.close();
            }
            if (outJU != null) {
                outJU.close();
            }
            if (clientSocketJU != null) {
                clientSocketJU.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MessageListenerJU {
        void onMessageReceived(String message);
    }

    public static void main(String[] args) {
        ClientBackendJU client = new ClientBackendJU("localhost", 9999);
        client.connectJU();

        Scanner scanner = new Scanner(System.in);
        client.addMessageListenerJU(new MessageListenerJU() {
            @Override
            public void onMessageReceived(String message) {
                System.out.println(message);
            }
        });

        while (true) {
            String userInput = scanner.nextLine();
            client.sendMessageJU(userInput);

            if (userInput.trim().equalsIgnoreCase("exit")) {
                break;
            }
        }

        client.closeConnectionJU();
    }
}
