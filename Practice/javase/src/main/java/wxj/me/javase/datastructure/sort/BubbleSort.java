package wxj.me.javase.datastructure.sort;

public class BubbleSort {


    public static int[] bubbleSort(int[] arr){
        if(arr == null || arr.length < 2){
            throw new RuntimeException("数组的长度必须大于1");
        }
        for (int i = 1; i < arr.length; i++) {
            boolean exchangeFlag = false;
            for (int j = arr.length -1; j > i-1; j--) {
                int temp = arr[j];
                if(arr[j] > arr[j-1]){
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    exchangeFlag = true;
                }
            }
            if(!exchangeFlag){
                break;
            }
            System.out.print("第" + i + "轮排序结果：" );
            display(arr);
        }
        return arr;
    }

    public static void display(int[] arr){
        for (int i = 0; i < arr.length ; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {4,2,8,9,5,7,6,1,3};
        System.out.print("排序前数组为：");
        BubbleSort.display(array);
        System.out.println("---------排序中---------");
        BubbleSort.bubbleSort(array);
        System.out.println("---------排序结束---------");
    }
}
