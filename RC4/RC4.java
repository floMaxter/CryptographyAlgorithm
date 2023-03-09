package com.RC4_Final;

import java.lang.*;

public class RC4 {
    private static final int SIZE_MATRIX = 256;
    private final int[] S;
    public RC4() {
        S = new int[SIZE_MATRIX];
    }

    public byte[] encrypt(byte[] messageInByte, final byte[] key) {
        Character[] keySchedule = new Character[messageInByte.length];
        initWithVizhinerChipher(S, key);
        createMassiveOfKeys(S, keySchedule, messageInByte.length);
        byte[] message = new byte[messageInByte.length];
        for(int i = 0; i < messageInByte.length; i++) {
            message[i] = ((byte) (messageInByte[i] ^ keySchedule[i]));
        }

        return message;
    }

    private void init(int[] S, byte[] key) {
        for (int i = 0; i < SIZE_MATRIX; ++i) {
            S[i] = i;
        }
        int j = 0;
        for (int i = 0; i < SIZE_MATRIX; ++i) {
            int tmp = key[i % key.length];
            if(tmp < 0) {
                tmp += 256;
            }
            j = (j + S[i] + (char)tmp) % 256;

            swap(S, i, j);
        }
    }

    private void createMassiveOfKeys(int[] s, Character[] keySchedule, int plaintextLength) {
        int i = 0, j = 0;
        for (int k = 0; k < plaintextLength; ++k) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            swap(s, i, j);
            keySchedule[k] = (char) (s[(s[i] + s[j]) % 256]);
        }
    }

    private void swap(int[] S, int i, int j) {
        int tempValue = S[i];
        S[i] = S[j];
        S[j] = tempValue;
    }

    public void printMatrixS() {
        System.out.println("Матрица S: ");
        for(int i = 1; i < S.length + 1; i++) {
            if(i % 16 == 0) {
                System.out.println(S[i-1]);
            } else {
                System.out.print(S[i-1] + " ");
            }
        }
    }

    private void initWithVizhinerChipher(int[] S, byte[] key) {
        for (int i = 0; i < SIZE_MATRIX; ++i) {
            S[i] = i;
        }

        char[][] matrix = new char[256][256];
        char tmp1;
        for (int i = 0; i !=  256; i++ ){
            for (int j = 0; j != 256; j++){
                tmp1 = (char)((i+j) % 256);
                matrix[i][j] = tmp1;
            }
        }

        int j = 0;
        for (int i = 0; i < SIZE_MATRIX; ++i) {
            int tmp2 = key[i % key.length];
            if(tmp2 < 0) {
                tmp2 += 256;
            }
            j = (j + (int)matrix[tmp2][S[i]]) % 256;

            swap(S, i, j);
        }
    }
}

