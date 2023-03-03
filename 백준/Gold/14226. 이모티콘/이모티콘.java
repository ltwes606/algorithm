import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public Integer nextInt() {
            return Integer.parseInt(next());
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            this.builder = new StringBuilder();
        }

        public void print(String str) {
            builder.append(str);
        }

        public void println(String str) {
            print(str + "\n");
        }

        public void close() {
            try {
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.delete(0, builder.length());
        }
    }

    static class Emoticon {

        private int screenSize;
        private int clipboardSize;
        private int count;

        public Emoticon(int screenSize, int clipboardSize, int count) {
            this.screenSize = screenSize;
            this.clipboardSize = clipboardSize;
            this.count = count;
        }

        public Emoticon(Emoticon emoticon) {
            this.screenSize = emoticon.getScreenSize();
            this.clipboardSize = emoticon.getClipboardSize();
            this.count = emoticon.getCount();
        }


        public int getScreenSize() {
            return screenSize;
        }

        public void setScreenSize(int screenSize) {
            this.screenSize = screenSize;
        }

        public int getClipboardSize() {
            return clipboardSize;
        }

        public void setClipboardSize(int clipboardSize) {
            this.clipboardSize = clipboardSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        private void increaseCount() {
            count++;
        }

        public void storeClipboard() {
            clipboardSize = screenSize;
            increaseCount();
        }

        public void pasteScreen() {
            if (clipboardSize == 0) {
                return;
            }

            screenSize += clipboardSize;
            increaseCount();
        }

        public void decreaseScreenCount() {
            screenSize--;
            increaseCount();
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int desiredScreenSize = reader.nextInt();

        // 연산
        int result = bfs(desiredScreenSize);

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    /**
     * @param desiredScreenSize
     * @return minCount
     */
    private static int bfs(int desiredScreenSize) {
        Queue<Emoticon> queue = new LinkedList<>();

        queue.add(new Emoticon(1, 0, 0));

        boolean[][] visited = new boolean[desiredScreenSize + 1][desiredScreenSize + 1];
        while (!queue.isEmpty()) {
            Emoticon emoticon = queue.remove();
            if (visited[emoticon.getScreenSize()][emoticon.getClipboardSize()]) {
                continue;
            }

            visited[emoticon.getScreenSize()][emoticon.getClipboardSize()] = true;
            if (emoticon.getScreenSize() == desiredScreenSize) {
                return emoticon.getCount();
            }

            //화면에 있는 이모티콘을 모두 복사해서 클립보드에 저장한다.
            Emoticon computedEmoticon1 = new Emoticon(emoticon);
            computedEmoticon1.storeClipboard();
            if (validRange(computedEmoticon1, desiredScreenSize)) {
                queue.add(computedEmoticon1);
            }

            //클립보드에 있는 모든 이모티콘을 화면에 붙여넣기 한다.
            Emoticon computedEmoticon2 = new Emoticon(emoticon);
            computedEmoticon2.pasteScreen();
            if (validRange(computedEmoticon2, desiredScreenSize)) {
                queue.add(computedEmoticon2);
            }

            //화면에 있는 이모티콘 중 하나를 삭제한다.
            Emoticon computedEmoticon3 = new Emoticon(emoticon);
            computedEmoticon3.decreaseScreenCount();
            if (validRange(computedEmoticon3, desiredScreenSize)) {
                queue.add(computedEmoticon3);
            }
        }
        return -1;
    }

    private static boolean validRange(Emoticon emoticon, int maxSize) {
        int screenSize = emoticon.getScreenSize();
        int clipboardSize = emoticon.getClipboardSize();

        if (screenSize < 0 || maxSize < screenSize) {
            return false;
        }
        if (clipboardSize < 0 || maxSize < clipboardSize) {
            return false;
        }
        return true;
    }
}