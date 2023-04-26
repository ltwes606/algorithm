import java.util.*;

class Solution {
    public String[] solution(String[] players, String[] callings) {
        // players 깊은 복사
        String[] result = players.clone();
        
        // 랭킹맵 생성
        Map<String, Integer> ranking = new HashMap<>();
        for (int i = 0; i < players.length; i++){
            ranking.put(result[i], i);
        }
        
        // 순위 변동
        for (int i = 0; i < callings.length; i++){
            String calledPlayer = callings[i];
            int currentRank = ranking.get(calledPlayer);
            
            // 결과 스왑
            String prevPlayer = result[currentRank - 1];
            result[currentRank - 1] = calledPlayer;
            result[currentRank] = prevPlayer;
            
            // 맵 스왑
            ranking.put(calledPlayer, currentRank - 1);
            ranking.put(prevPlayer, currentRank);
        }
        return result;
    }
}