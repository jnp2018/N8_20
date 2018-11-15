package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static int port = 6688;
    public static String host = "192.168.43.50";
    public static String log = "[Client] ";

    public static void main(String[] args) throws IOException {

        try {
            String[] filenames = args[0].split(";");
            InetAddress ip = InetAddress.getByName(host);

            Socket s = new Socket(ip, port);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.println(dis.readUTF());
            dos.writeInt(filenames.length);
            dos.flush();
            for (String filename : filenames) {
                dos.writeUTF(filename);
                dos.flush();
                File file = new File(filename);
                dos.writeLong(file.length());
                dos.flush();
                System.out.println(dis.readUTF());
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int read = 0;
                int totalRead = 0;
                while ((read = fis.read(buffer)) > 0) {
                    totalRead += read;
                    System.out.println(log + "send " + filename + " " + totalRead + " bytes.");
                    dos.write(buffer, 0, read);
                    dos.flush();
                }
                System.out.println(dis.readUTF());
                fis.close();
            }
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
