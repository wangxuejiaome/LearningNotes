package wxj.me.javase.concurrency.exercise;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static wxj.me.javase.util.Print.print;

/**
 * Create by 18113881 on 2019/5/22 10 : 24
 */


abstract class DelayTask implements Delayed, Runnable {

    private long delay;
    private long trigger;

    public DelayTask(long delay) {
        this.delay = delay;
        trigger = System.nanoTime() + NANOSECONDS.convert(delay, MILLISECONDS);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayTask delayTask = (DelayTask) o;
        return Long.compare(this.delay, delayTask.delay);
    }

    @Override
    public void run() {
        action();
        System.out.println(this + " ");
    }

    abstract void action();

}

class GreenHouse {

    private volatile boolean light = false;
    private volatile boolean water = false;

    public String getThermostat() {
        return thermostat;
    }

    public void setThermostat(String thermostat) {
        this.thermostat = thermostat;
    }

    private volatile String thermostat = "Day";

    class BellingTask extends DelayTask {

        public BellingTask(long delay) {
            super(delay);
        }

        @Override
        void action() {

        }

        public String toString() {
            return "Bing!";
        }
    }

    class LightOnTask extends DelayTask {

        public LightOnTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            light = true;
        }

        public String toString() {
            return "Light on is " + light;
        }
    }

    class LightOffTask extends DelayTask {

        public LightOffTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            light = false;
        }

        public String toString() {
            return "Light on is " + light;
        }
    }

    class WaterOnTask extends DelayTask {

        public WaterOnTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            water = true;
        }

        public String toString() {
            return "Greenhouse water on is " + water;
        }

    }

    class WaterOffTask extends DelayTask {

        public WaterOffTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            water = false;
        }

        public String toString() {
            return "Greenhouse water on is " + water;
        }
    }

    class ThermostatNightTask extends DelayTask {

        public ThermostatNightTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            setThermostat("Night");
        }

        @Override
        public String toString() {
            return "Thermostat on " + getThermostat() + " setting";
        }
    }

    class ThermostatDayTask extends DelayTask {

        public ThermostatDayTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            setThermostat("Day");
        }

        @Override
        public String toString() {
            return "Thermostat on " + getThermostat() + " setting";
        }
    }

    class TerminateTask extends DelayTask {


        public TerminateTask(long delay) {
            super(delay);
        }

        @Override
        void action() {
            System.exit(0);
        }

        public String toString() {
            return "Terminating";
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayTask> delayQueue;

    public DelayedTaskConsumer(DelayQueue<DelayTask> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Run task with the current thread
                delayQueue.take().run();
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        print("Finished DelayedTaskConsumer");
    }
}


public class E33GreenHouseDelayQueue {


    public static void main(String[] args) {
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        GreenHouse greenHouse = new GreenHouse();
        delayQueue.add(greenHouse.new BellingTask(900));
        delayQueue.add(greenHouse.new ThermostatNightTask(0));
        delayQueue.add(greenHouse.new LightOnTask(200));
        delayQueue.add(greenHouse.new LightOffTask(400));
        delayQueue.add(greenHouse.new WaterOnTask(600));
        delayQueue.add(greenHouse.new WaterOffTask(800));
        delayQueue.add(greenHouse.new ThermostatDayTask(1400));
        delayQueue.add(greenHouse.new TerminateTask(5000));
      /*  GreenHouse greenHouse = new GreenHouse();
        delayQueue.offer(greenHouse.new BellingTask(900), 900, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new ThermostatNightTask(0), 0, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new LightOnTask(200), 200, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new LightOffTask(400), 400, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new WaterOnTask(600), 600, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new WaterOffTask(800), 800, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new ThermostatDayTask(1400), 1400, TimeUnit.MILLISECONDS);
        delayQueue.offer(greenHouse.new TerminateTask(5000), 5000, TimeUnit.MILLISECONDS);*/

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new DelayedTaskConsumer(delayQueue));
    }

}
