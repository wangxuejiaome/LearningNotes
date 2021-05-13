package wxj.me.javase.concurrency.demostration;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.rmi.CORBA.Tie;

import static wxj.me.javase.util.Print.print;

public class CloseResource {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost",8080).getInputStream();
        executorService.execute(new IOBlocked(socketInput));
        executorService.execute(new IOBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(100);
        print("Shutting down all threads");
        executorService.shutdownNow();
        TimeUnit.SECONDS.sleep(1);
        print("Closing " + socketInput.getClass().getSimpleName());
        socketInput.close();// Release blocked thread
        TimeUnit.SECONDS.sleep(1);
        print("Closing " + System.in.getClass().getSimpleName());
        System.in.close();// Release blocked thread
    }
}
