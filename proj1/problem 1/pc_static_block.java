package problem1;

public class pc_static_block {
    private static int NUM_END = 200000;
    private static int[] THREAD_NUMS = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 32 };

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {                        // 인자를 정하면 해당 쓰레드 개수로 실행
            THREAD_NUMS = new int[1];
            THREAD_NUMS[0] = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }                                              // args가 없으면 사전에 정의된 THREAD_NUMS로 for문을 돌며
        for (int i = 0; i < THREAD_NUMS.length; i++) { // 1~32개의 쓰레드 개수로 한 번씩 실행
            int threadNum = THREAD_NUMS[i];
            static_block_Thread.counter = 0;

            static_block_Thread[] thread = new static_block_Thread[threadNum];
            for (int t = 0; t < threadNum; t++) {
                thread[t] = new static_block_Thread(t, threadNum);
            }

            long startTime = System.currentTimeMillis();

            for (int t = 0; t < threadNum; t++) {
                thread[t].start();
            }

            for (int t = 0; t < threadNum; t++) {
                thread[t].join();
            }

            long endTime = System.currentTimeMillis();
            long timeDiff = endTime - startTime;

            System.out.println(threadNum + " Thread\n");

            for (int t = 0; t < threadNum; t++) {
                System.out.println("Thread " + t + " Execution Time: " + thread[t].timeDiff + "ms");
            }

            System.out.println("\nProgram Execution Time: " + timeDiff + "ms");
            System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + static_block_Thread.counter);
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

    static class static_block_Thread extends Thread {
        int start;
        int end;
        static int counter;
        int temp = 0;
        long startTime;
        long endTime;
        long timeDiff;

        public static_block_Thread(int threadNum, int threadAmount) {
            this.start = (NUM_END / threadAmount) * threadNum;
            if (threadNum != threadAmount - 1) {
                this.end = (NUM_END / threadAmount) * (threadNum + 1);
            } else {
                this.end = NUM_END;
            }
        }

        @Override
        public void run() {
            startTime = System.currentTimeMillis();
            for (int i = start; i < end; i++) {
                if (isPrime(i))
                    temp++;
            }

            counter += temp;
            endTime = System.currentTimeMillis();
            timeDiff = endTime - startTime;
        }
    }
}
