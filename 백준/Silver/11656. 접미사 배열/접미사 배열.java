import java.util.Scanner;

public class Main {
    public static int stringCompare(String str1, String str2){
        int str1Len = str1.length();
        int str2Len = str2.length();
        int compareLen = str1Len > str2Len ? str2.length() : str1.length();

        for (int i = 0; i < compareLen; i++){
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);

            if (ch1 != ch2)
                return (ch1 - ch2);
        }

        if (str1Len != str2Len)
            return (str1Len - str2Len);
        return (0);
    }

    public static void insertSort(String[] strArr) {
        for (int i = 0; i < strArr.length; i++){
            int insertIdx = i;
            while (insertIdx > 0 &&
                    stringCompare(strArr[insertIdx - 1], strArr[insertIdx]) > 0){
                String tempStr = strArr[insertIdx - 1];
                strArr[insertIdx - 1] = strArr[insertIdx];
                strArr[insertIdx] = tempStr;
                insertIdx--;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        String[] dictionaryOrder = new String[str.length()];

        for (int i = 0; i < dictionaryOrder.length; i++){
            dictionaryOrder[i] = str.substring(i);
        }

        insertSort(dictionaryOrder);

        for (int i = 0; i < dictionaryOrder.length; i++){
            System.out.println(dictionaryOrder[i]);
        }
        scanner.close();
    }
}
