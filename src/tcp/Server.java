package tcp;

import java.io.*;
import java.net.*;
import java.util.Arrays;

class Server {

    public static void main(String[] argv) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        int counter = 0;

        ServerSocket welcomeSocket = new ServerSocket(9103);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            byte[] buffer = new byte[1024];

            while (true) {
                // takes input from the client socket
                in = new DataInputStream(connectionSocket.getInputStream());
                //writes on client socket
                out = new DataOutputStream(connectionSocket.getOutputStream());
                // Receiving data from client
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int off = in.read(buffer); //offset
                if (off == -1) {
                    break;
                }
                counter++;
                // sending data to client
                out.write((byte)(counter));

                // can send client's data back to client, left code for control purposes
//                baos.write(buffer, 0, off);
//                byte[] result = baos.toByteArray();
//                out.write(result);
//                String res = Arrays.toString(result);
//                System.out.println(res);

            }
        }
    }
}
