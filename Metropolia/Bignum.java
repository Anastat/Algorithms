package Lab04;

import java.util.Arrays;

public class Bignum {
    private byte[] number;          // least significand digit first (index 0), most significand last (index length-1)
    private static int mulCounter;  // variable to count the number of multiplications


    public Bignum(int n) {
        number = new byte[n];
    }

    public Bignum(String s) {
        int     n = s.length();
        number = new byte[n];

        for (int i = n-1; i >= 0; i--)
            number[n-i-1] = (byte)Character.getNumericValue(s.charAt(i));
    }


    /* print out the number to the string s */
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = number.length-1; i >= 0; i--)
            s.append(number[i]);

        return (s.toString());
    }

    //resize byte array if number starts from 0
    private void deleteStartZero() {
        byte [] newArr = new byte [number.length-1];
        for (int i = number.length-2; i>=0; i--) {
            newArr[i] = number[i];
        }
        number = newArr;
    }


    /* print out the given number (for debug only) */
    public void printBigNum(String s) {
        System.out.println(s + ": " + toString());
    }


    /* create a new number whose digits are x[from, to) */
    public Bignum selectBigNum(int from, int to) {
        Bignum r = new Bignum(to-from);

        for (int i = from; i < to; i++)
            r.number[i-from] = number[i];

        return r;
    }


    /* subtract two numbers this - y */
    public Bignum subBigNum(Bignum y) throws Exception{

        Bignum r = new Bignum(number.length);
        int    carry;

        // sub digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < number.length; i++) {
            r.number[i] = (byte)(number[i] - (i < y.number.length ? y.number[i] : 0) - carry);
            if (r.number[i] < 0) {
                carry = 1;
                r.number[i] += 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            throw new Exception("Overflow in subtraction\n");
        }

        return r;
    }


    /* add two numbers together this + y */
    public Bignum addBigNum(Bignum y) throws Exception{
        Bignum r, a, b;
        int    carry;

        // a is the larger number, b is the smaller
        if (number.length > y.number.length) {
            a = this; b = y;
        } else {
            a = y; b = this;
        }

        r = new Bignum(a.number.length);

        // add digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < a.number.length; i++) {
            r.number[i] = (byte)(a.number[i] + (i < b.number.length ? b.number[i] : 0) + carry);
            if (r.number[i] > 9) {
                carry = 1;
                r.number[i] -= 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            r.number = Arrays.copyOf(r.number, r.number.length+1);
            r.number[r.number.length-1] = 1;
        }

        return r;
    }


    /* multiply two numbers (this * y) together using divide-and-conquer technique */
    public Bignum mulBigNum(Bignum y) throws Exception{
        // you work is to be done here!!!

        if (y.number.length != this.number.length) makeEqualLength(y);
        int n = y.number.length;
        if (n==0) return null;

        int half =(Math.max(this.number.length, y.number.length)+1)/2;


        if (n==1) {
            if (this.number[0] == 0 || y.number[0] == 0) return new Bignum(String.valueOf(0));
            Bignum mult = new Bignum(String.valueOf(this.number[0]*y.number[0]));
            mulCounter++;
            return mult;
        }


        Bignum xl = this.selectBigNum(half, this.number.length);
        Bignum xr = this.selectBigNum(0, half);

        Bignum yl = y.selectBigNum(half, this.number.length);
        Bignum yr = y.selectBigNum(0, half);

        Bignum p1 = xl.mulBigNum(yl);
        Bignum p2 = xr.mulBigNum(yr);
        Bignum p3 = xr.addBigNum(xl).mulBigNum(yr.addBigNum(yl));


        // P1×10^n+(P3-P1-P2)×10^(n/2)+P2
        //if number have 0 at start - resize number arr
        if (p1.number[p1.number.length-1] == 0) p1.deleteStartZero();
        if (p3.number[p3.number.length-1] == 0) p3.deleteStartZero();


        if (n%2 == 1) n++;
        return bignumPowOfTen(p1, n).addBigNum(bignumPowOfTen(p3.subBigNum(p1).subBigNum(p2), n/2).addBigNum(p2));
    }


    public void clrMulCounter() {
        mulCounter = 0;
    }


    public int rclMulCounter() {
        return (mulCounter);
    }

    private void makeEqualLength(Bignum y) {
        int lenX = number.length;
        int lenY = y.number.length;

        if (lenX<lenY) {
            byte [] newNumber = new byte[lenY];
            for (int i = 0; i<number.length; i++)
                newNumber[i] = number[i];
            number = newNumber;
        } else {
            byte [] newNumber = new byte[lenX];
            for (int i = 0; i<y.number.length; i++)
                newNumber[i] = y.number[i];
            y.number = newNumber;
        }
    }

    private Bignum bignumPowOfTen (Bignum x, int pow) {

        Bignum bignumPow = new Bignum(x.number.length + pow);


        for (int i = 0; i<x.number.length; i++) {
            bignumPow.number[i+pow] = x.number[i];
        }
        return bignumPow;
    }

    // Generic function to merge multiple arrays of same type in Java
    public static <T> T[] merge(T[]... arrays)
    {
        int finalLength = 0;
        for (T[] array : arrays) {
            finalLength += array.length;
        }

        T[] dest = null;
        int destPos = 0;

        for (T[] array : arrays)
        {
            if (dest == null) {
                dest = Arrays.copyOf(array, finalLength);
                destPos = array.length;
            } else {
                System.arraycopy(array, 0, dest, destPos, array.length);
                destPos += array.length;
            }
        }
        return dest;
    }
}
