import java.util.Arrays;

class Solution {
    public String solution(String s) {
    String[] numbersString = s.split(" ");
    
    int[] numbers = Arrays.stream(numbersString)
            .mapToInt(Integer::parseInt)
            .toArray();

    int minNumber = findMin(numbers);
    int maxNumber = findMax(numbers);
    
    return minNumber + " " + maxNumber;
    }

    private static int findMin(int[] numbers) {
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            if (result > numbers[i]) {
                result = numbers[i];
            }
        }
        return result;
    }

    private static int findMax(int[] numbers) {
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            if (result < numbers[i]) {
                result = numbers[i];
            }
        }
        return result;
    }
}