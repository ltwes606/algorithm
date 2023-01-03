import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int inputCount = scanner.nextInt();
		int[] inputs = new int[inputCount];
		for (int i = 0; i < inputs.length; i++)
			inputs[i] = scanner.nextInt();

		int maxInput = findMax(inputs);
		boolean[] primes = getSieveOfEratosthenes(maxInput);

		int[] outputs = new int[inputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = countGoldbachPartition(inputs[i], primes);
		}
		printOutputs(outputs);
		scanner.close();
	}

	private static int findMax(int[] array) {
		int maxInput = 0;
		for (int i = 0; i < array.length; i++) {
			if (maxInput < array[i])
				maxInput = array[i];
		}
		return maxInput;
	}

	private static boolean[] getSieveOfEratosthenes(int n) {
		boolean[] primes = new boolean[n + 1];
		Arrays.fill(primes, true);
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (primes[i]) {
				for (int j = i * i; j <= n; j += i) {
					primes[j] = false;
				}
			}
		}
		return primes;
	}

	private static int countGoldbachPartition(int n, boolean[] primes) {
		int count = 0;
		for (int i = 2; i <= n / 2; i++) {
			if (primes[i] && primes[n - i])
				count++;
		}
		return count;
	}

	private static void printOutputs(int[] outputs) {
		for (int i = 0; i < outputs.length; i++) {
			System.out.println(outputs[i]);
		}
	}
}
