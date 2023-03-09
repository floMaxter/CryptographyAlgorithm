package com.suai.streamchipher;

import com.suai.streamchipher.polynomial.Polynomial;

import java.util.ArrayList;
import java.util.LinkedList;
public class Register {
    Polynomial polynomial;
    LinkedList<Integer> register;
    ArrayList<Integer> tapSequence;

    public Register(Polynomial polynomial, LinkedList<Integer> seed) {
        this.polynomial = polynomial;
        register = seed;
        tapSequence = initTapSequence();
    }

    private ArrayList<Integer> initTapSequence() {
        ArrayList<Integer> arrSequence = new ArrayList<>();
        for(int i = 1; i < polynomial.getDeg(); i++) {
            if(polynomial.getSpecificCoeff(i) != 0) {
                arrSequence.add(i);
            }
        }
        return arrSequence;
    }


    public int bitShift(){
        int resultOfXOR = register.get(tapSequence.get(0));
        for(int i = 1; i != tapSequence.size(); i++){
            resultOfXOR = XOR(resultOfXOR, register.get(tapSequence.get(i)-1));
        }
        register.addFirst(resultOfXOR);
        int ansForBit = register.getLast();
        register.removeLast();
        return ansForBit;
    }
    private int XOR(int first, int second){
        return first == second ? 0 : 1;
    }
    public void showCurrentCondition(){
        System.out.println("Size of register is:" + polynomial.getDeg());

        System.out.println("Bits in register are:");
        for(int i = 0; i != register.size(); i++){
            System.out.print(register.get(i));
        }
        System.out.println();

        System.out.println("TapSequence is:");
        for(int i = 0; i != tapSequence.size(); i++){
            System.out.print(tapSequence.get(i));
        }
        System.out.println();
    }
}
