package com.RC4_Final;

import java.util.Scanner;
import java.lang.String;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your message:");
        String message;
        while (true) {
            message = input.nextLine();
            if (checkForHexOnly(message)) {
                break;
            }
            System.out.println("You entered wrong data. Please try again.");
        }

        System.out.println("Enter your key:");
        String key;
        while (true) {
            key = input.nextLine();
            if (checkForHexOnly(key)) {
                break;
            }
            System.out.println("You entered wrong data. Please try again.");
        }

        RC4 algorithmRC4 = new RC4();

        byte[] cipherMessage = algorithmRC4.encrypt(fromCharToHex(message), fromCharToHex(key));
        byte[] decryptionMessage = algorithmRC4.encrypt(cipherMessage, fromCharToHex(key));

        System.out.println("Открытый текст: " + message);
        System.out.println("Ключ: " + key);
        System.out.print("Зашифрованный текст: ");
        String cipherString = convertToString(cipherMessage);
        System.out.print(cipherString + '\n');
        System.out.print("Расшифрован как: ");
        String decipherString = convertToString(decryptionMessage);
        System.out.print(decipherString + '\n');
        System.out.println("Matrix: ");
        algorithmRC4.printMatrixS();
    }

    public static String convertToString(byte[] cipherMessage) {
        char[] HEX_ARRAY = "0123456789ABCDEF ".toCharArray();
        char[] hexChars = new char[cipherMessage.length * 2];
        for (int j = 0; j < cipherMessage.length; j++) {
            int v = cipherMessage[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] convertToByteArray(String message) {
        byte[] messageInByte = new byte[message.length()];
        char[] messageInChar = message.toCharArray();
        for (int i = 0; i < messageInChar.length; i++) {
            messageInByte[i] = (byte) messageInChar[i];
        }
        return messageInByte;
    }

    private static boolean checkForHexOnly(String message) {
        if (message.length() == 0){
            return false;
        }
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) < 48 || message.charAt(i) > 57) {
                if (message.charAt(i) < 65 || message.charAt(i) > 70) {
                    return false;
                }
            }
        }
        return true;
    }

    private static byte[] fromCharToHex(String message) {
        char[] messageInChar = message.toCharArray();

        for (int i = 0; i < messageInChar.length; i++) {
            if (isLetter(messageInChar[i])) {
                messageInChar[i] = (char) ((int) messageInChar[i] - 55);
            } else {
                messageInChar[i] = (char) ((int) messageInChar[i] - 48);
            }
        }


        byte[] finalMessageInByte;
        if (messageInChar.length % 2 == 0) {
            finalMessageInByte = new byte[messageInChar.length / 2];
            int j = 0;
            for (int i = 0; i < messageInChar.length - 1; i += 2) {
                finalMessageInByte[j] = (byte) (((int) messageInChar[i] * 16) + (int) messageInChar[i + 1]);
                j++;
            }
        } else {
            finalMessageInByte = new byte[(messageInChar.length / 2) + 1];
            int j = 0;
            int i = 0;
            for (i = 0; i < messageInChar.length - 2; i += 2) {
                finalMessageInByte[j] = (byte) (((int) messageInChar[i] * 16) + (int) messageInChar[i + 1]);
                j++;
            }
            finalMessageInByte[j] = (byte) (messageInChar[i]);
        }

        return finalMessageInByte;
    }

    private static boolean isLetter(char symbol) {
        return symbol >= 65 && symbol <= 70;
    }
}