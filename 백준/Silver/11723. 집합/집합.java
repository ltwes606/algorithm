import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int size = Integer.parseInt(reader.readLine());

        // 연산
        Set<Integer> integerSet = new HashSet<>();
        for (int i = 0; i < size; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            String command = tokenizer.nextToken();
            Integer integer = null;
            if (tokenizer.hasMoreTokens()) {
                integer = Integer.parseInt(tokenizer.nextToken());
            }
            switch (command) {
                case "add":
                    integerSet.add(integer);
                    break;
                case "remove":
                    integerSet.remove(integer);
                    break;
                case "check":
                    if (!integerSet.contains(integer)) {
                        writer.write("0\n");
                        break;
                    }
                    writer.write("1\n");
                    break;
                case "toggle":
                    if (integerSet.contains(integer)) {
                        integerSet.remove(integer);
                        break;
                    }
                    integerSet.add(integer);
                    break;
                case "all":
                    for (int element = 1; element <= 20; element++) {
                        integerSet.add(element);
                    }
                    break;
                case "empty":
                    integerSet.clear();
                    break;
                default:
                    break;
            }
        }

        // 종료 작업
        reader.close();
        writer.close();
    }
}