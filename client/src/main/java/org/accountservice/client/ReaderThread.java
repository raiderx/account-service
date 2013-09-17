package org.accountservice.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * @author Pavel Karpukhin
 */
public class ReaderThread implements Runnable {

    private final Random random = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String url;
    private final int[] keys;

    public ReaderThread(String url, int[] keys) {
        this.url = url;
        this.keys = keys;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder(url);
        if (!url.endsWith("/")) {
            sb.append("/");
        }
        sb.append("account/get/");
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

                InputStream stream = connection.getInputStream();
                ActionResult<Long> result = mapper.readValue(stream, new TypeReference<ActionResult<Long>>() {});
                if (result.getSuccess()) {
                    System.out.printf("Balance of [%d] account: %d\n", keys[key], result.getData());
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
