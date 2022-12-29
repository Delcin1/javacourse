package com.example.lab1;

public class Point2d {

    /** Координата x **/
    private double xCoord;
    /** Координата y **/
    private double yCoord;

    /** Конструктор инициализации **/
    public Point2d( double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    /** Конструктор по умолчанию **/
    public Point2d() {
        this(0, 0);
    }

    /** Возвращание координаты x **/
    public double getX() {
        return xCoord;
    }

    /** Возвращание координаты y **/
    public double getY() {
        return yCoord;
    }

    /** Установка значения координаты x **/
    public void setX(double val) {
        xCoord = val;
    }

    /** Установка значения координаты y **/
    public void setY(double val) {
        yCoord = val;
    }

}
