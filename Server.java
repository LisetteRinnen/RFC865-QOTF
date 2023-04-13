import java.io.*;
import java.net.*;


public class Server {

  static String quote = "If you look for perfection, you'll never be content. - Leo Tolstoy \n";

  public static void handleTCPRequest(ServerSocket TCPserver) {
    try {
      Socket socket = null;
      while ((socket = TCPserver.accept()) != null) {
        System.out.println("TCP client accepted");
        try(OutputStream output = socket.getOutputStream()) {
          System.out.println("Quote sent!");
          output.write(quote.getBytes());

          // Wait for the client to stop reading
          Thread.sleep(10);

          // end output
          socket.shutdownOutput();
          System.out.println();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  public static void handleUDPRequest(DatagramSocket UDPserver) {
    try {
      // create a blank packet for receiving
      byte[] buf = new byte[512];
      DatagramPacket packet = new DatagramPacket(buf, 512);

      // check to see if server recieved a packet
      while (true) {
        UDPserver.receive(packet);
        System.out.println("UDP client accepted, packet received");
        try {
          // create a quote packet to send
          byte[] messageByte = quote.getBytes();
          SocketAddress clientSocketAddress = packet.getSocketAddress();
          DatagramPacket message = new DatagramPacket(messageByte, messageByte.length, clientSocketAddress);
          System.out.println("Created quote packet");

          // send the quote
          UDPserver.send(message);
          System.out.println("Sent quote!");
          System.out.println();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  public static void main (String... args) throws Exception {
    ServerSocket TCPserver = new ServerSocket(17);
    DatagramSocket UDPserver = new DatagramSocket(17);

    try {
      // TCP
      System.out.println("TCP server thread starting...");
      new Thread(() -> handleTCPRequest(TCPserver)).start();

      // UDP
      System.out.println("UDP server thread starting...");
      new Thread(() -> handleUDPRequest(UDPserver)).start();

      System.out.println();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

