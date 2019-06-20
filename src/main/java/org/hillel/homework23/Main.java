package org.hillel.homework23;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println(findPrimeNumbers(1, 1000).toString());
    }


    private static List<Integer> findPrimeNumbers(int min, int max) {
        List<Integer> list = new ArrayList<>();
        for (int i = min; i < max; i++) {
            if (i < 4) {
                list.add(i);
            } else if (i % 2 != 0) {
                if (i % 3 != 0) {
                    Iterator<Integer> it = list.iterator();
                    boolean isPrime = true;
                    while (it.hasNext()) {
                        int value = it.next();
                        if ( (i % value == 0) & value !=1) {
                            isPrime = false;
                            break;
                        }
                    }
                    if (isPrime) list.add(i);
                }
            }
        }
        return list;
    }
    }