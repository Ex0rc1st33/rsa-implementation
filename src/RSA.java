import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

public class RSA {
    public static int generatePrime() {
        Random rand = new Random();
        int p = rand.nextInt(2000);
        while (!checkPrime(p)) {
            p = rand.nextInt(2000);
        }
        return p;
    }

    public static boolean checkPrime(int number) {
        if (number < 2)
            return false;
        for (int i = 2; i < number; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static long modulo(long base, int exponent, int n) {
        long value = 1;
        long remainder;
        int e = exponent;
        List<Integer> list = new ArrayList<Integer>();
        while (e != 0) {
            list.add(e % 2);
            e /= 2;
        }
        remainder = (int) Math.pow(base, 1) % n;
        if (list.get(0) == 1) {
            value = (value * remainder) % n;
        }
        for (int i = 1; i < list.size(); i++) {
            remainder = (remainder * remainder) % n;
            if (list.get(i) == 1) {
                value = (value * remainder) % n;
            }
        }
        return value;
    }

    public static long encrypt(int m, int e, int n) {
        return modulo(m, e, n);
    }

    public static long decrypt(long encrypted, int d, int n) {
        return modulo(encrypted, d, n);
    }

    public static void main(String[] args) {
        int p = generatePrime();
        int q = generatePrime();

        int n = p * q;
        int phi;
        if (p != q)
            phi = (int) (n * (1 - 1 / (double) p) * (1 - 1 / (double) q));
        else
            phi = (int) (n * (1 - 1 / (double) p));

        int max_e = phi;
        int e = 1;
        boolean relative = true;
        for (int i = 3; i < max_e; i += 2) {
            for (int j = 3; j <= i; j += 2) {
                if (i % j == 0 && phi % j == 0) {
                    relative = false;
                    break;
                }
            }
            if (relative) {
                e = i;
                break;
            }
            relative = true;
        }

        int d = 1;
        while ((d * e) % phi != 1) {
            d += 1;
        }

        System.out.println("Adja meg a titkosítandó üzenetet: ");
        Scanner input = new Scanner(System.in);
        int m = input.nextInt();
        input.close();
        long encrypted = encrypt(m, e, n);
        long decrypted = decrypt(encrypted, d, n);

        System.out.println("A p és q prímek: p=" + p + " q=" + q);
        System.out.println("A prímek szorzata: n=" + n);
        System.out.println("Phi(n): " + phi);
        System.out.println("Publikus kulcs: (" + e + "," + n + ")");
        System.out.println("Privát kulcs: (" + d + "," + n + ")");
        System.out.println("A titkosítandó üzenet: " + m);
        System.out.println("A titkosított üzenet: " + encrypted);
        System.out.println("A visszafejtett üzenet: " + decrypted);
    }
}