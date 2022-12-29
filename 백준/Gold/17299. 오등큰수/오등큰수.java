import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] count = new int[1000001];
        //  input size
        int size = Integer.parseInt(reader.readLine());
        //  input number
        StringTokenizer inputArr = new StringTokenizer(reader.readLine());

        int[] arr = new int[size];
        int[] result = new int[size];
        Stack<Integer> indexStack = new Stack<Integer>();

        for (int i = 0; i < size; i++){
            arr[i] = Integer.parseInt(inputArr.nextToken());
        }

        //  counting
        for (int i = 0; i < size; i++){
            count[arr[i]]++;
        }

        for (int i = 0; i < size; i++){
            while (!indexStack.isEmpty() && count[arr[indexStack.peek()]] < count[arr[i]]){
                result[indexStack.pop()] = arr[i];
            }
            indexStack.add(i);
        }

        while (!indexStack.isEmpty()){
            result[indexStack.pop()] = -1;
        }

//        //  test
//        for (int i = 0; i < size; i++){
//            writer.write(count[arr[i]] + " ");
//        }
//        writer.newLine();

        for (int i = 0; i < size; i++){
            writer.write(result[i] + " ");
        }
        writer.flush();
        reader.close();
        writer.close();
    }

}
