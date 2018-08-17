package wxj.me.proxylearn;

import wxj.me.sort.ISort;

public class SortProxy implements ISort {

    ISort sortProxy;

    public SortProxy(ISort sortProxy) {
        this.sortProxy = sortProxy;
    }

    @Override
    public int[] sort(int[] ints) {
        long start = System.nanoTime();
        int [] intsSorted = sortProxy.sort(ints);
        long end = System.nanoTime();
        System.out.println("costs time : " + (end - start));
        return intsSorted;
    }
}
