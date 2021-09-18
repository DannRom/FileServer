import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    // Declare Constants
    static int SERVER_PORT = 10007;
    static String DEFAULT_NEW_FILE_DIRECTORY = "src/newFile.txt"; // If using the command line, remove "src/".

    public static void main(String[] args) {
        System.out.println("Starting Server\nWaiting for connection...");

        // Initialize resource dependent objects as parameters within the try block.
        // All objects will automatically close at the end of the try block.
        // The serverSocket is created first and will wait for a connecting client socket.
        // Once the client has connected, the "in" object is declared to receive communication
        // from the client. A fileWriter is also created.
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter fileWriter = new BufferedWriter(
                     new FileWriter(DEFAULT_NEW_FILE_DIRECTORY))
        ) {
            // If the past connections and object creation are successful, then we
            // are able to proceed in writing the client input into a buffer.
            System.out.println("Connection Successful!\nUpload Processing...");

            // Until the client submits null, the server will wait for further data to be streamed in.
            // Each line of input will be written into the buffer.
            StringBuffer sb = new StringBuffer();
            for (String line = in.readLine(); line != null; line = in.readLine())
                sb.append(line).append("\n");

            // The data within the buffer is written to file.
            fileWriter.append(sb).flush();
            System.out.println("Upload complete.");
        } catch (IOException e) {
            // print detailed error stack trace.
            e.printStackTrace();
        }
    }
}