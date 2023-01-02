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

		String inputCount = reader.readLine();
		int[] inputArray = Arrays.stream(reader.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		long primeCount = Arrays.stream(inputArray).filter(number -> isPrime(number))
			.count();
		writer.write(String.valueOf(primeCount));
		reader.close();
		writer.close();
	}

	private static boolean isPrime(int number) {
		if (number < 2) {
			return false;
		}
		return IntStream.range(2, (int)Math.sqrt(number) + 1)
			.allMatch(divisible -> number % divisible != 0);
	}
}
