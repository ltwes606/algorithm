import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public Integer nextInt() {
            return Integer.parseInt(next());
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            builder = new StringBuilder();
        }

        public void print(String str) {
            builder.append(str);
        }

        public void println(String str) {
            print(str + "\n");
        }

        public void close() {
            try {
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Schedule implements Comparable<Schedule>{
        private final int startTime;
        private final int endTime;

        public Schedule(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        @Override
        public int compareTo(Schedule otherSchedule) {
            if (startTime > otherSchedule.getStartTime()) {
                return 1;
            } else if (startTime == otherSchedule.getStartTime()) {
                if (endTime > otherSchedule.getEndTime()) {
                    return 1;
                } else if (endTime == otherSchedule.getEndTime()) {
                    return 0;
                }
            }
            return -1;
        }

        @Override
        public String toString() {
            return "Schedule{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        Schedule[] schedules = new Schedule[size];
        for (int i = 0; i < schedules.length; i++) {
            int startTime = reader.nextInt();
            int endTime = reader.nextInt();
            Schedule schedule = new Schedule(startTime, endTime);

            schedules[i] = schedule;
        }

        // 오름차순 정렬(시작 시간, 종료 시간 순)
        Arrays.sort(schedules);

        int time = 0;
        int count = 0;
        for (int i = 0; i < schedules.length; i++) {
            Schedule schedule = schedules[i];

            if (time <= schedule.getStartTime()) {
                time = schedule.getEndTime();
                count++;
            }

            time = Math.min(time, schedule.endTime);
        }

        // 결과 출력
        printResult(count);

        // 종료 작업
        close();
    }

    private static void printSchedules(Schedule[] schedules) {
        for (int i = 0; i < schedules.length; i++) {
            writer.println(schedules[i].toString());
        }
    }

    private static void printResult(int result) {
        writer.print(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}