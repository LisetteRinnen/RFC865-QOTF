import java.io.*;
import java.net.*;


public class UDPClient {
  public static void main(String... args) {
    String host = "localhost";
    int port = Integer.valueOf(17);

    try {
      // send message and connect with server
      InetAddress serverAddress = InetAddress.getByName(host);
      DatagramSocket socket = new DatagramSocket();
      socket.connect(serverAddress, port);

      String content = "QOTF please";
      byte[] output = content.getBytes();
      DatagramPacket message = new DatagramPacket(output, output.length);

      socket.send(message);
      System.out.println("Request sent!");

      // recieve quote and print
      byte[] buf = new byte[512];
      DatagramPacket packet = new DatagramPacket(buf, 512);

      socket.receive(packet);
      String quote = new String(buf);
      System.out.println(quote);

      socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

