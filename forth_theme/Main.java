package forth_theme;


import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final BlockingQueue<String> a = new ArrayBlockingQueue<>(1000000);
    public static final BlockingQueue<String> b = new ArrayBlockingQueue<>(1000000);
    public static final BlockingQueue<String> c = new ArrayBlockingQueue<>(1000000);
    public static final String text =  "abc";
    public static final Integer length = 100;
    static AtomicInteger a_max = new AtomicInteger(-100);
    static AtomicInteger b_max = new AtomicInteger(-100);
    static AtomicInteger c_max = new AtomicInteger(-100);

    public static void main(String[] args) throws InterruptedException {


        Thread zero = new Thread(()->{
            for (int i=0; i<1000;i++) {
                String new_text = generateText(text, length);

                try {
                    a.put(new_text);
                    b.put(new_text);
                    c.put(new_text);
                    //System.out.println(new_text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("status");
            }


        });

        Thread fst =new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    int check = 0;
                    for (int cnt=0;cnt < a.take().length();cnt++){
                        if (a.take().charAt(cnt)=='a'){
                            //System.out.println("a take " + a.take());
                            //System.out.println("a");
                            check++;
                        };
                    }
                    //System.out.println("a check " + check);
                    if (check>a_max.get()){
                        a_max.set(check);
                        //System.out.println("status "+a_max.get());

                    }

                    //Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        Thread sec =new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    int check = 0;
                    //System.out.println("bbbb");
                    for (int cnt=0;cnt < b.take().length();cnt++){
                        if (b.take().charAt(cnt)=='b'){
                            //System.out.println("b");
                            check++;
                            //System.out.println(check);
                        };
                    }
                    if (check>b_max.get()){
                        b_max.set(check);
                        //System.out.println("status "+b_max.get());


                    }

                    //Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        Thread thr = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    int check = 0;
                    for (int cnt=0;cnt < c.take().length();cnt++){
                        if (c.take().charAt(cnt)=='c'){
                            //System.out.println("c");
                            check++;
                        };
                    }
                    if (check>c_max.get()){
                        c_max.set(check);
                        //System.out.println("status " + c_max.get());


                    }

                    //Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        zero.start();
        fst.start();
        sec.start();
        thr.start();
        zero.join();
        fst.join();
        sec.join();
        thr.join();
        zero.interrupt();
        sec.interrupt();
        thr.interrupt();
        fst.interrupt();
        System.out.println("a_final" + a_max.get());
        System.out.println("b_final" + b_max.get());
        System.out.println("c_final" + c_max.get());




    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
