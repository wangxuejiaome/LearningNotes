package wxj.me.proxylearn;

import android.util.Log;

import wxj.me.sort.BubbleSort;
import wxj.me.sort.ISort;

public class BubbleSortProxy implements ISort {

    BubbleSort mBubbleSort;

    public BubbleSortProxy(BubbleSort bubbleSort) {
        mBubbleSort = bubbleSort;
    }

    @Override
    public int[] sort(int[] ints) {
        long start = System.nanoTime();
        int[] intsSorted = mBubbleSort.sort(ints);
        long end = System.nanoTime();
        System.out.println("BubbleSort costs time : " + (end - start));
        return intsSorted;
    }
}
