package wxj.me.javase.access;

import java.lang.reflect.Method;

import wxj.me.javase.access.dessert.Cookie;

public class ChocolateChip extends Cookie {

    public ChocolateChip() {
        System.out.println("ChocolateChip constructor");
    }

    public void chomp(){
        //! bite(); // Can't access bite
    }

    public static void main(String[] args) {
        ChocolateChip chocolateChip = new ChocolateChip();
        chocolateChip.chomp();
        Class c = chocolateChip.getClass();
        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }
    }
}

// Cookie 类中存在一个 bite() 方法，该方法也存在于任何一个从 Cookie 继承而来的类中。
// 但是由于 bite() 有包访问权限，而且它位于另外一个包内，所以我们在这个包内无法使用它。