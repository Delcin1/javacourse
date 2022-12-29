package com.example.primes;

/*
Данная программа выводит все простые числа меньше 100
 */

public class Primes {
    public static void main(String[] args) {
        for (int i=2; i<100; i++) {
            if (isPrime(i)) {
                System.out.println(i);
            }
        }
    }

    public static boolean isPrime(int n) {   //проверка: является ли число простым
        for (int i=2; i < n/2; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}