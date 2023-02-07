import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static final List<String> result = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int listSize = Integer.parseInt(tokenizer.nextToken());
        int inputSize = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        Character[] inputs = new Character[inputSize];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = tokenizer.nextToken().charAt(0);
        }

        // 초기화
        Character[] sortedCharacters = sort(inputs);

        // dfs
        makeCode(sortedCharacters, 0, new ArrayList<>(), listSize);

        // 결과 출력
        for (String line : result) {
            System.out.println(line);
        }

        // 종료 작업
        reader.close();
    }

    private static void makeCode(Character[] array, int startIndex,
            ArrayList<Character> code, int maxSize) {
        if (code.size() == maxSize) {
            if (validate(code)) {
                result.add(code.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()));
            }
            return;
        }

        for (int i = startIndex; i < array.length; i++) {
            code.add(array[i]);
            makeCode(array, i + 1, code, maxSize);
            code.remove(code.size() - 1);
        }
    }

    private static boolean validate(ArrayList<Character> list) {
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        int vowelCount = 0;
        int consonantCount = 0;
        for (int i = 0; i < list.size() && (vowelCount < 1 || consonantCount < 2); i++) {
            if (contains(vowels, list.get(i))) {
                vowelCount++;
            } else {
                consonantCount++;
            }
        }
        if (vowelCount >= 1 && consonantCount >= 2) {
            return true;
        }
        return false;
    }

    private static boolean contains(char[] chars, char ch) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ch) {
                return true;
            }
        }
        return false;
    }

    private static Character[] sort(Character[] characters) {
        Character[] result = characters.clone();
        for (int i = 0; i < result.length; i++) {
            int minIndex = i;
            int minValue = result[i];
            for (int j = i + 1; j < result.length; j++) {
                if (minValue > result[j]) {
                    minIndex = j;
                    minValue = result[j];
                }
            }
            swap(result, i, minIndex);
        }
        return result;
    }

    private static void swap(Character[] characters, int index1, int index2) {
        Character tmp = characters[index1];
        characters[index1] = characters[index2];
        characters[index2] = tmp;
    }
}
