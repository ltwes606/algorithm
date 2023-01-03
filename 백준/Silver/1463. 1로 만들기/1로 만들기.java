import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<Integer>();

        arr.add(scanner.nextInt());

        int count = 0;
        while (!arr.contains(1)){
            int size = arr.size();
            for (int i = 0; i < size; i++){
                int value = arr.get(i);
                if (value % 3 == 0)
                    arr.add(value / 3);
                if (value % 2 == 0)
                    arr.add(value / 2);
                arr.set(i, value - 1);
            }
            count++;
        }

        System.out.println(count);
        scanner.close();
    }
}