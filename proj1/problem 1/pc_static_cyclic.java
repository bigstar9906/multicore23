package problem1;

public class pc_static_cyclic {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 32;
    private static int JOB_SIZE = 10;

    private static int[] THREAD_NUMS = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 32 };

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) { // 인자를 정하면 해당 쓰레드 개수로 실행
            THREAD_NUMS = new int[1];
            THREAD_NUMS[0] = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }                                              // args가 없으면 사전에 정의된 THREAD_NUMS로 for문을 돌며
        for (int i = 0; i < THREAD_NUMS.length; i++) { // 1~32개의 쓰레드 개수로 한 번씩 실행
            NUM_THREADS = THREAD_NUMS[i];

            static_cyclic_Thread.counter = 0;

            static_cyclic_Thread[] thread = new static_cyclic_Thread[NUM_THREADS];
            for (int t = 0; t < NUM_THREADS; t++) {
                thread[t] = new static_cyclic_Thread(t);
            }

            long startTime = System.currentTimeMillis();
            for (int t = 0; t < NUM_THREADS; t++) {
                thread[t].start();
            }
            for (int t = 0; t < NUM_THREADS; t++) {
                thread[t].join();
            }

            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;

            System.out.println(NUM_THREADS + " Thread\n");

            for (int t = 0; t < NUM_THREADS; t++) {
                System.out.println("Thread " + t + " Execution Time: " + thread[t].timeDiff + "ms");
            }

            System.out.println("\nProgram Execution Time: " + timeDiff + "ms");
            System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + static_cyclic_Thread.counter);
            System.out.println("********************************");
        }
    }

    private static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }

        for (int i = 2; i < x; i++) {
            if (x % i == 0)
                return false;
        }

        return true;
    }

    static class static_cyclic_Thread extends Thread {
        int startPoint;
        static int counter;
        int temp = 0;
        long startTime;
        long endTime;
        long timeDiff;

        public static_cyclic_Thread(int threadNum) {
            this.startPoint = threadNum * JOB_SIZE;
        }

        @Override
        public void run() {
            startTime = System.currentTimeMillis();

            for (int i = startPoint; i < NUM_END; i = i + NUM_THREADS * JOB_SIZE) {
                for (int j = 0; j < JOB_SIZE; j++) {
                    if (isPrime(i + j))
                        temp++;
                }
            }

            counter += temp;
            endTime = System.currentTimeMillis();
            timeDiff = endTime - startTime;
        }
    }
}
