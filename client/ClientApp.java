import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientApp {

    public static void main(String args[]) throws NumberFormatException, IOException {

        String portNumber = "";

        if (args.length > 0) {
            portNumber = args[0];
        } else {
            System.err.println("Invalid arguments input");
        }

        // day 04 - slide 08, 09
        try {
            Socket s = new Socket("localhost", Integer.parseInt(portNumber));

            InputStream is = s.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            OutputStream os = s.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            Console console = System.console();
            String cookie = "";
            String keyboardInput = "";
            while (!keyboardInput.toLowerCase().equals("quit")) {
               keyboardInput = console.readLine("Enter '1' to request for a cookie ('quit' to terminate):");

               dos.writeUTF(keyboardInput);
               dos.flush();

               cookie = dis.readUTF();
               System.out.println(cookie);
            }


            dos.close();
            bos.close();
            os.close();

            dis.close();
            bis.close();
            is.close();

        } catch (EOFException eofException) {

        }


    }
}