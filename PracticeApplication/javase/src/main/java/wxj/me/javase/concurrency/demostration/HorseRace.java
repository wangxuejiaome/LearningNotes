package wxj.me.javase.concurrency.demostration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

/**
 * Create by 18113881 on 2019/4/30 14 : 58
 */

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random random = new Random(47);
    private static CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier barrier) {
        cyclicBarrier = barrier;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {
            // A legitimate way to exit
        } catch (BrokenBarrierException e) {
            // This one we want to know about
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}

public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier cyclicBarrier;

    public HorseRace(int nHorses, final int pause) {
        cyclicBarrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    stringBuilder.append("=");
                }
                print(stringBuilder);
                for (Horse horse: horses) {
                    print(horse.tracks());
                }
                for (Horse horse : horses) {
                    if(horse.getStrides() >= FINISH_LINE) {
                        print(horse + "Won");
                        executorService.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    print("barrier-action sleep interrupted");
                }
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(cyclicBarrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 1000;
        new HorseRace(nHorses,pause);
    }
}
