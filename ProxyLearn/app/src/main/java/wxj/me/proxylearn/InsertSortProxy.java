package wxj.me.proxylearn;

import android.util.Log;

import wxj.me.sort.ChoiceSort;
import wxj.me.sort.ISort;
import wxj.me.sort.InsertSort;

/**
 * @author Administrator
 */
public class InsertSortProxy implements ISort {

    InsertSort mInsertSort;

    public InsertSortProxy(InsertSort insertSort) {
        mInsertSort = insertSort;
    }

    @Override
    public int[] sort(int[] ints) {
        long start = System.nanoTime();
        int [] intsSorted = mInsertSort.sort(ints);
        long end = System.nanoTime();
        System.out.println("costs time : " + (end - start));
        return intsSorted;
    }
}
