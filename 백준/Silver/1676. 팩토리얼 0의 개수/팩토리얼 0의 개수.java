import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.parseInt(reader.readLine());
		int two = 0;
		int five = 0;
		for (int i = 2; i <= N; i++) {
			// two += countPrimeFactor(2, i);
			five += countPrimeFactor(5, i);
		}
		writer.write(String.valueOf(five));
		reader.close();
		writer.close();
	}

	private static int countPrimeFactor(int primeFactor, int number) {
		int divisible = primeFactor;
		int count = 0;
		while (number % divisible == 0) {
			divisible *= primeFactor;
			count++;
		}
		return count;
	}
}
