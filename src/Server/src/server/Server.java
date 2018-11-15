package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    public static int port = 6688;
    public static String log = "[Server] ";

    public static void main(String[] args) throws IOException {
        System.out.println(log + "Server is running!!");
        ServerSocket ss = new ServerSocket(port);

        while (true) {
            Socket s = null;

            try {
                s = ss.accept();

                System.out.println(log + "A new client is connected : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                Thread t = new ClientHandler(s, dis, dos);

                t.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public static String log = "[Server] ";

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        try {
            dos.writeUTF(log + "Connected to Sever!");
            dos.flush();

            int num_files = dis.readInt();
            for (int i = 0; i < num_files; i++) {
                String filename = dis.readUTF();
                Long filesize = dis.readLong();
                FileOutputStream fos = new FileOutputStream("_" + filename);
                dos.writeUTF(log + "Start transfer file " + filename + " with size " + filesize);
                dos.flush();
                byte[] buffer = new byte[4096];
                int read = 0;
                int totalRead = 0;
                while (filesize > 0 && (read = dis.read(buffer)) > 0) {
                    totalRead += read;
                    filesize -= read;
                    System.out.println(log + "Read " + filename + " " + totalRead + " bytes.");
                    fos.write(buffer, 0, read);
                }
                dos.writeUTF(log + "Completed transfer file " + filename);
                fos.close();
            }

            this.dis.close();
            this.dos.close();
            this.s.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
