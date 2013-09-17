package org.accountservice.client;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pavel Karpukhin
 * @since 16.09.2013
 */
public class ConsoleApp {

    private static final Logger logger = Logger.getLogger(ConsoleApp.class.getName());

    public static void main(String[] args) {
        Settings settings = Settings.parseSettings(args);
        if (!settings.isSucceed()) {
            logger.log(Level.SEVERE, settings.getMessage());
            System.exit(-1);
        }

        List<Integer> keys = new ArrayList<Integer>();
        StringTokenizer tokenizer = new StringTokenizer(settings.getKeys(), ", ");
        while (tokenizer.hasMoreTokens()) {
            keys.add(Integer.parseInt(tokenizer.nextToken()));
        }
        int[] intKeys = new int[keys.size()];
        for (int i = 0; i < keys.size(); ++i) {
            intKeys[i] = keys.get(i);
        }

        ReaderThread reader = new ReaderThread(settings.getUrl(), intKeys);
        WriterThread writer = new WriterThread(settings.getUrl(), intKeys);

        Thread[] threads = new Thread[settings.getReadersCount() + settings.getWritersCount()];

        for (int i = 0; i < settings.getReadersCount(); ++i) {
            threads[i] = new Thread(reader);
            threads[i].start();
        }
        for (int i = settings.getReadersCount(); i < settings.getReadersCount() + settings.getWritersCount(); ++i) {
            threads[i] = new Thread(writer);
            threads[i].start();
        }

        try {
            for (int i = 0; i < threads.length; ++i) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
        }
    }
}
