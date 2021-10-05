package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Client {
    public static void main(String args[]) throws Exception {
        String HOST = "127.0.0.1", message = "u";
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(HOST);

        List<Double> experiments = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            byte[] payload =  new byte[1];
            Instant start = Instant.now();
            DatagramPacket sendPacket = new DatagramPacket(payload, payload.length, IPAddress, 9033);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(payload, payload.length);
            clientSocket.receive(receivePacket);
            Instant end = Instant.now();

            Duration timeElapsed = Duration.between(start, end);
            experiments.add((double) timeElapsed.toNanos());
        }

        Collections.sort(experiments);
        double ninetyPercentile = percentile(90,experiments);
        double tenPercentile = percentile(10,experiments);
        double mean = experiments.stream().mapToDouble(val -> val).average().orElse(0.0);

        // for reporting purposes
        System.out.println(experiments);
        System.out.println("90 Percentile: "+ninetyPercentile);
        System.out.println("10 Percentile: "+tenPercentile);
        System.out.println("Mean: "+mean);

        clientSocket.close();
    }


    public static double percentile(double percentile, List<Double> items) {
        Collections.sort(items);
        return items.get((int) Math.round(percentile / 100.0 * (items.size() - 1)));
    }

}
