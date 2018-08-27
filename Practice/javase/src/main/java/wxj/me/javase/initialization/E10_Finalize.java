package wxj.me.javase.initialization;

/**
 * 编写具有 finalize方法的类，并在方法中打印消息。在 main() 中为该类创建一个对象。是解释这个程序的行为。
 */
public class E10_Finalize {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("call finalize()");
    }

    public static void main(String[] args) throws Throwable {
        new E10_Finalize();
    }
}

// 运行程序没有看到 finalize() 被执行，因为内存很充足，垃圾回收期没有被触发没有调用 finalize() 方法。