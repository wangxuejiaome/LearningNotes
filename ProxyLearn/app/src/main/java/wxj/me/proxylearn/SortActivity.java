package wxj.me.proxylearn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import wxj.me.sort.BubbleSort;
import wxj.me.sort.ChoiceSort;
import wxj.me.sort.InsertSort;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compareCostTime();
    }

    private void compareCostTime() {
        int[] unOrderedInts = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        System.out.print("未排序数组顺序为：");
        display(unOrderedInts);

        /*int[] unOrderedInts1 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        BubbleSortProxy bubbleSortProxy = new BubbleSortProxy(new BubbleSort());
        System.out.print("BubbleSort ");
        bubbleSortProxy.sort(unOrderedInts1);

        int[] unOrderedInts2 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        ChoiceSortProxy choiceSortProxy = new ChoiceSortProxy(new ChoiceSort());
        System.out.print("ChoiceSort ");
        choiceSortProxy.sort(unOrderedInts2);

        int[] unOrderedInts3 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        InsertSortProxy insertSortProxy = new InsertSortProxy(new InsertSort());
        System.out.print("InsertSort ");
        insertSortProxy.sort(unOrderedInts3);*/

        int[] unOrderedInts1 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        SortProxy sortProxy = new SortProxy(new BubbleSort());
        System.out.print("BubbleSort ");
        sortProxy.sort(unOrderedInts1);

        int[] unOrderedInts2 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        SortProxy sortProxy2 = new SortProxy(new ChoiceSort());
        System.out.print("ChoiceSort ");
        sortProxy2.sort(unOrderedInts2);

        int[] unOrderedInts3 = new int[]{4, 2, 8, 9, 5, 7, 6, 1, 3};
        SortProxy sortProxy3 = new SortProxy(new InsertSort());
        System.out.print("InsertSort ");
        sortProxy3.sort(unOrderedInts3);

    }

    public static void display(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
