import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
public class ex1 {
    public static void main(String args[])throws InterruptedException{
        
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        ProducingThread p_thread = new ProducingThread(queue);
        ConsumingThread c_thread = new ConsumingThread(queue);

        new Thread(c_thread).start();
        new Thread(p_thread).start();

        Thread.sleep(5000);
    }
}

class ProducingThread implements Runnable{
    protected BlockingQueue<String> queue = null;
    public ProducingThread(BlockingQueue<String> queue){
        this.queue = queue;
    }

    public void run(){
        try{
            Thread.sleep(1000);
            queue.put("1");
            System.out.println("put 1 in queue");
            queue.put("2");
            System.out.println("put 2 in queue");
            queue.put("3");
            System.out.println("put 3 in queue");
            queue.put("4");
            System.out.println("put 4 in queue");
            queue.put("5");
            System.out.println("put 5 in queue");
            queue.put("6");
            System.out.println("put 6 in queue");
            
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class ConsumingThread implements Runnable{
    protected BlockingQueue<String> queue = null;
    public ConsumingThread(BlockingQueue<String> queue){
        this.queue = queue;
    }

    public void run(){
        try{
            System.out.println("Try to take");
            System.out.println("Take : "+queue.take());
            System.out.println("Take : "+queue.take());
            System.out.println("Take : "+queue.take());
            System.out.println("Take : "+queue.take());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}