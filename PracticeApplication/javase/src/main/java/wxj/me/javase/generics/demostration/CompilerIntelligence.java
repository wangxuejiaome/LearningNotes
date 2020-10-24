package wxj.me.javase.generics.demostration;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: CompilerIntelligence
 * Author: wangxuejiao
 * Date: 2019/12/18 17:34
 * Description:
 * Version: 1.0.0
 */
public class CompilerIntelligence {

    public static void main(String[] args) {
        List<? extends Fruit> flist = Arrays.asList(new Apple());
        Apple a = (Apple) flist.get(0); // No warning
        flist.contains(new Apple()); // Argument is 'Object'
        flist.indexOf(new Apple()); // Argument is 'Object'
    }
}
