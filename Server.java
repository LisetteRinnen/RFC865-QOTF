import java.io.*;
import java.net.*;


public class Server {

  public static String getQuote() {
    // Favorite movie: Hidden Figures
    String[] quotes = {
      "\"We All Get To The Peak Together, Or We Don't Get There At All.\" - Al Harrison \n",
      "\"There's Only One Thing To Do: Learn All We Can.\" - Dorothy Vaughan \n",
      "\"Whoever Gets There First Will Make The Rules.\" - Al Harrison \n",
      "\"It's Equal Rights. I Have The Right To See Fine In Every Color.\" - Mary Jackson \n",
      "\"No Crime In A Broken Down Car.\" \"No Crime Being Negro, Neither.\" - Dorothy Vaughan & Mary Jackson \n",
      "\"Separate And Equal Are Two Different Things. Just Because It's The Way Don't Make It Right.\" - Dorothn Vaughan \n",
      "\"Civil Rights Ain't Always Civil.\" - Levi Jackson \n",
      "\"And It's Not Because We Wear Skirts. It's Because We Wear Glasses.\" - Katherine Johnson \n",
      "\"You, Sir, You Are The Boss. You Just Have To Act Like One... Sir.\" - Katherine Johnson \n",
      "\"Despite What You Think, I Have Nothing Against Y'all.\" \"I Know You Probably Believe That.\" - Vivian Mitchell & Dorothy Vaughan \n",
      "\"Every Time We Have A Chance To Get Ahead, They Move The Finish Line.\" - Mary Jackson \n",
      "\"We all pee the same color.\" - Al Harrison \n",
      "\"Which one is going to make you the first?\" - Mary Jackson \n",
      "\"Life is pain, Highness. Anyone who says differently is selling something.\" - Westley \n",
      "\"Hello. My name is Inigo Montoya. You killed my father. Prepare to die.\" - Inigo Montoya \n",
      "\"I always think that everything could be a trap, which is why I’m still alive.\" - Prince Humperdinck \n",
      "\"There’s a shortage of perfect breasts in the world. It would be a pity to damage yours.\" - Westley \n",
      "\"Good night, Westley. Good work. Sleep well. I’ll most likely kill you in the morning.\" - Dread Pirate Roberts \n",
      "\"You mean you wish to surrender to me? Very well, I accept.\" - Westley \n",
      "\"This is true love. Do you think this happens every day?\" - Westley \n"
    };

    String resultQuote = quotes[(int) (Math.random() * quotes.length)];
    return resultQuote;
  }


  public static void handleTCPRequest(ServerSocket TCPserver) {
    try {
      Socket socket = null;
      while ((socket = TCPserver.accept()) != null) {
        System.out.println("TCP client accepted");
        try(OutputStream output = socket.getOutputStream()) {
          String quote = getQuote();
          System.out.println(quote);
          output.write(quote.getBytes());
          System.out.println("Quote sent!");

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
          String quote = getQuote();
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

