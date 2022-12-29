import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] alphabet = new int[26];

        String str = reader.readLine();

        Arrays.fill(alphabet, -1);
        for (int i = 0; i < str.length(); i++){
            int index = str.charAt(i) - 'a';
            if (alphabet[index] == -1)
                alphabet[index] = i;
        }

        for (int firstIndex: alphabet){
            writer.write(String.valueOf(firstIndex) + " ");
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}
