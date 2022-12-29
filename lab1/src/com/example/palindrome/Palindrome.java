package com.example.palindrome;

import java.util.Scanner;

/*
Данная программа проверяет: является ли введенная пользователем строка палиндромом
 */

public class Palindrome {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        if (isPalindrome(s)) {
            System.out.println(String.format("Строка %s является палиндромом", s));
        } else {
            System.out.println(String.format("Строка %s не является палиндромом", s));
        }
    }

    public static String reverseString(String s) {     // Функция, которая переворачивает строку
        String reversedString = "";

        for (int i=s.length()-1; i>=0; i--) {
            reversedString += s.charAt(i);
        }

        return reversedString;
    }

    public static boolean isPalindrome(String s) {     //Функция, которая проверяет: является ли введенная пользователем
        if (s.equals(reverseString(s))) {              //строка палиндромом. Она сравнивает ее с ее перевернутой версией
            return true;
        } else {
            return false;
        }

    }
}
