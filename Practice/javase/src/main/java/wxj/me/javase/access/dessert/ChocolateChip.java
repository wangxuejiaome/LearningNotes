package wxj.me.javase.access.dessert;

import java.lang.reflect.Method;

public class ChocolateChip extends Cookie {

    public ChocolateChip() {
        System.out.println("ChocolateChip constructor");
    }

    public void chomp(){
        //bite();
    }

    public static void main(String[] args) {
        ChocolateChip chocolateChip = new ChocolateChip();
        chocolateChip.chomp();
        Class c = chocolateChip.getClass();
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }
    }
}