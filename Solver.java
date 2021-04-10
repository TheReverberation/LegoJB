import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Solver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            strings.add(in.next());
        }
        System.out.println(new Solver(strings).solve());
    }

    private enum Color {
        WHITE, GRAY, BLACK
    }

    private List<Integer> sorted;
    private List<Color> color;
    private List<List<Integer>> edges;
    private List<String> strings;


    public Solver(List<String> strings) {
        this.strings = strings;
        edges = new ArrayList<>();
        color = new ArrayList<>();
        sorted = new ArrayList<>();
    }

    public String solve() {
        for (int i = 0; i <= letterToIndex('z'); ++i) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < strings.size() - 1; ++i) {
            int j = 0;
            for (; j < Math.min(strings.get(i).length(), strings.get(i + 1).length()) &&
                    strings.get(i).charAt(j) == strings.get(i + 1).charAt(j); ++j);
            if (j < Math.min(strings.get(i).length(), strings.get(i + 1).length())) {
                edges.get(letterToIndex(strings.get(i + 1).charAt(j))).add(letterToIndex(strings.get(i).charAt(j)));
            }
        }
        if (topSort()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < sorted.size(); ++i) {
                builder.append(indexToLetter(sorted.get(i)));
            }
            return builder.toString();
        } else {
            return "Impossible";
        }
    }

    private boolean topSort() {
        for (int i = 0; i < edges.size(); ++i) {
            color.add(Color.WHITE);
        }
        for (int v = 0; v < color.size(); ++v) {
            if (color.get(v) == Color.WHITE) {
                if (!dfs(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int v) {
        color.set(v, Color.GRAY);
        for (Integer next : edges.get(v)) {
            if (color.get(next) == Color.WHITE) {
                if (!dfs(next)) {
                    return false;
                }
            } else if (color.get(next) == Color.GRAY) {
                return false;
            }
        }
        sorted.add(v);
        color.set(v, Color.BLACK);
        return true;
    }

    private static int letterToIndex(char c) {
        return c - 'a';
    }

    private static char indexToLetter(int n) {
        return (char) ('a' + n);
    }
}
