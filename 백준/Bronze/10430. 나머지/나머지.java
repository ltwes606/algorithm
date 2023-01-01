import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) throws IOException {
		Remainder remainder = new Remainder(
			new BufferedReader(new InputStreamReader(System.in)),
			new BufferedWriter(new OutputStreamWriter(System.out)));

		remainder.run();
		remainder.terminal();
	}
}

class Remainder {
	BufferedReader reader;
	BufferedWriter writer;

	public Remainder(BufferedReader reader, BufferedWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void run() throws IOException {
		String input = reader.readLine();
		int[] array = Stream.of(input.split(" ")).mapToInt(Integer::parseInt).toArray();
		int A = array[0];
		int B = array[1];
		int C = array[2];

		Integer result1 = (A + B) % C;
		Integer result2 = ((A % C) + (B % C)) % C;
		Integer result3 = (A * B) % C;
		Integer result4 = ((A % C) * (B % C)) % C;
		println(result1.toString());
		println(result2.toString());
		println(result3.toString());
		println(result4.toString());
	}

	private void println(String str) throws IOException {
		writer.write(str + "\n");
	}

	public void terminal() throws IOException {
		reader.close();
		writer.close();
	}
}