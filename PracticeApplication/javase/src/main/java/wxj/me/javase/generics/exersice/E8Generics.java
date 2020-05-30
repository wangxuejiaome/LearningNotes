package wxj.me.javase.generics.exersice;

import java.util.Iterator;
import java.util.Random;

import wxj.me.javase.util.Generator;

/**
 * Copyright (C), 2015-2019, suning
 * FileName: E8Generics
 * Author: wangxuejiao
 * Date: 2019/10/18 15:19
 * Description:
 * Version: 1.0.0
 */
public class E8Generics {

    public static void main(String[] args) {
        StoryCharactersGenerator storyCharactersGenerator = new StoryCharactersGenerator();
        for (int i = 0; i < 5; i++) {
            System.out.println(storyCharactersGenerator.next());
        }
        for (StoryCharacters storyCharacters : new StoryCharactersGenerator(5))
            System.out.println(storyCharacters);
    }
}


class StoryCharacters {
    private static long counter = 0;
    private final long id = counter++;

    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}

class GoodGuys extends StoryCharacters {
}

class BadGuys extends StoryCharacters {
}

class StoryCharactersGenerator implements Generator<StoryCharacters>, Iterable<StoryCharacters> {

    private Class[] types = {GoodGuys.class, BadGuys.class};
    private static Random rand = new Random(47);

    public StoryCharactersGenerator() {
    }

    // For iteration:
    private int size = 0;

    public StoryCharactersGenerator(int size) {
        this.size = size;
    }

    @Override
    public StoryCharacters next() {
        try {
            return (StoryCharacters) types[rand.nextInt(types.length)].newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class StoryCharactersIterator implements Iterator<StoryCharacters> {
        int count = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public StoryCharacters next() {
            count--;
            return StoryCharactersGenerator.this.next();
        }
    }

    @Override
    public Iterator<StoryCharacters> iterator() {
        return new StoryCharactersIterator();
    }
}