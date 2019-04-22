package wxj.me.javase.concurrency.exercise;

import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/4/22 16 : 34
 */

class Switch {
    public boolean switchStatus;

    public synchronized void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    public synchronized boolean getSwitchStatus() {
        return switchStatus;
    }
}

class TaskOpenSwitch implements Runnable {

    Switch aSwitch;

    public TaskOpenSwitch(Switch aSwitch) {
        System.out.println("TaskOpenSwitch constructor");
        this.aSwitch = aSwitch;
    }

    @Override
    public void run() {
        try {
            System.out.println("TaskOpenSwitch will sleep 3 seconds");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("TaskOpenSwitch set the switch to true");
            aSwitch.setSwitchStatus(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TaskOffSwith implements Runnable {

    Switch aSwitch;

    public TaskOffSwith(Switch aSwitch) {
        this.aSwitch = aSwitch;
        System.out.println("TaskOffSwitch constructor");
    }

    @Override
    public void run() {
        System.out.println("TaskOffSwitch run");
        while (true){
            if(aSwitch.getSwitchStatus()) {
                aSwitch.setSwitchStatus(false);
                System.out.println("TaskOffSwitch change the switch to false");
                break;
            }
        }
        System.out.println("TaskOffSwitch end");
    }
}

public class E22Wait {
    public static void main(String[] args) {
        Switch aSwitch = new Switch();
        long startMillis = System.currentTimeMillis();
        System.out.println("Main start: " + startMillis);
        TaskOpenSwitch taskOpenSwitch = new TaskOpenSwitch(aSwitch);
        taskOpenSwitch.run();
        TaskOffSwith taskOffSwith = new TaskOffSwith(aSwitch);
        taskOffSwith.run();
        long endMillis = System.currentTimeMillis();
        System.out.println("Main end: " + endMillis + ", custom: " + (endMillis - startMillis) / 1000 + " seconds");
    }
}
