package tcp;

import java.io.*;
import java.net.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

// Q3. Experiment2
class Client3 {

    private static String HOST = "127.0.0.1";

    public static void main(String[] argv) throws Exception {
        // prepare payload
        byte[] smallPayload = {(byte)0x5F};
        byte[] buffer = new byte[1024];
        List<Double> experiments = new ArrayList<>();

        byte[] arr = null;
        arr=smallPayload;
        Socket clientSocket = new Socket(HOST, 9103);

        for (int i = 0; i < 100; i++) {
            Instant start = Instant.now();
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            InputStream in = null;
            in = new DataInputStream(clientSocket.getInputStream());

            //send to server
            outToServer.write(arr);
            String req = Arrays.toString(arr);
            /////System.out.println("Sent to server : " + req);

            // receive from server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(buffer, 0 , in.read(buffer));
            byte result[] = baos.toByteArray();
            String res = Arrays.toString(result);
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            ////System.out.println("TIME: "+timeElapsed+ " SERVER RESPONSE: " + res);
            System.out.println(timeElapsed.toNanos());
            experiments.add((double) timeElapsed.toMillis());
        }
        clientSocket.close();

        double ninetyPercentile = percentile(90, experiments);
        double tenPercentile = percentile(10, experiments);
        double mean = experiments.stream().mapToDouble(val -> val).average().orElse(0.0);

        // for reporting purposes
        System.out.println(experiments);
        System.out.println("90 Percentile: " + ninetyPercentile);
        System.out.println("10 Percentile: " + tenPercentile);
        System.out.println("Mean: " + mean);

    }


    public static double percentile(double percentile, List<Double> items) {
        Collections.sort(items);
        return items.get((int) Math.round(percentile / 100.0 * (items.size() - 1)));
    }
}