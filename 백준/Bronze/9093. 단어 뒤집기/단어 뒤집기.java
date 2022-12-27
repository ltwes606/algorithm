import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder buffer = new StringBuilder();

        int count = Integer.parseInt(reader.readLine());
        for (int i = 0; i < count; i++){
            String[] strArr = reader.readLine().split(" ");

            for (int arrIdx = 0; arrIdx < strArr.length; arrIdx++){
                for (int strIdx = strArr[arrIdx].length() - 1; strIdx >= 0; strIdx--){
                    buffer.append(strArr[arrIdx].charAt(strIdx));
                }
                buffer.append(' ');
            }
            buffer.append('\n');
        }

        writer.write(buffer.toString());
        writer.close();
        reader.close();
    }
}
