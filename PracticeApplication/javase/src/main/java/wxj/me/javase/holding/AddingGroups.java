package wxj.me.javase.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddingGroups {
    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
        Integer[] moreInts = {6,7,8,9,10};
        collection.addAll(Arrays.asList(moreInts));
        // Runs significantly faster, but you can't
        // construct a Collection this way:
        Collections.addAll(collection,11,12,13,14,15);
        Collections.addAll(collection,moreInts);
        // Produces a list "backed by" an array;
        List<Integer> list = Arrays.asList(16,17,18,19,20);
        list.set(1,99); // OK -- modify an element
        // list.add(21); // Runtime error because the underlying array cannot be resized.
        System.out.println("collection:" + collection);
        System.out.println("list:" + list);
    }
}
// 添加元素的方法：
// 1. Collection 的构造方法：接受另一个 Collection 来将自身初始化，可以使用 Arrays.asList() 来为这个构造器产生输入；
// 2. Collection 的 AddAll() 方法： 该方法只能接受另外一个 Collection 对象作为参数，不太灵活；
// 3. Collections.addAll() 方法：接受一个集合，和可变参数序列。