import java.io.*;
import java.net.*;
import java.util.*;

public class ServerBackendJU {
    // Fortune lists
    private ArrayList<String> positiveFortunes;
    private ArrayList<String> negativeFortunes;

    // Word sets
    private HashSet<String> positiveWords;
    private HashSet<String> negativeWords;

    private ServerGUIJU serverGUIJU;

    public ServerBackendJU(ServerGUIJU serverGUIJU) {
        this.serverGUIJU = serverGUIJU;
    }

    public static void main(String[] args) {
        ServerBackendJU serverJU = new ServerBackendJU(new ServerGUIJU());
        serverJU.initialize();
    }

    private void updateWordCountGUI(int positiveCount, int negativeCount) {
        serverGUIJU.updatePositiveWordCountJU(positiveCount);
        serverGUIJU.updateNegativeWordCountJU(negativeCount);
    }
    public void initialize() {

        // Initialize fortune lists
        positiveFortunes = new ArrayList<String>();
        positiveFortunes.add("You will meet someone special today.");
        positiveFortunes.add("A great opportunity is headed your way.");
        positiveFortunes.add("Your hard work will pay off in the near future.");
        positiveFortunes.add("Good things come to those who wait, and today you will be rewarded.");
        positiveFortunes.add("You will be pleasantly surprised by a gift or kind gesture from someone you know.");
        positiveFortunes.add("Your positive attitude will lead you to success in all areas of your life.");
        positiveFortunes.add("You have the skills and talents to achieve great things, so don't doubt yourself.");
        positiveFortunes.add("Your determination and perseverance will help you overcome any obstacle.");
        positiveFortunes.add("A new adventure awaits you, and it will bring you great joy and happiness.");
        positiveFortunes.add("You will be surrounded by love and positivity today.");
        positiveFortunes.add("You have the power to make a difference in someone's life today.");
        positiveFortunes.add("Your creativity and innovation will be recognized and appreciated by others.");

        negativeFortunes = new ArrayList<String>();
        negativeFortunes.add("You will face a difficult challenge soon.");
        negativeFortunes.add("Be prepared for unexpected setbacks.");
        negativeFortunes.add("You may encounter financial difficulties in the near future.");
        negativeFortunes.add("Your plans may not go as expected, and you will need to adjust accordingly.");
        negativeFortunes.add("A relationship in your life may experience some turbulence.");
        negativeFortunes.add("You may experience some health problems or physical discomfort in the near future.");
        negativeFortunes.add("A difficult decision lies ahead, and you may struggle to make the right choice.");
        negativeFortunes.add("You may encounter conflict or disagreement with someone close to you.");
        negativeFortunes.add("You may feel a sense of loss or disappointment in a situation that is important to you.");
        negativeFortunes.add("Your efforts may not yield the results you were hoping for, and you may feel discouraged.");
        negativeFortunes.add("You may experience a sense of isolation or loneliness in the near future.");
        negativeFortunes.add("You may encounter obstacles or delays in achieving your goals.");

        // Initialize word sets
        positiveWords = new HashSet<String>();
        positiveWords.add("happy");
        positiveWords.add("joyful");
        positiveWords.add("excited");
        positiveWords.add("blissful");
        positiveWords.add("ecstatic");
        positiveWords.add("content");
        positiveWords.add("euphoric");
        positiveWords.add("elated");
        positiveWords.add("grateful");
        positiveWords.add("radiant");
        positiveWords.add("thrilled");
        positiveWords.add("serene");
        positiveWords.add("blessed");
        positiveWords.add("exhilarated");
        positiveWords.add("gleeful");
        positiveWords.add("overjoyed");
        positiveWords.add("jubilant");
        positiveWords.add("uplifted");
        positiveWords.add("satisfied");
        positiveWords.add("peaceful");
        positiveWords.add("delighted");
        positiveWords.add("enthusiastic");

        negativeWords = new HashSet<String>();
        negativeWords.add("sad");
        negativeWords.add("angry");
        negativeWords.add("frustrated");
        negativeWords.add("depressed");
        negativeWords.add("frustrated");
        negativeWords.add("anxious");
        negativeWords.add("worried");
        negativeWords.add("irritated");
        negativeWords.add("disappointed");
        negativeWords.add("discouraged");
        negativeWords.add("dismayed");
        negativeWords.add("miserable");
        negativeWords.add("heartbroken");
        negativeWords.add("hopeless");
        negativeWords.add("furious");
        negativeWords.add("enraged");
        negativeWords.add("outraged");
        negativeWords.add("resentful");
        negativeWords.add("bitter");
        negativeWords.add("hateful");
        negativeWords.add("hostile");
        negativeWords.add("aggressive");
        negativeWords.add("defeated");

        // Create and start server thread
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(9999);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try {
            // Get input and output streams from socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read and store the username from the client
            //String username = in.readLine();

            // Send initial message to client
            //String initialMessage = "Hello, " + username + ". Are you having a good day?";
            //out.println(initialMessage);

            // Read client response
            String clientResponse = in.readLine();

            String repeatResponse;
            boolean sendFortune = true;
            while (sendFortune) {
                // Count positive and negative words
                int positiveCount = 0;
                int negativeCount = 0;
                String[] words = clientResponse.split("\\s+");
                for (String word : words) {
                    if (positiveWords.contains(word.toLowerCase())) {
                        positiveCount++;
                    } else if (negativeWords.contains(word.toLowerCase())) {
                        negativeCount++;
                    }
                }
                // Update word count fields in GUI
                updateWordCountGUI(positiveCount, negativeCount);

                // Determine fortune type and select fortune
                Random random = new Random();
                ArrayList<String> fortunes = new ArrayList<String>();
                if (positiveCount > negativeCount) {
                    fortunes.addAll(positiveFortunes);
                    fortunes.addAll(negativeFortunes);
                } else if (negativeCount > positiveCount) {
                    fortunes.addAll(positiveFortunes);
                } else {
                    fortunes.addAll(positiveFortunes);
                    fortunes.addAll(negativeFortunes);
                }
                String fortune = fortunes.get(random.nextInt(fortunes.size()));

                // Send fortune to client
                String fortuneMessage = "Ok, give me a minute to consult my crystal ball.";
                out.println(fortuneMessage);
                Thread.sleep(5000);
                String fortuneResponse = "I see " + fortune;
                out.println(fortuneResponse);

                // Wait for client response
                String repeatMessage = "Do you want me to try again? Yes or No?";
                out.println(repeatMessage);

                // Read client response
                repeatResponse = in.readLine();

                // If the client says "Yes", set the flag to true and read another client response to be used in the next iteration
                sendFortune = repeatResponse.trim().equalsIgnoreCase("Yes");
                String message = "Tell me about your day";
                out.println(message);
                if (sendFortune) {
                    clientResponse = in.readLine();
                }
            }

            // Send goodbye message to client
            String goodbyeMessage = "Goodbye";
            out.println(goodbyeMessage);

            // Close socket
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

