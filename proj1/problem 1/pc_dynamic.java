package problem1;

public class pc_dynamic {
    private static int NUM_END = 200000;
    private static int JOB_SIZE = 10;

    private static int[] THREAD_NUMS = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 32 };

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {                         //인자를 정하면 해당 쓰레드 개수로 실행
            THREAD_NUMS = new int[1];
            THREAD_NUMS[0] = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }                                              // args가 없으면 사전에 정의된 THREAD_NUMS로 for문을 돌며
        for (int i = 0; i < THREAD_NUMS.length; i++) { // 1~32개의 쓰레드 개수로 한 번씩 실행
            int threadNum = THREAD_NUMS[i];

            dynamic_Thread.counter = 0;
            dynamic_Thread.current_value = -10;

            dynamic_Thread[] thread = new dynamic_Thread[threadNum];
            for (int t = 0; t < threadNum; t++) {
                thread[t] = new dynamic_Thread(t);
            }

            long startTime = System.currentTimeMillis();
            for (int t = 0; t < threadNum; t++) {
                thread[t].start();
            }
            int thread_cnt = 0;
            for (; thread_cnt < threadNum; thread_cnt++) {
                thread[thread_cnt].join();
            }
            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;

            System.out.println(threadNum + " Thread\n");

            for (int t = 0; t < threadNum; t++) {
                System.out.println("Thread " + t + " Execution Time: " + thread[t].timeDiff + "ms");
            }

            System.out.println("\nProgram Execution Time: " + timeDiff + "ms");
            System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + dynamic_Thread.counter);
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

    static class dynamic_Thread extends Thread {
        int x;
        static int counter;
        static int current_value;
        int temp = 0;
        long startTime;
        long endTime;
        long timeDiff;
        int threadNum;

        public dynamic_Thread(int threadNum) {
            this.threadNum = threadNum;
            this.x = update();
        }

        @Override
        public void run() {
            temp = 0;
            startTime = System.currentTimeMillis();
            while (this.x < NUM_END) {
                for (int i = 0; i < JOB_SIZE; i++) {
                    if (isPrime(this.x + i)) {
                        temp++;
                    }
                }
                this.x = update();
            }

            addFindNumber(temp);
            endTime = System.currentTimeMillis();
            timeDiff = endTime - startTime;
        }

        static synchronized int update() {
            current_value += JOB_SIZE;
            return current_value;
        }

        static synchronized void addFindNumber(int temp) {
            counter += temp;
        }
    }
}
