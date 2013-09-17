package org.accountservice.client;

/**
 * @author Pavel Karpukhin
 */
public class Settings {

    public static final int DEFAULT_WRITERS_COUNT = 10;
    public static final int DEFAULT_READERS_COUNT = 10;
    public static final String DEFAULT_KEYS = "1, 2, 3, 4, 5, 6, 7, 8, 9, 10";
    public static final String DEFAULT_URL = "http://localhost:8080/";

    private int writersCount = DEFAULT_WRITERS_COUNT;
    private int readersCount = DEFAULT_READERS_COUNT;
    private String keys = DEFAULT_KEYS;
    private String url = DEFAULT_URL;
    private boolean succeed = true;
    private String message;

    public static Settings parseSettings(String[] args) {
        Settings settings = new Settings();

        for (int i = 0; i < args.length; ) {
            if ("-w".equals(args[i]) || "-writers".equals(args[i])) {
                if (i + 1 > args.length - 1) {
                    return Failed(args[i] + " requires writers count specification");
                }
                try {
                    settings.setWritersCount(Integer.parseInt(args[i + 1]));
                } catch (NumberFormatException e) {
                    return Failed(args[i + 1] + " is not an integer number");
                }
                i = i + 2;
                continue;
            }
            if ("-r".equals(args[i]) || "-readers".equals(args[i])) {
                if (i + 1 > args.length - 1) {
                    return Failed(args[i] + " requires readers count specification");
                }
                try {
                    settings.setReadersCount(Integer.parseInt(args[i + 1]));
                } catch (NumberFormatException e) {
                    return Failed(args[i + 1] + " is not an integer number");
                }
                i = i + 2;
                continue;
            }
            if ("-k".equals(args[i]) || "-keys".equals(args[i])) {
                if (i + 1 > args.length - 1) {
                    return Failed(args[i] + " requires keys specification");
                }
                settings.setKeys(args[i + 1]);
                i = i + 2;
                continue;
            }
            if ("-u".equals(args[i]) || "-url".equals(args[i])) {
                if (i + 1 > args.length - 1) {
                    return Failed(args[i] + " requires url specification");
                }
                settings.setUrl(args[i + 1]);
                i = i + 2;
                continue;
            }
            return Failed("Unexpected option");
        }
        return settings;
    }

    public static Settings Failed(String message) {
        Settings settings = new Settings();
        settings.setSucceed(false);
        settings.setMessage(message);
        return settings;
    }

    public int getWritersCount() {
        return writersCount;
    }

    public void setWritersCount(int writersCount) {
        this.writersCount = writersCount;
    }

    public int getReadersCount() {
        return readersCount;
    }

    public void setReadersCount(int readersCount) {
        this.readersCount = readersCount;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
