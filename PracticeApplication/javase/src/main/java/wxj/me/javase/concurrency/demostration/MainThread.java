package wxj.me.javase.concurrency.demostration;

public class MainThread {

    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }
}
