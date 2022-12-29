package com.example.lab1;

import java.util.Scanner;

/*
Данная программа вычисляет площадь треугольника в трехмерной системе координат
 */

public class Lab1 {

    public static void main(String args[]) {
        // Ввод координат
        Scanner in = new Scanner(System.in);
        System.out.println("Введите координаты первой точки через пробел:");
        String s1 = in.nextLine();
        System.out.println("Введите координаты второй точки через пробел:");
        String s2 = in.nextLine();
        System.out.println("Введите координаты третьей точки через пробел:");
        String s3 = in.nextLine();

        String[] point1Coord = s1.split(" ");
        String[] point2Coord = s2.split(" ");
        String[] point3Coord = s3.split(" ");

        // Создание точек по координатам
        Point3d point1 = new Point3d(Double.parseDouble(point1Coord[0]),
                Double.parseDouble(point1Coord[1]), Double.parseDouble(point1Coord[2]));

        Point3d point2 = new Point3d(Double.parseDouble(point2Coord[0]),
                Double.parseDouble(point2Coord[1]), Double.parseDouble(point2Coord[2]));

        Point3d point3 = new Point3d(Double.parseDouble(point3Coord[0]),
                Double.parseDouble(point3Coord[1]), Double.parseDouble(point3Coord[2]));

        // Проверка на равенство координат точек
        if (point1.equals(point2) || point2.equals(point3) || point1.equals(point3)) {
            System.out.println("Вычислить площадь невозможно");
        } else {
            System.out.println(computeArea(point1, point2, point3));    // Вывод площади треугольника
        }
    }

    // Вычисление площади треугольника
    public static double computeArea(Point3d a, Point3d b, Point3d c) {
        double ab = a.distanceTo(b);
        double ac = a.distanceTo(c);
        double bc = b.distanceTo(c);
        double p = (ab + ac + bc)/2;
        double s = Math.sqrt(p * (p - ab) * (p - bc) * (p - ac));

        return s;
    }

}
