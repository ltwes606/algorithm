import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    public static final int MAX_VALUE = 10;
    public static final int MIN_VALUE = -10;

    private static int size;
    private static char[][] commands;

    private static ArrayList<String> result = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        size = Integer.parseInt(reader.readLine());
        String commandInput = null;
        if (size != 0) {
            commandInput = reader.readLine();
        }
        String[] commandLines = new String[size];
        int sizeCount = 0;
        for (int i = 0; i < size; i++) {
            commandLines[i] = commandInput.substring(sizeCount, sizeCount + size - i);
            sizeCount += size - i;
        }

        // 표와 같이 입력
        commands = new char[size][size];
        for (int i = 0; i < size; i++) {
            // 입력되지 않는 값은 0
            for (int j = i; j < size; j++) {
                commands[i][j] = commandLines[i].charAt(j - i);
            }
        }

        // 초기화
        boolean[] visited = new boolean[MAX_VALUE - MIN_VALUE + 2];

        // 백트래킹
        makeSequence(new ArrayList<>());

        // 결과 출력
        System.out.println(result.get(0));

        // 종료 작업
        reader.close();
    }

    private static boolean makeSequence(ArrayList<Integer> sequence) {
        if (sequence.size() == size) {
            result.add(sequence.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            return true;
        }

        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            if (!validCommandColumn(sequence, i)) {
                continue;
            }

            sequence.add(i);
            if (makeSequence(sequence)) {
                return true;
            }
            sequence.remove(sequence.size() - 1);
        }
        return false;
    }

    private static boolean validCommandColumn(ArrayList<Integer> sequence, int addNumber) {
        int sequenceSum = sum(sequence) + addNumber;

        for (int i = 0; i <= sequence.size(); i++) {
            if (!validCommand(sequenceSum, commands[i][sequence.size()])) {
                return false;
            }
            if (sequence.size() > i) {
                sequenceSum -= sequence.get(i);
            }
        }
        return true;
    }

    private static boolean validCommand(int number, Character ch) {
        if (ch.equals('+')) {
            return number > 0;
        } else if (ch.equals('-')) {
            return number < 0;
        } else if (ch.equals('0')) {
            return number == 0;
        }
        return false;
    }

    private static int sum(ArrayList<Integer> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i);
        }
        return result;
    }
}