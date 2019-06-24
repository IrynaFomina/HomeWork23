package org.hillel.homework23;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

class PrimeNumbers extends RecursiveTask<List<Integer>> {
    private static int[] inputData;
    private int min, max;
    private static List<Integer> primeList = new ArrayList<>();

    public PrimeNumbers(int[] inputData) {
        this.inputData = inputData;
        this.min = 0;
        this.max = inputData.length - 1;
    }

    public PrimeNumbers(int[] inputData, int min, int max) {
        this.inputData = inputData;
        this.min = min;
        this.max = max;
    }

    @Override
    protected List<Integer> compute() {
        int length = max - min + 1;
//        System.out.println(min + " "+max + " "+ length);
        if (primeList.size() < 3) {
            primeList.add(1);
            primeList.add(2);
            primeList.add(3);
        }
        int factor = 5;
        if (length <= factor) {
            for (int i = min; i <= max; i++) {
                if (inputData[i] % 2 != 0 & inputData[i] % 3 != 0 & inputData[i] != 1) {
                    boolean isPrime = true;
                    for (int j = 2; j < inputData[i]; j++) {
                        if (inputData[i] % j == 0) {
                            isPrime = false;
                            break;
                        }
                    }
                    if (isPrime) {
                        primeList.add(inputData[i]);
//                        System.out.println(Thread.currentThread().getName() + " added " +inputData[i] );
                    }
                }
            }
        } else {
            PrimeNumbers primeNumbers1 = new PrimeNumbers(inputData, min, min - 1 + length / 2);
            PrimeNumbers primeNumbers2 = new PrimeNumbers(inputData, min + length / 2, max);
            primeNumbers2.fork();
            primeNumbers1.compute();
            primeNumbers2.join();
        }
        return primeList;
    }

}

public class ForkJavaPoolHW {
    public static void main(String[] args) {
        int data[] = IntStream.rangeClosed(1, 1000000).toArray();
        long t = System.currentTimeMillis();
        List<Integer> res = new ForkJoinPool().invoke(new PrimeNumbers(data));
//        System.out.println("res is " + res);
        System.out.println(System.currentTimeMillis() - t);
    }
}
