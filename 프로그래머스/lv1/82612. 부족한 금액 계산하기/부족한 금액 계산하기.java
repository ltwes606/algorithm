import java.io.BufferedReader;
import java.io.InputStreamReader;


class Solution {
    public long solution(int price, int money, int count) throws Exception {    
        // 합산
        long result = -money;
        for (int i = count; i > 0; i--) {
            result += i * price;
        }
        
        // 결과 반환
        if (result < 0) {
            return 0;
        }
        return result;
    }
}
