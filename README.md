# Fortune-Teller---GUI
This Java application, consisting of multiple classes, is a chat-based fortune telling service with a GUI. The application uses a server-client architecture where the client communicates with the server and receives a fortune based on their chat inputs.

Fortune Teller
This is a Java-based client-server application that features a chat-based fortune telling service with a GUI. The server analyzes the client's messages, determines the mood of the messages, and sends back a fortune (either positive or negative) based on the detected mood.

How to run
Before running, ensure you have Java installed on your system.

First, you need to start the server. Compile the ServerBackendJU and ServerGUIJU classes, and run the ServerGUIJU class:
bash
Copy code
javac ServerBackendJU.java ServerGUIJU.java
java ServerGUIJU
Once the server is running, you can start the client. Compile the ClientBackendJU and ClientGUIJU classes, and run the ClientGUIJU class:
bash
Copy code
javac ClientBackendJU.java ClientGUIJU.java
java ClientGUIJU
You should now see the GUI for the client. Enter your username and password, then start chatting with the server by sending messages. The server will respond with a fortune based on your messages.

Dependencies
Java JDK: This project requires the Java Development Kit. You can download it from https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
Note
This application is for entertainment purposes only. The fortunes provided by the server are randomly generated and do not have any basis in reality.
