package org.hillel.homework23;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors();
//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(poolSize, poolSize,60, TimeUnit.SECONDS,
//                        )
        long t = System.currentTimeMillis();
        System.out.println(findPrimeNumbers(prepareData(1500000)).toString());
//        System.out.println(prepareData(150).toString());
        System.out.println(System.currentTimeMillis() - t);
    }

    private static List<Integer> findPrimeNumbers(List<Integer> list) {
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
        return primeList;
    }

    private static List<Integer> prepareData(int n) {
        List<Integer> list = new ArrayList<>();
        if (n > 1) {
            list.add(1);
        }
        if (n > 2) {
            list.add(2);
        }
        if (n > 3) {
            list.add(3);

            for (int i = 4; i <= n; i++) {
                if ((i % 2) != 0
                        & (i % 3) != 0
                       ) { // & !((Math.sqrt(i) % 1) == 0)
                    list.add(i);
                }
            }
        }
        return list;
    }
}