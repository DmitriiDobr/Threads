package third_theme.generate_nicknames;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger fst = new AtomicInteger(0);
    public static AtomicInteger sec = new AtomicInteger(0);
    public static AtomicInteger thr = new AtomicInteger(0);
    public static String[] texts = new String[100_000];

    public static void main(String[] args) {

        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


        new Thread(() -> {
            for (String text : texts) {
                StringBuilder sb = new StringBuilder(text);
                if (text.equals(sb.reverse().toString())&&(text.length()>=3 && text.length()<=5)) {
                    if (text.length()==3){
                        fst.addAndGet(1);
                    }else if (text.length()==4){
                        sec.addAndGet(1);
                    }else{thr.addAndGet(1);}

                }

            }


        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if ((text.chars().distinct().count() == 1)&&(text.length()>=3 && text.length()<=5)) {
                    if (text.length()==3){
                        fst.addAndGet(1);
                    }else if (text.length()==4){
                        sec.addAndGet(1);
                    }else{thr.addAndGet(1);}

                }


            }


        }).start();


        new Thread(() -> {
            for (String text : texts) {
                if (isAlphabaticOrder(text)&&(text.length()>=3 && text.length()<=5)) {
                    if (text.length()==3){
                        fst.addAndGet(1);
                    }else if (text.length()==4){
                        sec.addAndGet(1);
                    }else{thr.addAndGet(1);}

                }


            }

        }).start();

        System.out.println("Красивых слов с длиной 3: " + fst);
        System.out.println("Красивых слов с длиной 4: " + sec);
        System.out.println("Красивых слов с длиной 5: " + thr);

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isAlphabaticOrder(String s) {
        // length of the string
        int n = s.length();
        char c[] = new char[n];
        for (int i = 0; i < n; i++) {
            c[i] = s.charAt(i);
        }
        Arrays.sort(c);
        for (int i = 0; i < n; i++)
            if (c[i] != s.charAt(i))
                return false;

        return true;
    }
}
