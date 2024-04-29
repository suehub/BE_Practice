package cloneMethod;

import java.util.Arrays;

public class shallowDeep {
    public static void main(String[] args) {

        System.out.println("Shallow copy"); // 얕은 복사, 변경O, call-by-reference 유사 개념

        int[] a = {0,1,2,3,4,5,6,7,8,9};
        int[] b = a;

        a[0] = 100;

        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }

        System.out.println("---------------------");

        System.out.println("Deep Copy"); //깊은 복사, 변경X, call-by-values 유사 개념

        int[] c = {0,1,2,3,4,5,6,7,8,9};
        int[] d = Arrays.copyOf(c,c.length);

        c[0] = 100;

        for (int i=0; i<d.length; i++){
            System.out.println(d[i]);
        }
    }
}
