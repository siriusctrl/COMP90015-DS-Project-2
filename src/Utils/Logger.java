package Utils;

public class Logger {

    public static void log(Object thing) {
        System.out.println("==== " + thing + " ====");
    }

    public static void logError(Object thing) {
        System.err.println("==== " + thing + " ====");
    }
}
