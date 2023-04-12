import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution {

    public static final int NAME = 0;
    public static final int START = 1;
    public static final int PLAYTIME = 2;

    public String[] solution(String[][] plansString) {
        Plan[] plans = getPlans(plansString);

//        Arrays.sort(plans);

//        printPlans(plans);

        return run(plans);
    }

    private static Plan[] getPlans(String[][] plansString) {
        Plan[] plans = new Plan[plansString.length];
        for (int i = 0; i < plans.length; i++) {
            plans[i] = new Plan();
            plans[i].setName(plansString[i][NAME]);
            plans[i].setStartValue(valueOfStart(plansString[i][START]));
            plans[i].setPlaytime(parsePlaytimeToInt(plansString[i][PLAYTIME]));
        }
        return plans;
    }

    private static int valueOfStart(String start) {
        int result = Integer.parseInt(start.substring(0, 2)) * 60;
        result += Integer.parseInt(start.substring(3, 5));
        return result;
    }

    private static int parsePlaytimeToInt(String playtime) {
        return Integer.parseInt(playtime);
    }

    private static String[] run(Plan[] plans) {
        Queue<Plan> runningQueue = createRunningQueue(plans);
        LinkedList<Object[]> blockedDeque = new LinkedList<>();

        List<Plan> result = new ArrayList<>();
        while (!runningQueue.isEmpty()) {
            Plan plan = runningQueue.poll();
            if (runningQueue.isEmpty()) {
                result.add(plan);
                break;
            }

            int currentPlaytime =
                    runningQueue.peek().getStartValue() - plan.getStartValue();
            int remainingPlaytime =
                    currentPlaytime - plan.getPlaytime();

            if (remainingPlaytime >= 0) {
                result.add(plan);
                performBlockedQueue(blockedDeque, remainingPlaytime, result);
                continue;
            }
            blockedDeque.addFirst(new Object[]{plan, -remainingPlaytime});
        }

        while (!blockedDeque.isEmpty()) {
            result.add((Plan) blockedDeque.removeFirst()[0]);
        }

        return result.stream().map(p -> p.getName()).toArray(String[]::new);
    }

    private static Queue<Plan> createRunningQueue(Plan[] plans) {
        Queue<Plan> runningQueue = new PriorityQueue<>();
        for (int i = 0; i < plans.length; i++) {
            runningQueue.offer(plans[i]);
        }
        return runningQueue;
    }

    private static void performBlockedQueue(LinkedList<Object[]> blockedDeque,
            int remainingPlaytime,
            List<Plan> result) {
        while (remainingPlaytime != 0 && !blockedDeque.isEmpty()) {
            Object[] element = blockedDeque.poll();
            Plan plan = (Plan) element[0];
            int currentPlaytime = (int) element[1];

            if (remainingPlaytime < currentPlaytime) {
                blockedDeque.addFirst(new Object[]{plan, currentPlaytime - remainingPlaytime});
                return;
            }
            remainingPlaytime -= currentPlaytime;
            result.add(plan);
        }
    }

    private static void printPlans(Plan[] plans) {
        for (int i = 0; i < plans.length; i++) {
            System.out.println(plans[i]);
        }
    }

    public static void main(String[] args) {
        String[][] plansString = new String[4][3];
        plansString[0][0] = "science";
        plansString[0][1] = "02:40";
        plansString[0][2] = "50";

        plansString[1][0] = "music";
        plansString[1][1] = "12:20";
        plansString[1][2] = "40";

        plansString[2][0] = "history";
        plansString[2][1] = "14:00";
        plansString[2][2] = "30";

        plansString[3][0] = "computer";
        plansString[3][1] = "12:30";
        plansString[3][2] = "100";

//        String[][] plansString = new String[3][3];
//        plansString[0][0] = "korean";
//        plansString[0][1] = "11:40";
//        plansString[0][2] = "30";
//
//        plansString[1][0] = "english";
//        plansString[1][1] = "12:10";
//        plansString[1][2] = "20";
//
//        plansString[2][0] = "math";
//        plansString[2][1] = "12:30";
//        plansString[2][2] = "40";

        String[] solution = new Solution().solution(plansString);
        for (String plan : solution) {
            System.out.println(plan);
        }
    }

    static class Plan implements Comparable<Plan> {

        private String name;
        private int startValue;
        private int playtime;

        public Plan() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStartValue() {
            return startValue;
        }

        public void setStartValue(int startValue) {
            this.startValue = startValue;
        }

        public int getPlaytime() {
            return playtime;
        }

        public void setPlaytime(int playtime) {
            this.playtime = playtime;
        }

        @Override
        public int compareTo(Plan otherPlan) {
            return startValue - otherPlan.getStartValue();
        }

        @Override
        public String toString() {
            return "Plan{" +
                    "name='" + name + '\'' +
                    ", startValue=" + startValue +
                    ", playtime=" + playtime +
                    '}';
        }
    }
}
