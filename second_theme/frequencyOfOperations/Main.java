package second_theme.frequencyOfOperations;

import java.util.*;


public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        Thread maxCnt = new Thread(() -> {
            while (!Thread.interrupted()) {
                for (int c = 0; c < 1000; c++) {
                    synchronized (sizeToFreq) {
                        try {
                            sizeToFreq.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int maximum = -1000000;
                        int maxkey = -10000;
                        for (int key : sizeToFreq.keySet()) {
                            if (sizeToFreq.get(key) > maximum) {
                                maximum = sizeToFreq.get(key);
                                maxkey = key;

                            }

                        }
                        System.out.println("Частота " + maxkey +" Текущий максимум " + maximum);


                    }

                }
            }



        });
        maxCnt.start();


        for (int k = 0; k < 1000; k++) {

            Runnable task = () -> {

                String result = generateRoute("RLRFR", 100);
                int count = 0;
                for (int j = 0; j < result.length(); j++) {
                    if (result.charAt(j) == 'R') {
                        count++;
                    }


                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        int update = sizeToFreq.get(count) + 1;
                        sizeToFreq.put(count, update);

                        System.out.printf("- %d (%d раз)", sizeToFreq.get(count), count);
                        System.out.println();
                    } else {
                        sizeToFreq.put(count, 1);
                        System.out.printf("- %d (%d раз)", 1, count);
                        System.out.println();
                    }
                    sizeToFreq.notify();

                }

            };
            Thread mythread = new Thread(task);
            threads.add(mythread);
            mythread.start();
            Thread.sleep(1);

        }


        for (Thread thread : threads) {
            thread.join();
        }
        maxCnt.interrupt();


    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }


}


