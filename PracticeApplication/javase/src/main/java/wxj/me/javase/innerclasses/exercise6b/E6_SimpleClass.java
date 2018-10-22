package wxj.me.javase.innerclasses.exercise6b;

import wxj.me.javase.innerclasses.exercise6.E6_Interface;

public class E6_SimpleClass {

    protected class InnerImpl implements E6_Interface{

        // Force constructor to be public:
        public InnerImpl() {}

        @Override
        public void f() {
            System.out.println("Protect Inner class implements interface");
        }
    }

}
