package com.suai.streamchipher;

import com.suai.streamchipher.polynomial.Polynomial;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many registers in the algorithm: ");
        int numberReg = sc.nextInt();
        List<Register> registers = new ArrayList<>();

        for (int i = 0; i < numberReg; i++) {
            registers.add(new Register(creatingPolynomial(), creatingSeed()));
        }

        System.out.println("Enter the number of bits in the output: ");
        int numOfBitsInOutput = sc.nextInt();

        System.out.println("Output bits:");
        ArrayList<Integer> result = getResultOfThreeRegisters(numOfBitsInOutput, registers);

        System.out.println();
        System.out.println("End of algorithm\n-----------------------------\n");

        System.out.println("Calculation of linear complexity using the algorithm Berlekamp Massey");
        int[] res = new int[result.size()];
        for (int i = 0; i != result.size(); i++) {
            res[i] = result.get(i);
        }

        byte[] finalRes = new byte[result.size()];
        for (int i = 0; i != result.size(); i++) {
            finalRes[i] = (byte) res[i];
        }

        System.out.println("linear complexity is: " + BerlekampMassey(finalRes));

        int numOfNulls = 0;
        int numOfUnits = 0;
        for(int i = 0; i != numOfBitsInOutput; i++){
            if(res[i] == 0){
                numOfNulls++;
            } else {
                numOfUnits++;
            }
        }
        System.out.println("Number of nulls is: " + numOfNulls);
        System.out.println("Number of units is: " + numOfUnits);
    }

    public static Polynomial creatingPolynomial() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter degree");
        int degree = sc.nextInt();

        ArrayList<Integer> coefficient = new ArrayList<>();
        for (int i = 0; i < degree+1; i++) {
            int curDegree = degree - i;
            System.out.print("x" + curDegree + ": ");
            coefficient.add(sc.nextInt());
        }
        Polynomial polynomial = new Polynomial(degree, coefficient);
        System.out.println(polynomial);

        return polynomial;
    }

    public static LinkedList<Integer> creatingSeed() {
        Scanner sc = new Scanner(System.in);
        LinkedList<Integer> arrSeed = new LinkedList<>();
        System.out.println("Enter seed as sequence ones and zeros: ");
        String[] seedBits = sc.nextLine().split("\\s");
        for (String seedBit : seedBits) {
            arrSeed.add(Integer.parseInt(seedBit));
        }
        return arrSeed;
    }

    public static ArrayList<Integer> getResultOfThreeRegisters(int numOfBitsInOutput, List<Register> registers) {
        ArrayList<Integer> result = new ArrayList<>();
        int a, b, c;
        for (int i = 0; i <= numOfBitsInOutput; i++) {
            a = registers.get(2).bitShift();
            b = registers.get(1).bitShift();
            c = registers.get(0).bitShift();

            int nota;
            if (a == 1) {
                nota = 0;
            } else {
                nota = 1;
            }
            int res = (a & b) ^ ((nota) & c);

            result.add(res);
        }
        return result;
    }

    public static void testOfCorrectWorkOfRegister(Register threeBitsRegister) {
        int a1;
        System.out.println("Биты на выходе: ");
        for (int i = 0; i != 14; i++) {
            a1 = threeBitsRegister.bitShift();
            System.out.print(a1 + " ");
        }
    }

    public static int BerlekampMassey(byte[] s) {
        int L, N, m, d;
        int n = s.length;
        byte[] c = new byte[n];
        byte[] b = new byte[n];
        byte[] t = new byte[n];

        b[0] = c[0] = 1;
        N = L = 0;
        m = -1;

        while (N < n) {
            d = s[N];
            for (int i = 1; i <= L; i++)
                d ^= c[i] & s[N - i];
            if (d == 1) {
                System.arraycopy(c, 0, t, 0, n);
                for (int i = 0; (i + N - m) < n; i++)
                    c[i + N - m] ^= b[i];
                if (L <= (N >> 1)) {
                    L = N + 1 - L;
                    m = N;
                    System.arraycopy(t, 0, b, 0, n);
                }
            }
            N++;
        }
        return L;
    }

}