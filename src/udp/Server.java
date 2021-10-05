package udp;

import java.net.*;

class Server {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9033);

        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
//            String requestReceived = new String(receivePacket.getData());
//            System.out.println(requestReceived);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            DatagramPacket sendPacket =
                    new DatagramPacket(new byte[1],
                            new byte[1].length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}