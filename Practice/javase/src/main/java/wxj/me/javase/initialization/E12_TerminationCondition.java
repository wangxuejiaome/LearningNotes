package wxj.me.javase.initialization;

/**
 * 编写一个名为 Tank 的类，此类的状态可以是“满的”或“空的”。
 * 其终结条件是：对象清理时必须处于空状态。
 * 请编写 finalize() 以检测终止条件是否成立，在 main 中测试 Tank 可能发生的几种使用方式。
 */

class Tank{

    private final int id;
    private static int count;
    private boolean isEmpty;

    public Tank(boolean isEmpty){
        this.isEmpty = isEmpty;
        id = count ++;
    }

    public void setEmpty(){
        isEmpty = true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(!isEmpty){
            System.out.println("error:the tank" + id +  "is not empty");
        }else {
            System.out.println("tank" + id + "has been collected");
        }
    }
}

public class E12_TerminationCondition {

    public static void main(String[] args) {

        Tank tank = new Tank(false);
        new Tank(false);
        new Tank(true);
        Tank tank3 = new Tank(false);
        tank3.setEmpty();
        System.gc();
    }

}

// 运行结果会出现如下几种情况：

/*  情况1：
    tank2has been collected
    error:the tank1is not empty

    情况2：
    error:the tank1is not empty

    情况3:
    tank2has been collected

    情况4：

    */

/**
 * 分析：从上面运行的结果可以看出：
 * 1. 如果对象被指向一个引用变量，垃圾回收器不会回收这两个对象，比如 tank 和 tank3 并未被回收
 * 2. tank1 和 tank2 未被引用的对象，可能都被回收，也肯能都没有被回收，也可能只回收了其中一个。
 * 垃圾回收器在运行过程中并不能保证每次都被触发，则 finalize() 则不能保证每次都会执行（比如情况4）；
 * 垃圾回收器在回收的时候，可能不会回收全部垃圾，有的时候只会回收部分（比如情况2和3）；
 *
 * 结论：我们并不能指望 finalize() 方法一定会被调用，但是我们可以利用 finalize() 来判读对象是否真确的被清理。
 */
