import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        // 깊은 복사
        int[][] sortedTargets = new int[targets.length][];
        for (int i = 0; i < sortedTargets.length; i++){
            sortedTargets[i] = targets[i].clone();
        }
        
        // 시간순 정렬
        Arrays.sort(sortedTargets, new Comparator<int[]>() {
            @Override
            public int compare(int[] target1, int[] target2){
                return target1[0] - target2[0];
            }
        });
        
        // 계산
        int result = 0;
        Queue<Integer> queue = new PriorityQueue<>();
        for (int time = 0, i = 0; time <= 100_000_000; time++){
            if (!queue.isEmpty() && queue.peek() == time) {
                queue.clear();
                result++;
            }
            
            while (i < sortedTargets.length && sortedTargets[i][0] == time) {
                queue.offer(sortedTargets[i][1]);
                i++;
            }
        }
        
        if (!queue.isEmpty()) {
            queue.clear();
            result++;
        }
        return result;
    }
}