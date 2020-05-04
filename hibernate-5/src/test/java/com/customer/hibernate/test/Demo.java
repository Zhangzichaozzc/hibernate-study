package com.customer.hibernate.test;

import java.util.Arrays;

import org.junit.Test;

/**
 * Demo
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class Demo {
//    private final char[] values;

//    private Demo(char[] values) {
//        this.values = values;
//    }

//    private char[] getValues() {
//        return values;
//    }
//
//
//    public static void main(String[] args) {
////        String str = "abcdefg";
////        Demo demo = new Demo(str.toCharArray());
////        for (char value : demo.getValues()) {
////            System.out.println(value);
////        }
////        for (int i = 0; i < demo.values.length; i++) {
////            demo.values[i] = (char) ('A' + i);
////        }
////        for (char value : demo.values) {
////            System.out.println(value);
////        }
//
//        String str = "aabcaabc";
//        System.out.println("str.replace('a', 'z') = " + str.replace('a', 'z'));
//
//        int[] ints = {1, 2, 3, 4, 5};
//        byte[] dest = new byte[5];
////        System.arraycopy(ints, 0, dest, 0, ints.length);
//        int[] ints1 = Arrays.copyOf(ints, 2);
//        for (int i : ints1) {
//            System.out.println(i);
//        }
////        for (int i : dest) {
////            System.out.println(i);
////        }
//    }


    public Demo() {
    }

    @Test
    public void test() {
        String a = "helloworld";
        String b = "hello";
        String c = "world";
        String d = b + "world";
        String e = "hello" + "world";
//        System.out.println("a = " + a + ", b = " + b);
//        System.out.println("a == e = " + a == e);
//        System.out.println("a.equals(e) = " + a.equals(e));
        System.out.println("a == e = " + (a == e));
        System.out.println("a == d = " + (a == d));
//        System.out.println("a == d.intern = " + (a .equals( d)));
//        System.out.println(d.equals("helloworld"));
        System.out.println(a == d.intern());
    }

    class A {
        private int anInt;

        public A(int anInt) {
            this.anInt = anInt;
        }

        @Override
        public String toString() {
            return "A{" +
                    "anInt=" + anInt +
                    '}';
        }
    }

    @Test
    public void testTransport(){
        A a = new A(1);
        fun(a);
        System.out.println(a);
    }

    private void fun(A a) {
        a = new A(2);
    }
}
