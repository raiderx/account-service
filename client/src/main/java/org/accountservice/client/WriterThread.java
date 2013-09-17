package org.accountservice.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * @author Pavel Karpukhin
 */
public class WriterThread implements Runnable {

    private final Random random = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String url;
    private final int[] keys;

    public WriterThread(String url, int[] keys) {
        this.url = url;
        this.keys = keys;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder(url);
        if (!url.endsWith("/")) {
            sb.append("/");
        }
        sb.append("account/add/");
        String baseUrl = sb.toString();

        int key = random.nextInt(keys.length);
        String address = baseUrl + keys[key];
        URL url;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + address);
            return;
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.addRequestProperty("Content-Type", "application/json");

                PrintWriter pw = new PrintWriter(connection.getOutputStream());
                long sum = random.nextLong() % 20;
                pw.println(Long.toString(sum));
                pw.close();

                InputStream stream = connection.getInputStream();
                ActionResult<Long> result = mapper.readValue(stream, new TypeReference<ActionResult<Long>>() {});
                if (result.getSuccess()) {
                    System.out.printf("Balance of [%d] account was updated: %d\n", keys[key], sum);
                } else {
                    System.out.println(result.getMessage());
                }
                stream.close();

                key = random.nextInt(keys.length);
                address = baseUrl + keys[key];
                try {
                    url = new URL(address);
                } catch (MalformedURLException e) {
                    System.out.println("Malformed URL: " + address);
                    return;
                }
            } catch (IOException e) {
                System.err.println("Connection error: " + e.getMessage());
                return;
            }
        }
    }
}
