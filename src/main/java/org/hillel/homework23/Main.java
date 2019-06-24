package org.hillel.homework23;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static ThreadPoolExecutor poolExecutor;
    private static List<Future<List<Integer>>> futures = new ArrayList<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int poolSize = Runtime.getRuntime().availableProcessors();
        int number = 1000000;
        int queue = 10;
        int factor = number / queue;
        long t = System.currentTimeMillis();
        System.out.println("factor: " + factor);
        poolExecutor = new ThreadPoolExecutor(poolSize,
                poolSize,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queue));
        fork(number, factor);
        List<Integer> prepareList = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            prepareList.addAll(future.get());
        }
        findPrimeNumbers(prepareList);
//        System.out.println(findPrimeNumbers(prepareList).toString());
//        findPrimeNumbers(prepareData(1, number));
        System.out.println(System.currentTimeMillis() - t);
        poolExecutor.shutdown();
    }

    private static void fork(int n, int factor) {
        int c = n / factor;
        int pr = n % factor;
        for (int i = 0; i < c; i++) {
            int min = i * factor + 1;
            int max = (i + 1) * factor;
            Future<List<Integer>> f = poolExecutor.submit(() -> prepareData(min, max));
            futures.add(f);
        }
        if (pr != 0) {
            Future<List<Integer>> f = poolExecutor.submit(() -> prepareData(c * factor + 1, n));
            futures.add(f);
        }

    }

    private static List<Integer> findPrimeNumbers(List<Integer> list) {
        long t = System.currentTimeMillis();
        List<Integer> primeList = new ArrayList<>();
        primeList.add(1);
        for (int i = 1; i < list.size(); i++) {
            boolean isPrime = true;
            for (int j = 1; j < i; j++) {
                if (list.get(i) % list.get(j) == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primeList.add(list.get(i));
            }
        }
        System.out.println(System.currentTimeMillis() - t);
        return primeList;
    }

    private static List<Integer> prepareData(int min, int max) {
        long t = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int j = min; !(j > max); j++) {
            switch (j) {
                case (1):
                    list.add(1);
                    break;
                case (2):
                    list.add(2);
                    break;
                case (3):
                    list.add(3);
                    break;
                default: {
                    if ((j % 2) != 0
                            & (j % 3) != 0
                            & !((Math.sqrt(j) % 1) == 0)) { // )
                        list.add(j);
                    }
                }
            }
        }
        System.out.println(System.currentTimeMillis() - t);
        return list;
    }
}