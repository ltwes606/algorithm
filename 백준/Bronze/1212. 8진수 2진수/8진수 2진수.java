import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String octalString = reader.readLine();
        reader.close();

        char[] binaryChars = new char[octalString.length() * 3];
        char[] octalChars = octalString.toCharArray();
        for (int i = 0; i < octalChars.length; i++) {
            int octal = octalChars[i] - '0';
            binaryChars[i * 3] = (char) ((octal / 4) + '0');
            octal %= 4;
            binaryChars[i * 3 + 1] = (char) ((octal / 2) + '0');
            octal %= 2;
            binaryChars[i * 3 + 2] = (char) (octal + '0');
        }
        int firstIndex = -1;
        for (int i = 0; i < binaryChars.length; i++) {
            if (binaryChars[i] == '1') {
                firstIndex = i;
                break;
            }
        }
        if (firstIndex == -1) {
            writer.write("0");
        } else {
            writer.write(String.valueOf(binaryChars).substring(firstIndex));
        }
        writer.close();
    }
}