import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        // 깊은 복사
        int[][] sortedTargets = new int[targets.length][];
        for (int i = 0; i < sortedTargets.length; i++) {
            sortedTargets[i] = targets[i].clone();
        }
        
        // 시간순 정렬
        Arrays.sort(sortedTargets, (target1, target2) -> target1[0] - target2[0]);
        
        // 계산
        int result = 0;
        int defenseTime = -1;
        for (int i = 0; i < sortedTargets.length; i++) {
            int[] target = sortedTargets[i];
            if (defenseTime < target[0]) {
                result++;
                defenseTime = target[1] - 1;
            } else if (defenseTime > target[1] - 1) {
                defenseTime = target[1] - 1;
            }
        }
        return result;
    }
}