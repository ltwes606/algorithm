import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
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

    static class Result {

        private int infectedComputerCount;
        private int lastInfectedComputerTime;

        public Result() {
        }

        public int getInfectedComputerCount() {
            return infectedComputerCount;
        }

        public void setInfectedComputerCount(int infectedComputerCount) {
            this.infectedComputerCount = infectedComputerCount;
        }

        public int getLastInfectedComputerTime() {
            return lastInfectedComputerTime;
        }

        public void setLastInfectedComputerTime(int lastInfectedComputerTime) {
            this.lastInfectedComputerTime = lastInfectedComputerTime;
        }
    }

    static class Computer {

        private boolean infected;
        private final Map<Integer, Integer> connections;

        public Computer() {
            connections = new HashMap<>();
        }

        public void connectionComputer(int key, int value) {
            connections.put(key, value);
        }

        public boolean isInfected() {
            return infected;
        }

        public void setInfected(boolean infected) {
            this.infected = infected;
        }

        public Map<Integer, Integer> getConnections() {
            return connections;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        int testCaseCount = reader.nextInt();
        Result[] results = new Result[testCaseCount];
        for (int i = 0; i < results.length; i++) {
            results[i] = new Result();
        }

        for (int i = 0; i < testCaseCount; i++) {
            int computerSize = reader.nextInt();
            Computer[] computers = new Computer[computerSize + 1];
            for (int j = 1; j < computers.length; j++) {
                computers[j] = new Computer();
            }

            int injectedConnectCount = reader.nextInt();
            int startComputerIndex = reader.nextInt();
            for (int j = 0; j < injectedConnectCount; j++) {
                int computerIndex1 = reader.nextInt();
                int computerIndex2 = reader.nextInt();
                int infectedTime = reader.nextInt();

                computers[computerIndex2].connectionComputer(computerIndex1, infectedTime);
            }

            results[i].setLastInfectedComputerTime(dijkstra(computers, startComputerIndex));
            results[i].setInfectedComputerCount(countInfectedComputers(computers));
        }

        printResult(results);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void printResult(Result[] results) {
        for (Result result : results) {
            writer.println(
                    result.getInfectedComputerCount() + " " + result.getLastInfectedComputerTime());
        }
    }

    private static int countInfectedComputers(Computer[] computers) {
        int result = 0;
        for (int i = 1; i < computers.length; i++) {
            Computer computer = computers[i];
            if (!computer.isInfected()) {
                continue;
            }
            result++;
        }
        return result;
    }

    private static int dijkstra(Computer[] computers, int startComputerIndex) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, startComputerIndex});

        int result = 0;
        while (!pq.isEmpty()) {
            int[] element = pq.poll();
            int currentIndexInfectedTime = element[0];
            Computer currentComputer = computers[element[1]];
            if (currentComputer.isInfected()) {
                continue;
            }
            currentComputer.setInfected(true);
            result = currentIndexInfectedTime;

            for (Integer computerIndex : currentComputer.getConnections().keySet()) {
                pq.offer(new int[]{
                        currentIndexInfectedTime
                                + currentComputer.getConnections().get(computerIndex),
                        computerIndex});
            }
        }
        return result;
    }
}