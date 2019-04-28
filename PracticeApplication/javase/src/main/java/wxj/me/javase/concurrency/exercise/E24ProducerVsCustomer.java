package wxj.me.javase.concurrency.exercise;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static wxj.me.javase.util.Print.print;
import static wxj.me.javase.util.Print.printnb;

/**
 * Create by 18113881 on 2019/4/24 08 : 51
 */

class Meal {
    public static int count;
    public final int id = count++;

    @Override
    public String toString() {
        return "Meal id: " + id;
    }
}

class Restaurant {

    Chef chef;
    WaitPerson waitPerson;

    public final int SIZE = 10;
    List<Meal> buffer = new ArrayList<>();
    ExecutorService executorService = Executors.newCachedThreadPool();


    public Restaurant() {
        chef = new Chef(this);
        waitPerson = new WaitPerson(this);
        start();
    }

    public boolean isFull() {
        return buffer.size() >= SIZE;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }


    public void start() {
        executorService.execute(chef);
        executorService.execute(waitPerson);
    }
}

class Chef implements Runnable {

    Restaurant restaurant;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.isFull()) {
                        wait();
                    }
                    if (Meal.count >= 20) {
                        print("Sale out");
                        restaurant.executorService.shutdownNow();
                    } else {
                        Meal meal = new Meal();
                        restaurant.buffer.add(meal);
                        print("Make " + meal + " ,buffer size: " + restaurant.buffer.size());

                        synchronized (restaurant.waitPerson) {
                            restaurant.waitPerson.notifyAll();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            printnb("Chef interrupted");
        }
    }
}

class WaitPerson implements Runnable {

    Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.isEmpty()) {
                        wait();
                    }
                    print("waitPerson get a " + restaurant.buffer.remove(0));

                    synchronized (restaurant.chef) {
                        restaurant.chef.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson Interrupted");
        }
    }

}

public class E24ProducerVsCustomer {

    public static void main(String[] args) {
        new Restaurant();
    }
}
