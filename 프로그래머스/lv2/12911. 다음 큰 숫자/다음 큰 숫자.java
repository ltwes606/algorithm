class Solution {
    public int solution(int number) {
        int targetOneCount = countOne(number);
        int result = number + 1;
        while (targetOneCount != countOne(result)) {
            result++;
        }
        return result;
    }
    
    private static int countOne(int number) {
        int result = 0;
        while (number != 0) {
            if (number % 2 == 1) {
                result++;
            }
            number /= 2;
        }
        return result;
    }
}