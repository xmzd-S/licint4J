package main;
import libcint.LibcintLibrary;
import org.junit.Test;


public class Main {
    public static void main(String[] args) {
        int i = LibcintLibrary.INSTANCE.CINTlen_cart(1);
        System.out.println(i);
    }
    @Test
    public void test1(){
    }



}