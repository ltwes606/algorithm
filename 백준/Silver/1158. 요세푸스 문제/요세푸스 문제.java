import java.io.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
        LinkedList<Integer> list = new LinkedList<>();

        String[] cmd = reader.readLine().split(" ");
        int size = Integer.parseInt(cmd[0]);
        int interval = Integer.parseInt(cmd[1]);

        for (int i = 1; i <= size; i++)
            list.add(i);
//        for(int i = 0; i < list.size(); i++) {
//            write.write(String.valueOf(list.get(i)));
//            write.newLine();
//        }
        write.write('<');
        int index = 0;
        while (!list.isEmpty()) {
            index = (index + interval - 1) % list.size();
            write.write(String.valueOf(list.remove(index)));
            if (!list.isEmpty()) write.write(", ");
        }
        write.write('>');

        write.close();
        reader.close();
    }
}
