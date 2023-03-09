package com.suai.streamchipher.polynomial;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private final int deg;
    private final ArrayList<Integer> coefficients;

    public Polynomial(int deg, ArrayList<Integer> coefficients) {
        this.deg = deg;
        this.coefficients = coefficients;
    }

    public int getDeg() {
        return deg;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getSpecificCoeff(int index) {
        return coefficients.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = deg; i >= 0; i--) {
            sb.append(coefficients.get(deg - i)).append("X^").append(i);
            if(i > 0) {
                sb.append("+");
            }
        }
        return sb.toString();
    }
}
