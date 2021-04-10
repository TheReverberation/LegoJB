import java.util.regex.Pattern;
import java.util.concurrent.*;

public class NonblockingMatcher {
    public static void main(String[] args) {
        Future<Boolean> matchesFuture = new NonblockingMatcher().matches("abacaba.cpp", "a\\Sa\\Sa\\Sa\\.cpp");
        while (!matchesFuture.isDone()) {
            System.out.println("Wait for matches");
        }
        try {
            System.out.println(matchesFuture.get());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Boolean> matches(String text, String regex) {
        return executor.submit(() -> Pattern.compile(regex).matcher(text).matches());
    }
}
