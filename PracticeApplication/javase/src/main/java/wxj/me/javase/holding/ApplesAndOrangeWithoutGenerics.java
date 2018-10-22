package wxj.me.javase.holding;

import java.util.ArrayList;

class Apple{
    private static long counter;
    private final long id = counter++;
    public long id(){
        return id;
    }
}

class Orange{}

public class ApplesAndOrangeWithoutGenerics {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ArrayList apples = new ArrayList();
        // Not prevented from adding an Orange to apples;
        apples.add(new Orange());
        for (int i = 0; i < 3; i++) {
            apples.add(new Apple());
            ((Apple)apples.get(i)).id();
            // Orange is detected only at run time;
        }
    }
}
// 该程序在编译的时不会报错，在运行时，尝试将Orange 转换成 Apple 的时候后报异常
// 可以申明 ArrayList<Apple> 来保存 Apple 对象，这样在添加 Orange 对象时会在编译期报错，并且在取出对象的时候也不需要类型转换。
// 通过使用泛型可以在编译期就避免将错误的类型对象放置到容器中，并且在使用对象时，可以使用更加清晰的语法。