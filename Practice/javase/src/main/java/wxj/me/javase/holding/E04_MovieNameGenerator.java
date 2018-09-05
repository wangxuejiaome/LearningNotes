package wxj.me.javase.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

import wxj.me.javase.util.Generator;

/****************** Exercise 4 ******************
 * Create a generator class that produces String objects
 * with the names of characters from your favorite
 * movie each time you call next(), and then loops
 * around to the beginning of the character list
 * when it runs out of names. Use this generator to
 * fill an array, an ArrayList, a LinkedList, a
 * HashSet, a LinkedHashSet, and a TreeSet, then
 * print each container.
 ***********************************************/
class MovieNameGenerator implements Generator<String> {

    String[] characters = {
            "Grumpy", "Happy", "Sleepy", "Dopey", "Doc", "Sneezy",
            "Bashful", "Snow White", "Witch Queen", "Prince"
    };

    int next;

    @Override
    public String next() {
        String r = characters[next];
        next = (next + 1) % characters.length;
        return r;
    }
}

public class E04_MovieNameGenerator {

    static MovieNameGenerator movieNameGenerator = new MovieNameGenerator();

    public static String[] generatorMovieArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = movieNameGenerator.next();
        }
        return array;
    }

    public static Collection<String> generatorMovieList(Collection<String> collection) {
        for (int i = 0; i < 5; i++) {
            collection.add(movieNameGenerator.next());
        }
        return collection;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(generatorMovieArray(new String[5])));
        System.out.println(generatorMovieList(new ArrayList<String>()));
        System.out.println(generatorMovieList(new LinkedList<String>()));
        System.out.println(generatorMovieList(new HashSet<String>()));
        System.out.println(generatorMovieList(new LinkedHashSet<String>()));
        System.out.println(generatorMovieList(new TreeSet<String>()));
    }
}
