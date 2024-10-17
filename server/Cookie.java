import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie implements Runnable {

    List<String> cookies = new ArrayList<String>();

    Socket s;
    String fullPathName;

    public Cookie(Socket s, String dirPathFileName) {
        this.s = s;
        this.fullPathName = dirPathFileName;
    }

    public void readCookieFile(String dirPathFileName) throws IOException {

        FileReader fr = new FileReader(dirPathFileName);
        BufferedReader br = new BufferedReader(fr);

        String cookie = "";
        while ((cookie = br.readLine()) != null) {
            cookies.add(cookie);
        }
        br.close();
        fr.close();
    }

    public String getRandomCookie() {
        Random rand = new Random();

        if (cookies != null) {
            if (cookies.size() > 0) {
                return cookies.get(rand.nextInt(cookies.size()));

            } else {
                return "No cookie found!!!";
            }
        }

        return "No cookie found!!!";
    }

    public void printCookies() {
        if (cookies.size() > 0) {
            cookies.forEach(System.out::println);
        }
    }

    @Override
    public void run() {
        // day 04 - slide 9, 10
        try {
            readCookieFile(this.fullPathName);
            
            InputStream is;
            try {
                is = s.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
                String messageReceived = "";

                OutputStream os = s.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                // day 04 - slide 9
                while (!messageReceived.toLowerCase().equals("quit")) {
                    System.out.println("Waiting for client input...");

                    messageReceived = dis.readUTF(dis);

                    // pick a random cookie
                    String retrievedCookie = getRandomCookie();

                    // put it to the DataOutputStream to send back to client
                    dos.writeUTF(retrievedCookie);
                    dos.flush();
                }

                dos.close();
                bos.close();
                os.close();

                dis.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
        } finally {

        }
    }

}
