package wxj.me.javase.concurrency.exercise;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/19 19 : 48
 */

class Ticket {
    public int counter = 100;

    public synchronized void decreaseCounter(SellTicketTask sellTicketTask,int decreaseCounter) {
        if(counter >= decreaseCounter) {
            counter = counter - decreaseCounter;
            sellTicketTask.sellTotalCounter += decreaseCounter;
        }
    }

    public synchronized int getCounter() {
        return counter;
    }
}

class SellTicketTask implements Runnable {

    public static int counter = 0;
    public final int id = counter++;
    public int sellTotalCounter;

    Ticket ticket;
    Random random = new Random(47);

    public SellTicketTask(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true){
            int randomCounter = random.nextInt(3);
            ticket.decreaseCounter(this,random.nextInt(3));
            System.out.println("sell entrance id: " + id + " ,sell counter: " + randomCounter + " ,sell total counter: " + sellTotalCounter);
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TicketDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        System.out.println("Original total ticker: " + ticket.counter);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SellTicketTask(ticket));
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("left Ticket:" + ticket.getCounter());
        System.exit(0);
    }
}
