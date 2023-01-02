import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		int[] inputArray = Arrays.stream(reader.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		int[] primes = getPrimes(inputArray[0], inputArray[1]);
		String[] primesString = Arrays.stream(primes)
			.mapToObj(String::valueOf)
			.toArray(String[]::new);
		String result = String.join("\n", primesString);
		writer.write(result);
		reader.close();
		writer.close();
	}

	private static int[] getPrimes(int start, int end) {
		return IntStream.range(start, end + 1)
			.filter(number -> isPrime(number))
			.toArray();
	}

	private static boolean isPrime(int number) {
		if (number < 2) {
			return false;
		}
		return IntStream.range(2, (int)Math.sqrt(number) + 1)
			.allMatch(divisible -> number % divisible != 0);
	}
}
