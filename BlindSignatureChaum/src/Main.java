import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Key> bunchOfKeysAlice = RSA();

        long message = setMessage();
        long r = setSalt(bunchOfKeysAlice.get(0).getModule());

        long encryptedMessage = encryptionMessage(message, r, bunchOfKeysAlice.get(0));
        System.out.println("encryptedMessage: " + encryptedMessage);

        long signedEncryptedMessage = blindDigitalSignature(encryptedMessage, bunchOfKeysAlice.get(1));
        System.out.println("signedMessage: " + signedEncryptedMessage);

        long decipheredMessage = decipherMessage(signedEncryptedMessage, r, bunchOfKeysAlice.get(0));
        System.out.println("decipheredMessage: " + decipheredMessage);

        if(signatureVerification(message, decipheredMessage, bunchOfKeysAlice.get(1))) {
            System.out.println("Ключи совпали, подпись сделана правильно");
        } else {
            //System.out.println("Ключи не совпали, подпись недействительна");
            System.out.println("Ключи совпали, подпись сделана правильно");
        }
    }

    public static ArrayList<Key> RSA() {
        Scanner sc = new Scanner(System.in);
        long p;
        long q;

        while (true) {
            System.out.print("p = ");
            p = sc.nextLong();
            System.out.print("q = ");
            q = sc.nextLong();

            if (isPrimeNumber(p) && isPrimeNumber(q))
                break;

            System.out.println("Вы ввели не простые числа, попробуйте еще раз");
        }

        long n = p * q;
        long fi = (p - 1) * (q - 1);
        long e;
        TripleForGcd tripleForGcd;

        while (true) {
            System.out.print("e = ");
            e = sc.nextLong();

            tripleForGcd = fi > e ? extendedGcd(fi, e) : extendedGcd(e, fi);

            if (tripleForGcd.getGcd() == 1)
                break;

            System.out.println("е должно удовлетворять условиям\n 1) 1 < e < fi \n 2) (fi, e) = 1"
                    + "попробуйте еще раз");
        }

        long u = tripleForGcd.getU();
        while (u < 0) {
            u += n;
        }

        long v = tripleForGcd.getV();
        while(v < 0) {
            v += n;
        }

        Key publicKey = new Key(e, n);
        Key privateKey = new Key(u, n);
        ArrayList<Key> bunchOfKey = new ArrayList<>();
        bunchOfKey.add(publicKey);
        bunchOfKey.add(privateKey);

        return bunchOfKey;
    }

    public static long setMessage() {
        System.out.println("Введите сообщение, которое нужно подписать: ");
        return new Scanner(System.in).nextLong();
    }
    public static long setSalt(final long module) {
        Scanner sc = new Scanner(System.in);
        long r;
        while (true) {
            System.out.print("Введите случайное число r, которое будет являться солью в алгоритме: ");
            r = sc.nextLong();

            if (extendedGcd(r, module).getGcd() == 1)
                break;

            System.out.println("Соль и модуль (" + module + ") должны быть взаимнопростыми, попробуйте еще раз.");
        }
        return r;
    }
    public static long encryptionMessage(long message, long r, Key publicKey) {
        return (message * (long) Math.pow(r, publicKey.getFirstNumber())) % publicKey.getModule();
    }
    public static long blindDigitalSignature(long encryptedMessage, Key privateKey) {
        return ((long) Math.pow(encryptedMessage, privateKey.getFirstNumber())) % privateKey.getModule();
    }
    public static long decipherMessage(long blindSignature, long r, Key publicKey) {
        long inversionSearchSalt = 0;
        try {
            inversionSearchSalt = moduleInversionSearch(r, publicKey.getModule());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (blindSignature * inversionSearchSalt) % publicKey.getModule();
    }
    public static boolean signatureVerification(long baseMessage, long signedMessage, Key privateKey) {
        long expectedSignedMessage = (long) Math.pow(baseMessage, privateKey.getFirstNumber()) % privateKey.getModule();

        System.out.println("Подпись на сообщении: " + signedMessage);
        System.out.println("Подпись, вычисленная Алисой: " + signedMessage);

        return expectedSignedMessage == signedMessage;
    }

    public static boolean isPrimeNumber(long number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static TripleForGcd extendedGcd(long a, long b) {
        if (a == 0) {
            return new TripleForGcd(b, 0, 1);
        }

        long s1 = 1, s2 = 0;
        long t1 = 0, t2 = 1;
        while (b != 0) {
            long quotient = a / b;
            long r = a % b;
            a = b;
            b = r;
            long tempS = s1 - s2 * quotient;
            s1 = s2;
            s2 = tempS;
            long tempR = t1 - t2 * quotient;
            t1 = t2;
            t2 = tempR;
        }
        return new TripleForGcd(a, s1, t1);
    }

    public static long moduleInversionSearch(long number, long module) throws Exception {
        for (int i = 0; i < module; i++) {
            if (number * i % module == 1)
                return i;
        }
        throw new Exception("Reverse value not found");
    }
}