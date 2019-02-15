package wxj.me.javase.concurrency.exercise;

class PrintTask implements Runnable {

    public boolean suspend = false;

    int count;

    @Override
    public void run() {
        while (true) {
            if(!suspend){
                System.out.println("打印" + count);
                count ++ ;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(count > 5){
                suspend = true;
            }

        }
    }
}

public class IntervalTask {

    public static void main(String[] args) {
        Thread thread = new Thread(new PrintTask());
        thread.start();
    }
}
