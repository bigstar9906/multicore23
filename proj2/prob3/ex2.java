import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ex2 {
    static String data = "TEXT";
    public static void main(String[] args)throws InterruptedException{
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReadingThread r_thread1 = new ReadingThread(readWriteLock);
        ReadingThread r_thread2 = new ReadingThread(readWriteLock);
        ReadingThread r_thread3 = new ReadingThread(readWriteLock);
        WritingThread w_thread1 = new WritingThread(readWriteLock);
        WritingThread2 w_thread2 = new WritingThread2(readWriteLock);
        new Thread(r_thread1).start();
        new Thread(r_thread2).start();
        new Thread(r_thread3).start();
        new Thread(w_thread1).start();
        new Thread(w_thread2).start();

        Thread.sleep(4000);

    }

}

class ReadingThread implements Runnable{
    protected ReadWriteLock readWriteLock = null;
    public ReadingThread(ReadWriteLock readWriteLock){
        this.readWriteLock = readWriteLock;
    }

    public void run(){
        try{
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"\'s Try to Read");
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+"\'s Read data : " + ex2.data);
            Thread.sleep(2000);
            readWriteLock.readLock().unlock();
            System.out.println(Thread.currentThread().getName()+"\'s ReadLock release");        
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class WritingThread implements Runnable{
    protected ReadWriteLock readWriteLock = null;
    public WritingThread(ReadWriteLock readWriteLock){
        this.readWriteLock = readWriteLock;
    }

    public void run(){
        try{
            readWriteLock.writeLock().lock();
            System.out.println("Write Lock");
            ex2.data = ex2.data.concat("s");
            Thread.sleep(2000);
            readWriteLock.writeLock().unlock();
            System.out.println("WriteLock release");
            Thread.sleep(2000);
            System.out.println("Try Write");
            readWriteLock.writeLock().lock();
            System.out.println("Write Lock");
            ex2.data = ex2.data.concat("s");
            readWriteLock.writeLock().unlock();
            System.out.println("WriteLock release");
        }catch(InterruptedException e){
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
            ex2.data = ex2.data.concat("2");
            System.out.println("Write Lock 2 release");
            readWriteLock.writeLock().unlock();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}