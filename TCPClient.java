import java.io.*;
import java.net.*;


public class TCPClient {
  public static void main(String... args) {
    String host = "localhost";
    int port = Integer.valueOf(17);

    try (Socket socket = new Socket(host, port)) {
      // send message and connect with server
      OutputStream output = socket.getOutputStream();
      String message = "QOTD please";
      output.write(message.getBytes());
      System.out.println("Request sent!");

      // recieve quote and print
      InputStream input = socket.getInputStream();
      int exists = input.read();
      while (exists != -1) {
        System.out.write(exists);
        exists = input.read();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

