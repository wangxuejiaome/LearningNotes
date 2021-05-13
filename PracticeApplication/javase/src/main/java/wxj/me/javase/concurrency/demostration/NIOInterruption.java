package wxj.me.javase.concurrency.demostration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;

class NIOBlocked implements Runnable {

    private final SocketChannel socketChannel;

    public NIOBlocked(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            print("Waiting for read() in " + this);
            socketChannel.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e) {
            print("ClosedByInterruptException e");
        } catch (AsynchronousCloseException e) {
            print("AsynchronousCloseException");
        } catch (IOException e) {
            e.printStackTrace();
        }
        print("Exiting NIOBlocked.run() " + this);
    }
}

public class NIOInterruption {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8080);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost",8080);
        SocketChannel socketChannel1 = SocketChannel.open(inetSocketAddress);
        SocketChannel socketChannel2 = SocketChannel.open(inetSocketAddress);
        Future<?> future = executorService.submit(new NIOBlocked(socketChannel1));
        executorService.execute(new NIOBlocked(socketChannel2));
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(1);
        // Produce an interrupt via cancel:
        future.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        socketChannel2.close();
    }

}
