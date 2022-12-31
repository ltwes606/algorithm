import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = reader.readLine();
        char[] output = new char[input.length()];

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isAlphabetic(ch))
            {
                char standard = Character.isUpperCase(ch) ? 'A' : 'a';

                ch += 13;
                ch = (char)((ch - standard) % 26);
                ch += standard;
            }
            writer.write(ch);
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}
