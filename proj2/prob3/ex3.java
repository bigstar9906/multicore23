import java.util.concurrent.atomic.AtomicInteger;
public class ex3 {
    public static void main(String[] args)throws InterruptedException{
        AtomicInteger atom = new AtomicInteger(100);
        for(int i=0;i<10;i++){
            Thread t = new Thread(new AtomThread(atom));
            t.start();
        }
        
        Thread.sleep(1500);
    }

}

class AtomThread implements Runnable{
    protected AtomicInteger atom = null;
    public AtomThread(AtomicInteger atom){
        this.atom = atom;
    }

    public void run(){
        try{
        String name = Thread.currentThread().getName();
        System.out.println(name+" get "+atom.get());
        Thread.sleep(400);
        int middle = 150;
        atom.compareAndSet(middle, 200);
        System.out.println(name+" get "+atom.getAndAdd(10)+" and add 10");
        
        Thread.sleep(400);
        System.out.println(name+" add 10 and get "+atom.addAndGet(10));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

