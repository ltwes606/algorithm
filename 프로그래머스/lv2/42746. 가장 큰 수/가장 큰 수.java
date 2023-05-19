import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        String[] strings = Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new);
        sort(strings);

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            result.append(str);
        }
        
        if (isAllZero(result)) {
            return "0";
        }
        return result.toString();
    }

    private static void sort(String[] strings) {
        Arrays.sort(strings, (str1, str2) -> {
                    String repeatedStr1 = str1.repeat(str2.length());
                    String repeatedStr2 = str2.repeat(str1.length());
                    return repeatedStr2.compareTo(repeatedStr1);
                }
        );
    }
    
    private static boolean isAllZero(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}