import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    static class Calendar {

        private static final Integer MAX_EARTH_NUMBER = 15;
        private static final Integer MAX_SUN_NUMBER = 28;
        private static final Integer MAX_MOON_NUMBER = 19;
        public Integer earthNumber;
        public Integer sunNumber;
        public Integer moonNumber;

        public Calendar() {
            this.earthNumber = 1;
            this.sunNumber = 1;
            this.moonNumber = 1;
        }

        public Calendar(Integer earthNumber, Integer sunNumber, Integer moonNumber) {
            this.earthNumber = earthNumber;
            this.sunNumber = sunNumber;
            this.moonNumber = moonNumber;
        }

        public void up() {
            earthNumber = (earthNumber % MAX_EARTH_NUMBER)+ 1;
            sunNumber = (sunNumber % MAX_SUN_NUMBER) + 1;
            moonNumber = (moonNumber % MAX_MOON_NUMBER) + 1;
        }
    }

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(reader.readLine());
        Calendar inputCalendar = new Calendar(Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));

        // 초기값 설정
        Calendar currentCalendar = new Calendar();
        int result = 1;
        // 브루트포스 탐색
        while (!compareCalendar(currentCalendar, inputCalendar)) {
            currentCalendar.up();
            result++;
        }

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    /**
     * @return 같다면 true, 다르다면 false
     */
    private static Boolean compareCalendar(Calendar calendar1, Calendar calendar2) {
        if (Integer.compare(calendar1.earthNumber, calendar2.earthNumber) == 0 &&
                Integer.compare(calendar1.moonNumber, calendar2.moonNumber) == 0 &&
                Integer.compare(calendar1.sunNumber, calendar2.sunNumber) == 0) {
            return true;
        }
        return false;
    }
}