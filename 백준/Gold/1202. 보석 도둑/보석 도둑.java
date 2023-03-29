import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        int jewelQueueSize = reader.nextInt();
        int bagsSize = reader.nextInt();

        Queue<Jewel> jewelQueue = createJewelQueue(jewelQueueSize);
        List<Integer> bags = createBagsQueue(bagsSize);
        Queue<Integer> deletedPriceQueue = new PriorityQueue(Collections.reverseOrder());

        long result = findSumMaxWeight(bags, jewelQueue, deletedPriceQueue);

        printResult(result);

        // 종료 작업
        close();
    }

    private static Queue<Jewel> createJewelQueue(int jewelQueueSize) {
        Queue<Jewel> jewelQueue = new PriorityQueue<>();
        for (int i = 0; i < jewelQueueSize; i++) {
            int weight = reader.nextInt();
            int price = reader.nextInt();
            Jewel jewel = new Jewel(weight, price);

            jewelQueue.offer(jewel);
        }
        return jewelQueue;
    }

    private static List<Integer> createBagsQueue(int bagsSize) {
        List<Integer> bags = new ArrayList<>();
        for (int i = 0; i < bagsSize; i++) {
            int bagMaxWeight = reader.nextInt();
            bags.add(bagMaxWeight);
        }
        Collections.sort(bags);
        return bags;
    }


    private static long findSumMaxWeight(
            List<Integer> bags, Queue<Jewel> jewelQueue, Queue<Integer> deletedPriceQueue) {
        long result = 0;
        for (int i = 0; i < bags.size(); i++) {
            int baeMaxWeight = bags.get(i);
            result += findBagMaxPrice(baeMaxWeight, jewelQueue, deletedPriceQueue);
        }
        return result;
    }

    private static long findBagMaxPrice(
            int baeMaxWeight, Queue<Jewel> jewelQueue, Queue<Integer> deletedPriceQueue) {
        while (!jewelQueue.isEmpty()) {
            if (jewelQueue.peek().getWeight() > baeMaxWeight) {
                break;
            }
            deletedPriceQueue.offer(jewelQueue.poll().price);
        }

        if (deletedPriceQueue.isEmpty()) {
            return 0;
        }
        return deletedPriceQueue.poll();
    }

    private static void printResult(long result) {
        writer.println(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }

    static class Jewel implements Comparable<Jewel> {

        private int weight;
        private int price;

        public Jewel(int weight, int price) {
            this.weight = weight;
            this.price = price;
        }

        public int getWeight() {
            return weight;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public int compareTo(Jewel otherJewel) {
            if (weight == otherJewel.getWeight()) {
                return price - otherJewel.getPrice();
            }
            return weight - otherJewel.getWeight();
        }
    }

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
                    throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }
    }

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            builder = new StringBuilder();
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
                throw new RuntimeException(e);
            }
        }
    }
}