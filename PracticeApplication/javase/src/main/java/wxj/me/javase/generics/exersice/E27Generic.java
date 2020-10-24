package wxj.me.javase.generics.exersice;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E27Generic
 * Author: wangxuejiao
 * Date: 2019/12/18 17:04
 * Description:
 * Version: 1.0.0
 */
public class E27Generic {
    public static void main(String[] args) {
        // Compile Error: incompatible types:
        // List<Number> nlist = new ArrayList<Integer>();
        // Wildcards allow covariance:
        List<? extends Number> nlist = new ArrayList<Integer>();
        // Compile Error: can't add any type of object:
        // nlist.add(new Integer(1));
        // nlist.add(new Float(1.0));
        // nlist.add(new Object());
           nlist.add(null); // Legal but uninteresting
        // We know that it returns at least Number:
        Number n = nlist.get(0);
        System.out.println(n);
    }
}
