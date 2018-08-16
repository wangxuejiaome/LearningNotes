package wxj.me.proxylearn;

import android.util.Log;

import wxj.me.sort.BubbleSort;
import wxj.me.sort.ChoiceSort;
import wxj.me.sort.ISort;

public class ChoiceSortProxy implements ISort {

    ChoiceSort mChoiceSort;

    public ChoiceSortProxy(ChoiceSort choiceSort) {
        mChoiceSort = choiceSort;
    }

    @Override
    public int[] sort(int[] ints) {
        long start = System.nanoTime();
        int [] intsSorted = mChoiceSort.sort(ints);
        long end = System.nanoTime();
        System.out.println("ChoiceSort costs time : " + (end - start));
        return intsSorted;
    }
}
