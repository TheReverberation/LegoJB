import java.util.regex.Pattern;
import java.util.concurrent.*;

public class NonblockingMatcher {
    public static void main(String[] args) {
        Future<Boolean> matchesFuture = new NonblockingMatcher().matches("abacaba.cpp", "a\\Sa\\Sa\\Sa\\.cpp");
        try {
            System.out.println(matchesFuture.get(200, TimeUnit.MILLISECONDS));
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        System.out.println("hello, world");

    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Boolean> matches(String text, String regex) {
        return executor.submit(() -> Pattern.compile(regex).matcher(text).matches());
    }
}
