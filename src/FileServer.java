import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.StandardCopyOption;

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
             InputStream inputStream = clientSocket.getInputStream()
        ) {
            // After a successful connection the client will upload a file
            // that is then written to a designated file location
            System.out.println("Connection successful.\nWriting file data...");
            File outputFile = new File(DEFAULT_NEW_FILE_DIRECTORY);

            // To write the inputStream, the nio (New IO) standard library is used.
            java.nio.file.Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File upload complete.");
        } catch (IOException e) {
            // print detailed error stack trace.
            e.printStackTrace();
        }
    }
}