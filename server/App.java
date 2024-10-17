import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    // args[0] ==> port number
    // args[1] => directory path
    public static void main(String args[]) throws NumberFormatException, IOException {

        String portNumber = "";
        String dirPath = "";
        String fileName = "";
        if (args.length > 0) {
            portNumber = args[0];
            dirPath = args[1];
            fileName = args[2];
        } else {
            System.err.println("Invalid number of arguments expected");
            System.exit(0);
        }

        File newDirectory = new File(dirPath);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        // day 04 - slide 8
        ServerSocket ss = new ServerSocket(Integer.parseInt(portNumber));
        Socket s = ss.accept();
        System.out.printf("\r\nWebsocket server started on port... %s\r\n", portNumber);

        // read and print cookies
        // Cookie c = new Cookie(s);
        // c.readCookieFile(dirPath + File.separator + fileName);
        // c.printCookies();

        Thread th = new Thread(new Cookie(s, dirPath + File.separator + fileName));
        th.start();


    }
}