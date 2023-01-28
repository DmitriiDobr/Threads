package forth_theme;


import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static final BlockingQueue<String> a = new ArrayBlockingQueue<>(100);
    public static final BlockingQueue<String> b = new ArrayBlockingQueue<>(100);
    public static final BlockingQueue<String> c = new ArrayBlockingQueue<>(100);
    public static final String text = "abc";
    public static final Integer length = 100_000;

    public static void main(String[] args) throws InterruptedException {


        Thread zero = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String new_text = generateText(text, length);
                try {
                    a.put(new_text);
                    b.put(new_text);
                    c.put(new_text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });

        Thread fst = new Thread(() -> {
            int maximumA = findMaximum(a, 'a');
            System.out.println("Maximum A " + maximumA);
        });

        Thread sec = new Thread(() -> {
            int maximumB = findMaximum(b, 'b');
            System.out.println("Maximum B " + maximumB);
        });

        Thread thr = new Thread(() -> {
            int maximumC = findMaximum(c, 'c');
            System.out.println("Maximum C " + maximumC);
        });
        zero.start();
        fst.start();
        sec.start();
        thr.start();
        zero.join();
        fst.join();
        sec.join();
        thr.join();


    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int findMaximum(BlockingQueue<String> queue, char letter) {
        int count = 0;
        int max = 0;
        String text;
        try {
            for (int i = 0; i < 10000; i++) {
                text = queue.take();
                for (char c : text.toCharArray()) {
                    if (c == letter) count++;
                }
                if (count > max) max = count;
                count = 0;
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted");
            return -1;
        }
        return max;
    }

}
