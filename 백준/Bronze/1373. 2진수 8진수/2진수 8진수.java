import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String binaryString = reader.readLine();
        reader.close();

        ArrayList<Character> octalArray = new ArrayList<>();
        char[] binaryCharArray = binaryString.toCharArray();
        for (int i = binaryString.length() - 1; i >= 0; i -= 3) {
            char octalElement = '0';
            if (i - 2 >= 0) {
                octalElement += 4 * (binaryCharArray[i - 2] - '0');
            }
            if (i - 1 >= 0) {
                octalElement += 2 * (binaryCharArray[i - 1] - '0');
            }
            octalElement += (binaryCharArray[i] - '0');
            octalArray.add(octalElement);
        }
        Collections.reverse(octalArray);
        for (Character character : octalArray) {
            writer.write(character);
        }
        writer.close();
    }
}