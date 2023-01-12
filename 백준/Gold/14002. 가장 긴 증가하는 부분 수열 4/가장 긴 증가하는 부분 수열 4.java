import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer.parseInt(reader.readLine());
        int[] inputNumbers = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        ArrayList<Integer>[] arrayLists = new ArrayList[inputNumbers.length];
        for (int i = 0; i < arrayLists.length; i++) {
            arrayLists[i] = new ArrayList<>();
            arrayLists[i].add(inputNumbers[i]);
        }

        for (int i = 0; i < arrayLists.length; i++) {
            int addIndex = -1;
            int maxSize = 0;
            for (int j = 0; j < i; j++) {
                if (arrayLists[j].get(0) < arrayLists[i].get(0)
                        && maxSize < arrayLists[j].size()) {
                    addIndex = j;
                    maxSize = arrayLists[j].size();
                }
            }
            if (addIndex == -1) {
                continue;
            }
            for (int element : arrayLists[addIndex]) {
                arrayLists[i].add(element);
            }
        }

        int resultIndex = 0;
        int maxSize = 0;
        for (int i = 0; i < arrayLists.length; i++) {
            if (maxSize < arrayLists[i].size()) {
                resultIndex = i;
                maxSize = arrayLists[i].size();
            }
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.format("%d\n", arrayLists[resultIndex].size()));
        for (int i = arrayLists[resultIndex].size() - 1; i >= 0; i--) {
            writer.write(String.format("%d ", arrayLists[resultIndex].get(i)));
        }
        writer.close();
    }
}
