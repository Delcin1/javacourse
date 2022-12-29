package com.example.lab4;

import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    @Override
    public int numIterations(double x, double y) {
        double z2;
        double re = x;
        double im = y;
        double nextRe;
        double nextIm;
        int i = 0;

        while (i < MAX_ITERATIONS) {
            i += 1;
            nextRe = x + re * re - im * im;
            nextIm = y + -2 * re * im;
            re = nextRe;
            im = nextIm;
            z2 = re * re + im * im;
            if (z2 > 4) {
                return i;
            }
        }

        return -1;
    }

    public String toString() {
        return "Tricorn";
    }
}
