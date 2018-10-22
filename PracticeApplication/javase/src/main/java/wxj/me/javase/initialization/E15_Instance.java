package wxj.me.javase.initialization;

public class E15_Instance {

    private String name;
    private String nickName;

    static {
        System.out.println("静态方法域执行");
    }

    {
        name = "Jack";
        nickName = "大脸猫";
        System.out.println("实例初始化方法执行");
        System.out.println("name:" + name + ",nickName:" + nickName);
    }

    public E15_Instance(String name,String nickName){
        System.out.println("构造方法执行");
        System.out.println("name:" + name + ",nickName:" + nickName);
    }

    public static void main(String[] args) {
        new E15_Instance("wxj","小熊牛");
    }


    /**
     运行结果：
         静态方法域执行
         实例初始化方法执行
         name:Jack,nickName:大脸猫
         构造方法执行
         name:wxj,nickName:小熊牛

     note：
     - 格式如下：(格式相当于静态方法域去掉 static 关键字)
     {
         car = new Car();
         car1 = new Car();
     }
     - 初始化时机：在构造函数之前，静态初始化之后；
     - 特点：无论调用类的哪个构造函数，实例初始化都会执行；
     - 用处：对于支持 "匿名内部类" 的初始化是必须的。
     */

}
