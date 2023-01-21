import java.util.LinkedList;

class Solution {

    public int solution(String dartResult) {
        LinkedList<Integer> turns = new LinkedList<>();

        for (int i = 0; i < dartResult.length(); ) {
            char ch = dartResult.charAt(i);
            if (Character.isDigit(ch)) {
                int number = convertNumber(dartResult.substring(i));
                turns.add(number);
            } else if (isPower(ch)) {
                turns.set(turns.size() - 1, power(turns.getLast(), ch));
            } else { // option
                performOption(turns, ch);
            }

            i = moveNextIndex(dartResult, i);
        }

        return sum(turns);
    }

    private static int convertNumber(String str) {
        int result = 0;
        for (int i = 0; i < str.length() && Character.isDigit(str.charAt(i)); i++) {
            result = result * 10 + (str.charAt(i) - '0');
        }
        return result;
    }

    private static boolean isPower(char ch) {
        if (ch == 'S' || ch == 'D' || ch == 'T') {
            return true;
        }
        return false;
    }

    private static int power(int number, char ch) {
        // default 'S'
        int exponent = 1;
        if (ch == 'D') {
            exponent = 2;
        } else if (ch == 'T') {
            exponent = 3;
        }
        return (int) Math.pow(number, exponent);
    }

    private static void performOption(LinkedList<Integer> list, char ch) {
        if (ch == '#') {
            list.set(list.size() - 1, operateSharp(list.getLast()));
        } else if (ch == '*') {
            operateAsterisk(list);
        }
    }

    private static int operateSharp(int number) {
        return -number;
    }

    private static void operateAsterisk(LinkedList<Integer> list) {
        LinkedList<Integer> tmp = new LinkedList<>();
        for (int i = 2; i > 0 && !list.isEmpty(); i--) {
            tmp.add(list.removeLast());
        }

        while (!tmp.isEmpty()) {
            list.add(tmp.removeLast() * 2);
        }
    }

    private static int moveNextIndex(String str, int i) {
        if (!Character.isDigit(str.charAt(i))) {
            return i + 1;
        }
        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private static int sum(LinkedList<Integer> list) {
        int result = 0;
        while (!list.isEmpty()) {
            result += list.remove();
        }
        return result;
    }
}