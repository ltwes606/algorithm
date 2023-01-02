import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private static BufferedWriter writer;

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		writer = new BufferedWriter(new OutputStreamWriter(System.out));
		int max = 1000000;
		boolean[] primes = getSieveOfEratosthenes(max);

		int input;
		while ((input = scanner.nextInt()) != 0){
			findGoldbach(input, primes);
		}
		scanner.close();
		writer.close();
	}

	private static boolean[] getSieveOfEratosthenes(int n) {
		boolean[] primes = new boolean[n + 1];
		Arrays.fill(primes, true);
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (primes[i]) {
				for (int j = i * i; j < primes.length; j += i) {
					primes[j] = false;
				}
			}
		}
		return primes;
	}

	private static void findGoldbach(int number, boolean[] primes) throws IOException {
		for (int i = 2; i <= number / 2; i++) {
			if (primes[i] && primes[number - i]) {
				writer.write(String.format("%d = %d + %d\n", number, i, number - i));
				return;
			}
		}
		writer.write("Goldbach's conjecture is wrong.");
	}
}
