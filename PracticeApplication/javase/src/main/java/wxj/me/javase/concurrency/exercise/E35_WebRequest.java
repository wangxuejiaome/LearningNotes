package wxj.me.javase.concurrency.exercise;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by 18113881 on 2019/5/28 19 : 23
 * 需求：向有固定数量的服务器发送请求，目标是要确定这个服务器组可以处理的负载大小。
 *
 * 分析：
 * 负载大小的评判：在给定的时间内，看这组服务器共处理了多少请求。
 * 模拟场景：
 * 一个任务每隔 2 秒产生一个请求，请求的时长不确定，并把这些请求放入到队列中；
 * 服务器组从这个队列中取任务；
 * 统计每个服务器处理的任务个数，它们的总和就是它们这段时间的负载大小
 *
 * 服务器：
 * 需要记录自己处理的请求数；
 */


class Request {

    private int taskTime;

    public Request(int taskTime) {
        this.taskTime = taskTime;
    }

    public int getTaskTime(){
        return taskTime;
    }

    @Override
    public String toString() {
        return "[ " + taskTime + "] ";
    }
}

class RequestQueue extends ArrayBlockingQueue<Request> {

    public RequestQueue(int capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Request request : this) {
            result.append(result);
        }
        return result.toString();
    }
}

class GeneratorRequest implements Runnable {
    public BlockingQueue<Request> requests;

    private static Random random = new Random(47);

    public GeneratorRequest(BlockingQueue blockingQueue) {
        this.requests = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                requests.add(new Request(random.nextInt(3)));
                TimeUnit.MILLISECONDS.sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println(this + "Interrupted!");
        }
        System.out.println(this + "terminate");
    }
}

class Server implements Runnable {
    private static int counter;
    private final int id = counter++;
    private BlockingQueue<Request> requests;
    private int serveredCounter;


    public Server(BlockingQueue<Request> requests) {
        this.requests = requests;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Request request = requests.take();
                TimeUnit.MILLISECONDS.sleep(request.getTaskTime());
                requests.take();
                serveredCounter++;
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted!");
        }
    }

    public int getServedCount() {
        return serveredCounter;
    }

    @Override
    public String toString() {
        return "server " + id + " server:" + serveredCounter;
    }
}

public class E35_WebRequest {

    public static void main(String[] args) throws IOException {
        List<Server> servers = new ArrayList<>();
        final int MAX_QUEUE_SIZE = 50;
        BlockingQueue<Request> requests = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        GeneratorRequest generatorRequest = new GeneratorRequest(requests);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(generatorRequest);
        for (int i = 0; i < 4; i++) {
            Server server = new Server(requests);
            servers.add(server);
            executorService.execute(server);
        }

        System.in.read();
        int serveredTotalCounter = 0;
        for (Server server : servers) {
            serveredTotalCounter += server.getServedCount();
            System.out.println(server);
        }
        System.out.println("servers total server " + serveredTotalCounter);
    }
}

