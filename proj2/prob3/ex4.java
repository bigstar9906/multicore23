import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class ex4 {
    static String data = "TEXT";
    public static void main(String[] args){

        Runnable readLockBarrierAction = new Runnable() {
            public void run(){
                System.out.println("All Thread reached ReadLockBarrier");
            }
        };
        Runnable readEndBarrierAction = new Runnable() {
            public void run(){
                System.out.println("All Thread reached End");
            }
        };

        CyclicBarrier readLockBarrier = new CyclicBarrier(4, readLockBarrierAction);
        CyclicBarrier readEndBarrier = new CyclicBarrier(3, readEndBarrierAction);

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReadingThread r_thread1 = new ReadingThread(readWriteLock,readLockBarrier,readEndBarrier);
        ReadingThread r_thread2 = new ReadingThread(readWriteLock,readLockBarrier,readEndBarrier);
        ReadingThread r_thread3 = new ReadingThread(readWriteLock,readLockBarrier,readEndBarrier);
        WritingThread w_thread1 = new WritingThread(readWriteLock,readLockBarrier);
        WritingThread2 w_thread2 = new WritingThread2(readWriteLock);
        new Thread(r_thread1).start();
        new Thread(r_thread2).start();
        new Thread(r_thread3).start();
        new Thread(w_thread1).start();
        new Thread(w_thread2).start();
        
    }

}


class ReadingThread implements Runnable{
    protected ReadWriteLock readWriteLock = null;
    protected CyclicBarrier readLockBarrier = null;
    protected CyclicBarrier readEndBarrier = null;
    public ReadingThread(ReadWriteLock readWriteLock,CyclicBarrier readLockBarrier, CyclicBarrier readEndBarrier){
        this.readWriteLock = readWriteLock;
        this.readLockBarrier = readLockBarrier;
        this.readEndBarrier = readEndBarrier;
    }

    public void run(){
        try{
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"\'s Try to Read");
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+"\'s Read data : " + ex4.data);
            this.readLockBarrier.await();
            System.out.println(Thread.currentThread().getName()+"\'s ReadLock release");
            readWriteLock.readLock().unlock();
            this.readEndBarrier.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(BrokenBarrierException e){
            e.printStackTrace();
        }
    }
}

class WritingThread implements Runnable{
    protected ReadWriteLock readWriteLock = null;
    protected CyclicBarrier readLockBarrier = null;
    public WritingThread(ReadWriteLock readWriteLock,CyclicBarrier readLockBarrier){
        this.readWriteLock = readWriteLock;
        this.readLockBarrier = readLockBarrier;
    }

    public void run(){
        try{
            readWriteLock.writeLock().lock();
            System.out.println("Write Lock");
            Thread.sleep(2000);
            ex4.data = ex4.data.concat("s");
            readWriteLock.writeLock().unlock();
            System.out.println("WriteLock release");
            readLockBarrier.await();
            System.out.println("Try Write");
            readWriteLock.writeLock().lock();
            System.out.println("Write Lock");
            ex4.data = ex4.data.concat("s");
            readWriteLock.writeLock().unlock();
            System.out.println("WriteLock release");
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(BrokenBarrierException e){
            e.printStackTrace();
        }

    }
}
class WritingThread2 implements Runnable{
    protected ReadWriteLock readWriteLock = null;
    public WritingThread2(ReadWriteLock readWriteLock){
        this.readWriteLock = readWriteLock;
    }

    public void run(){
        try{
            Thread.sleep(500);
            System.out.println("Try write Lock 2");
            readWriteLock.writeLock().lock();
            System.out.println("Write Lock 2");
            ex4.data = ex4.data.concat("2");
            System.out.println("Write Lock 2 release");
            readWriteLock.writeLock().unlock();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}