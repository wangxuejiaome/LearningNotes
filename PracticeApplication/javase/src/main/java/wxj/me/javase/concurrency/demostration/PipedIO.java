package wxj.me.javase.concurrency.demostration;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static wxj.me.javase.util.Print.print;
import static wxj.me.javase.util.Print.printnb;

/**
 * Create by 18113881 on 2019/4/28 09 : 56
 */

class Sender implements Runnable {
    private Random random = new Random(47);
    private PipedWriter pipedWriter = new PipedWriter();

    public PipedWriter getPipedWriter() {
        return pipedWriter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for(char c = 'A'; c <= 'z'; c++) {
                    pipedWriter.write(c);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                }
            }
        } catch (IOException e) {
            print(e + " Sender write exception");
        } catch (InterruptedException e) {
            print(e + "Sender Sleep interrupted");
        }
    }
}

class Receiver implements Runnable {
    private PipedReader pipedReader;

    public Receiver(Sender sender) throws IOException {
        pipedReader = new PipedReader(sender.getPipedWriter());
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Blocks until characters are there:
                printnb("Read: " + (char) pipedReader.read() + ". ");
            }
        } catch (IOException e) {
            print(e + " Receiver read exception");
        }
    }
}
public class PipedIO {
    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        executorService.shutdownNow();
    }
}
