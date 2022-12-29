import java.io.*;
import java.util.*;

class Element{
    int inputValue;
    int index;
    int outputValue;

    public Element(int inputValue, int index){
        this.inputValue = inputValue;
        this.index = index;
        this.outputValue = -1;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


        reader.readLine();
        int[] values = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Element[] elements = new Element[values.length];
        for (int i = 0; i < values.length; i++){
            elements[i] = new Element(values[i], i);
        }

        Stack<Element> stack = new Stack<Element>();
        for (Element element: elements){
            while (!stack.isEmpty() && stack.peek().inputValue < element.inputValue){
                elements[stack.peek().index].outputValue = element.inputValue;
                stack.pop();
            }
            stack.add(element);
        }

        for (Element element: elements){
            writer.write(element.outputValue + " ");
        }

        reader.close();
        writer.close();
    }

}
