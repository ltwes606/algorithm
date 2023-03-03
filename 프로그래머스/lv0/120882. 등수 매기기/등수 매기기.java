import java.util.Arrays;
import java.util.Collections;

class Student {

    private int order;
    private int mathScore;
    private int englishScore;
    private double average;

    public Student(int mathScore, int englishScore) {
        this.mathScore = mathScore;
        this.englishScore = englishScore;
    }

    public void saveAverage() {
        average = (mathScore + englishScore) / 2.0;
    }

    public void changeOrder(Double[] averages) {
        order = findOrder(averages);
    }

    private int findOrder(Double[] averages) {
        for (int i = 0; i < averages.length; i++) {
            if (average == averages[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    public int getOrder() {
        return order;
    }

    public double getAverage() {
        return average;
    }
}

class Solution {

    public int[] solution(int[][] score) {
        Student[] students = new Student[score.length];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(score[i][0], score[i][1]);
            students[i].saveAverage();
        }

        Double[] averages = new Double[students.length];
        for (int i = 0; i < averages.length; i++) {
            averages[i] = students[i].getAverage();
        }

        Arrays.sort(averages, Collections.reverseOrder());

        for (int i = 0; i < students.length; i++) {
            students[i].changeOrder(averages);
        }

        int[] result = new int[students.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = students[i].getOrder();
        }
        return result;
    }
}