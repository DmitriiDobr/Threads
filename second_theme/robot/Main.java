package second_theme.robot;

import java.util.*;


public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static int maximum = -100000;
    public static int maxKey = -100000;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> mythreads = new ArrayList<>();
        for (int k = 0; k < 1000; k++) {

            Runnable task = () -> {
                synchronized (sizeToFreq) {
                    String result = generateRoute("RLRFR", 100);
                    int count = 0;
                    System.out.println("check");
                    for (int j = 0; j < result.length(); j++) {
                        if (result.charAt(j) == 'R') {
                            count++;
                        }


                    }
                    if (sizeToFreq.containsKey(count)) {
                        int update = sizeToFreq.get(count) + 1;
                        sizeToFreq.put(count, update);
                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }

            };
            Thread mythread = new Thread(task);
            mythreads.add(mythread);
            mythread.start();

        }
        for (Thread thr: mythreads){
            thr.join();

        }
        countMaximum();
        printFreq();


    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void countMaximum() {

        for (int key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maximum) {
                maximum = sizeToFreq.get(key);
                maxKey = key;

            }
        }
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n", maxKey, maximum);
        System.out.print("Другие размеры:");
    }

    public static void printFreq() {

        for (int key : sizeToFreq.keySet()) {
            System.out.println();
            System.out.printf("- %d (%d раз)", key, sizeToFreq.get(key));
        }
    }

}

