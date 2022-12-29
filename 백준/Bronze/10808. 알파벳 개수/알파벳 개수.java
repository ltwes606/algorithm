import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] alphabet = new int[26];

        String str = reader.readLine();
        for (int i = 0; i < str.length(); i++){
            alphabet[str.charAt(i) - 'a']++;
        }

        for (int count: alphabet){
            writer.write(String.valueOf(count) + " ");
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}
