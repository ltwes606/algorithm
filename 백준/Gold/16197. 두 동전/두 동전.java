import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public Integer nextInt() {
            return Integer.parseInt(next());
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            this.builder = new StringBuilder();
        }

        public void print(String str) {
            builder.append(str);
        }

        public void println(String str) {
            print(str + "\n");
        }

        public void close() {
            try {
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.delete(0, builder.length());
        }
    }

    static class Point {

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    static class Coin extends Point {

        private int prevX;
        private int prevY;

        public Coin(int x, int y) {
            super(x, y);
            prevX = -1;
            prevY = -1;
        }

        public Coin(int x, int y, int prevX, int prevY) {
            super(x, y);
            this.prevX = prevX;
            this.prevY = prevY;
        }

        public int getPrevX() {
            return prevX;
        }

        public int getPrevY() {
            return prevY;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int buttonMinCount = Integer.MAX_VALUE;
    private static int MAX_COUNT = 10;

    private static Point[] DIRECTS = new Point[]{
            new Point(0, -1), new Point(1, 0),
            new Point(0, 1), new Point(-1, 0)
    };


    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();
        char[][] inputs = new char[rowSize][colSize];
        for (int iRow = 0; iRow < rowSize; iRow++) {
            String line = reader.next();
            for (int iCol = 0; iCol < colSize; iCol++) {
                inputs[iRow][iCol] = line.charAt(iCol);
            }
        }

        // 맵과 코인 분리
        char[][] map = getMap(inputs);
        List<Coin> coins = getCoins(inputs);

        // dfs
        dfs(map, coins, 0);

        // 결과 출력
        printResult();

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static char[][] getMap(char[][] inputs) {
        char[][] result = new char[inputs.length][];
        for (int iRow = 0; iRow < result.length; iRow++) {
            result[iRow] = new char[inputs[iRow].length];
            for (int iCol = 0; iCol < result[iRow].length; iCol++) {
                char c = inputs[iRow][iCol];
                if (c == 'o') {
                    c = '.';
                }

                result[iRow][iCol] = c;
            }
        }
        return result;
    }

    private static List<Coin> getCoins(char[][] inputs) {
        List<Coin> result = new ArrayList<>();
        for (int iRow = 0; iRow < inputs.length; iRow++) {
            for (int iCol = 0; iCol < inputs[iRow].length; iCol++) {
                if (inputs[iRow][iCol] != 'o') {
                    continue;
                }
                result.add(new Coin(iCol, iRow));
            }
        }
        return result;
    }

    private static void dfs(char[][] map, List<Coin> coins, int count) {
        int fallenCoinsCount = countFallenCoins(map, coins);
        if (count > 10 || fallenCoinsCount > 1) {
            return;
        }

        if (fallenCoinsCount == 1) {
            buttonMinCount = Math.min(buttonMinCount, count);
            return;
        }

        reposition(map, coins);

        for (Point direct : DIRECTS) {
            List<Coin> nextCoins = new ArrayList<>();
            for (Coin coin : coins) {
                nextCoins.add(new Coin(coin.getX() + direct.getX(), coin.getY() + direct.getY(),
                        coin.getX(), coin.getY()));
            }

            dfs(map, nextCoins, count + 1);
        }
    }

    private static int countFallenCoins(char[][] map, List<Coin> coins) {
        int result = 0;
        for (Coin coin : coins) {
            if (validRange(map, coin)) {
                continue;
            }
            result++;
        }
        return result;
    }

    private static boolean validRange(char[][] map, Coin coin) {
        if (coin.getY() < 0 || map.length <= coin.getY()) {
            return false;
        }
        if (coin.getX() < 0 || map[coin.getY()].length <= coin.getX()) {
            return false;
        }
        return true;
    }

    private static void reposition(char[][] map, List<Coin> coins) {
        for (Coin coin : coins) {
            if (map[coin.getY()][coin.getX()] != '#') {
                continue;
            }
            coin.setX(coin.getPrevX());
            coin.setY(coin.getPrevY());
        }
    }

    private static void printResult() {
        if (buttonMinCount > 10) {
            buttonMinCount = -1;
        }
        writer.println(String.valueOf(buttonMinCount));
    }
}