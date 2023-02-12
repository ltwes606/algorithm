import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    private static int size;
    private static String[] commands;
    private static ArrayList<String> result;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        size = Integer.parseInt(reader.readLine());
        commands = reader.readLine().split(" ");

        // 백트래킹
        result = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            StringBuilder sequence = new StringBuilder();
            boolean[] visited = new boolean[10];
            visited[i] = true;
            sequence.append(i);
            makeSequence(visited, sequence);
            visited[i] = false;
        }

        // 결과출력
        System.out.println(result.get(0));
        System.out.println(result.get(result.size() - 1));

        // 종료 작업
        reader.close();
    }

    private static void makeSequence(boolean[] visited, StringBuilder sequence) {
        if (sequence.length() == size + 1) {
            result.add(sequence.toString());
            return;
        }

        for (int i = 9; i >= 0; i--) {
            if (visited[i]) {
                continue;
            }
            if (!compare(sequence.charAt(sequence.length() - 1) - '0', i,
                    commands[sequence.length() - 1])) {
                continue;
            }

            visited[i] = true;
            sequence.append(i);
            makeSequence(visited, sequence);
            sequence.deleteCharAt(sequence.length() - 1);
            visited[i] = false;
        }
    }

    private static boolean compare(int a, int b, String command) {
        if (command.equals("<")) {
            return a < b;
        } else if (command.equals(">")) {
            return a > b;
        } else if (command.equals("=")) {
            return a == b;
        }
        return false;
    }
}