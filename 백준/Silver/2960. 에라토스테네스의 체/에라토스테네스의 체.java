import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);

		int[] input = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int[] numbers = new int[input[0] + 1];
		Arrays.fill(numbers, 1);
		int numberOfTimes = input[1];
		int lastNumber = -1;
		int times = 0;
		while (times != numberOfTimes) {
			int index = findValidIndex(numbers);
			times += sieveArray(numbers, index, numberOfTimes - times);
		}
		scanner.close();
	}

	private static int findValidIndex(int[] number) {
		for (int index = 2; index < number.length; index++) {
			if (number[index] == 1)
				return index;
		}
		return -1;
	}

	private static int sieveArray(int[] numbers, int increase, int maxRepeat) {

		int repeat = 0;
		int index = 0;
		while (repeat < maxRepeat) {
			index += increase;
			if (index >= numbers.length)
				return repeat;
			else if (numbers[index] == 0)
				continue;
			numbers[index] = 0;
			repeat++;
		}
		if (repeat == maxRepeat)
			System.out.println(index);
		return repeat;
	}
}
