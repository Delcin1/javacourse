package com.example.lab1;

/**
 * трехмерный класс точки
 */

public class Point3d extends Point2d {

    private double zCoord;

    /** Конструктор инициализации **/
    public Point3d( double x, double y, double z) {
        super(x, y);
        zCoord = z;
    }

    /** Конструктор по умолчанию **/
    public Point3d() {
        this(0, 0, 0);
    }

    /** Возвращание координаты z **/
    public double getZ() {
        return zCoord;
    }

    /** Установка значения координаты z **/
    public void setZ(double val) {
        zCoord = val;
    }

    /** Сравнение координат 2 точек **/
    public boolean equals(Point3d point) {
        if (getX() == point.getX() && getY() == point.getY() && zCoord == point.getZ()) {
            return true;
        }
        return false;
    }

    /** Вычисление дистанции между двумя точками **/
    public double distanceTo(Point3d point) {
        double distance = Math.sqrt(Math.pow(getX() - point.getX(), 2)
                + Math.pow(getY() - point.getY(), 2) + Math.pow(zCoord - point.getZ(), 2));
        return Math.round(distance*100)/100;
    }
}
