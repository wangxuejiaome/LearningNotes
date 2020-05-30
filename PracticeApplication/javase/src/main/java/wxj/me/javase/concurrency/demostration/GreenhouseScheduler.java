package wxj.me.javase.concurrency.demostration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/5/13 10 : 23
 */
public class GreenhouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day";

    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String value) {
        thermostat = value;
    }

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);

    public void schedule(Runnable event, long delay) {
        scheduledThreadPoolExecutor.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    class LightOn implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here to
            // physically turn on the light.
            System.out.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here to
            // physically turn off the light.
            System.out.println("Turing off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here.
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }

    class WaterOff implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here.
            System.out.println("Turing greenhouse water on");
            water = true;
        }
    }

    class ThermostatNight implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            // Put hardware control code here.
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        @Override
        public void run() {
            System.out.println("Bing!");
        }
    }

    class Terminate implements Runnable {
        @Override
        public void run() {
            System.out.println("Terminating");
            scheduledThreadPoolExecutor.shutdownNow();
            // Must start a separate task to do this job.
            // since the scheduler has been shut down:
            new Thread() {
                @Override
                public void run() {
                    for (DataPoint d : data) {
                        System.out.println(d);
                    }
                }
            }.start();
        }
    }

    // New feature: data collection
    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity;

        public DataPoint(Calendar time, float temperature, float humidity) {
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        public String toString() {
            return time.getTime() + String.format(" temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }

    private Calendar lastTime = Calendar.getInstance();

    {
        // Adjust date to the half hour
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 60);
    }

    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random random = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());

    class CollectData implements Runnable {
        @Override
        public void run() {
            System.out.println("Collecting data");
            synchronized (GreenhouseScheduler.this) {
                // Pretend the interval is longer than it is:
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                // One in 5 chances of reversing the direction:
                if (random.nextInt(5) == 4) {
                    tempDirection = -tempDirection;
                }
                // Store previous value:
                lastTemp = lastTemp + tempDirection * (1.0f + random.nextFloat());
                if (random.nextInt(5) == 4) {
                    humidityDirection = -humidityDirection;
                }
                lastHumidity = lastHumidity + humidityDirection * random.nextFloat();
                // Calendar must be cloned, otherwise all
                // DataPoints hold references to the same lastTime.
                // For a basic object like Calendar, clone() is OK.
                data.add(new DataPoint((Calendar) lastTime.clone(), lastTemp, lastHumidity));
            }
        }
    }


    public static void main(String[] args) {
        GreenhouseScheduler greenhouseScheduler = new GreenhouseScheduler();
        greenhouseScheduler.schedule(greenhouseScheduler.new Terminate(), 5000);
        // Former "Restart" class not necessary:
        greenhouseScheduler.repeat(greenhouseScheduler.new Bell(), 0, 1000);
        greenhouseScheduler.repeat(greenhouseScheduler.new ThermostatNight(), 0, 2000);
        greenhouseScheduler.repeat(greenhouseScheduler.new LightOn(), 0, 200);
        greenhouseScheduler.repeat(greenhouseScheduler.new LightOff(), 0, 400);
        greenhouseScheduler.repeat(greenhouseScheduler.new WaterOn(), 0, 600);
        greenhouseScheduler.repeat(greenhouseScheduler.new WaterOff(), 0, 800);
        greenhouseScheduler.repeat(greenhouseScheduler.new ThermostatDay(), 0, 1400);
        greenhouseScheduler.repeat(greenhouseScheduler.new CollectData(), 500, 500);
    }
}
