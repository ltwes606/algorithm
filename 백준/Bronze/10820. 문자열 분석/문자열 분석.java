import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true){
            String input = reader.readLine();
            if (input == null)
                break;

            int[] frequency = new int[4];
            for (int i = 0; i < input.length(); i++){
                char ch = input.charAt(i);

                if (Character.isLowerCase(ch))
                    frequency[0]++;
                else if (Character.isUpperCase(ch))
                    frequency[1]++;
                else if (Character.isDigit(ch))
                    frequency[2]++;
                else if (Character.isSpaceChar(ch))
                    frequency[3]++;
            }

            for (int i = 0; i < frequency.length; i++)
                write.write(frequency[i] + " ");
            write.newLine();
        }


        write.flush();
        write.close();
        reader.close();
    }
}
