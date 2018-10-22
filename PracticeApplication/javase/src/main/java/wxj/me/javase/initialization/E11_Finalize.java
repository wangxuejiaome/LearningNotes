package wxj.me.javase.initialization;

/**
 *  在 E10_finalize.java 基础上进行修改，让 finalize() 方法总会被调用。
 */
public class E11_Finalize {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("call finalize()");
    }

    public static void main(String[] args) throws Throwable {
        new E10_Finalize();
        System.gc();
        System.runFinalization();
    }
}

/*
    Calling System.gc( ) and System.runFinalization( ) in sequence will
        probably but not necessarily call your finalizer (The behavior of finalize has been
        uncertain from one version of JDK to another.) The call to these methods is just
        a request; it doesn’t ensure the finalizer will actually run. Ultimately, nothing
        guarantees that finalize( ) will be called*/
