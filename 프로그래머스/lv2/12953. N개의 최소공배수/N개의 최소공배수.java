class Solution {
    public static final int MAX_NUMBER = 100;
    
    public int solution(int[] arr) {
        int[] resultDivisors = new int[MAX_NUMBER + 1];
        for (int i = 0; i < arr.length; i++) {
            int[] currentDivisors = countDivisors(arr[i]);
            toMaxValue(resultDivisors, currentDivisors);
        }
        int result = getProductOfFactors(resultDivisors);
        return result;
    }
    
    private static int[] countDivisors(int number) {
        int[] divisors = new int[MAX_NUMBER + 1];
        for (int i = 2; i <= number; i++) {
            while (number % i == 0) {
                number /= i;
                divisors[i]++;
            }
        }
        return divisors;
    }
    
    private static void toMaxValue(int[] destination, int[] source) {
        for (int i = 0; i < destination.length; i++) {
            if (destination[i] < source[i]) {
                destination[i] = source[i];
            }
        }
    }
    
    private static int getProductOfFactors(int[] divisors) {
        int result = 1;
        for (int i = 2; i < divisors.length ; i++) {
            if (divisors[i] == 0) {
                continue;
            }
            
            result *= (int)Math.pow(i, divisors[i]);
        }
        return result;
    }
}