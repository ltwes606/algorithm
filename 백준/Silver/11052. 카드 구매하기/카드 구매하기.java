import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static BufferedReader reader;

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine());
        int[] cards = getCards(count);
        reader.close();

        for (int i = 1; i < cards.length; i++) {
            for (int j = 0; j <= i / 2; j++) {
                cards[i] = Math.max(cards[i], cards[j] + cards[i - j]);
            }
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(cards[count]));
        writer.close();
    }

    private static int[] getCards(int inputCount) throws Exception {
        int[] input = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] result = new int[inputCount + 1];
        result[0] = 0;
        for (int index = 1; index < result.length; index++) {
            result[index] = input[index - 1];
        }
        return result;
    }
}