package wxj.me.javase.generics.exersice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E18Generic
 * Author: wangxuejiao
 * Date: 2019/10/25 9:44
 * Description:
 * Version: 1.0.0
 */

class BigFish {
    private static long counter = 1;
    private final long id = counter++;
    public BigFish() {}

    @Override
    public String toString() {
        return "BigFish " + id;
    }
}

class LittleFish {
    private static long counter = 1;
    private final long id = counter++;
    public LittleFish() {}

    @Override
    public String toString() {
        return "LittleFish " + id;
    }
}

public class E18Generic {

    public static void main(String[] args) {

        List<BigFish> bigFishList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bigFishList.add(new BigFish());
        }

        List<LittleFish> littleFishList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            littleFishList.add(new LittleFish());
        }

        Random random = new Random(47);
        for (int i = 0; i < littleFishList.size(); i++) {
            System.out.println(bigFishList.get(random.nextInt(bigFishList.size())) + " eat" + littleFishList.get(i));
        }
    }
}
