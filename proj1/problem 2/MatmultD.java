import java.util.*;
import java.lang.*;

// command-line execution example) java MatmultD 6 mat500.txt
// 6 means the number of threads to use
// mat500.txt means the file that contains two matrices is given as standard input
//

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
import java.io.*;
public class MatmultD
{
    private static int MAX_ROW = 500;
    private static int NUM_THREADS = 1;
    private static int[] THREAD_NUMS = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 32 };
    private static String FILE_NAME = "problem2\\mat500.txt"; // 파일 입출력을 위한 변수 설정
    private static Scanner sc = null;


    static int a[][];
    static int b[][];
    static int ans[][];
    public static void main(String [] args) throws InterruptedException {
        if(args.length==2){
            THREAD_NUMS = new int[1]; 
            THREAD_NUMS[0] = Integer.valueOf(args[0]);//argument가 있을 경우 Thread 개수 설정
            FILE_NAME = args[1];//argument가 있을 경우 파일명 설정
        }
        try{
            String path = System.getProperty("user.dir");//파일 현재경로(proj1)
            sc = new Scanner(new File(path+"\\"+FILE_NAME));//파일 열기
        }catch(FileNotFoundException e){
            e.printStackTrace();//파일 없을 경우 Exception 처리
        }
        a = readMatrix();
        b = readMatrix();

        for (int i = 0; i < THREAD_NUMS.length; i++) {
            NUM_THREADS = THREAD_NUMS[i];
            ans = new int[MAX_ROW][MAX_ROW];

            StaticThread[] thread = new StaticThread[NUM_THREADS];
            for (int t = 0; t < NUM_THREADS; t++) {
                thread[t] = new StaticThread(t);
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

            System.out.println("Thread " + NUM_THREADS);

            for (int t = 0; t < NUM_THREADS; t++) {
                System.out.println("Thread " + t + " Execution Time: " + thread[t].timeDiff + "ms");
            }

            System.out.println("\nProgram Execution Time: " + timeDiff + "ms");
            printMatrix(ans);

        }

    }

    public static int[][] readMatrix() {
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        MAX_ROW = rows;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = sc.nextInt();
            }
        }
        return result;
    }

    public static void printMatrix(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum+=mat[i][j];
            }
        }
        System.out.println("Matrix Sum = " + sum + "\n");
    }


    static class StaticThread extends Thread {
        int startRow;
        int endRow;
        static int counter;
        int temp = 0;
        long startTime;
        long endTime;
        long timeDiff;

        public StaticThread(int threadNum) {
            this.startRow = (MAX_ROW / NUM_THREADS) * threadNum;
            if (threadNum != NUM_THREADS - 1) {
                this.endRow = (MAX_ROW / NUM_THREADS) * (threadNum + 1);
            } else {
                this.endRow = MAX_ROW;
            }
        }

        @Override
        public void run() {
            startTime = System.currentTimeMillis();

            multMatrix();

            endTime = System.currentTimeMillis();
            timeDiff = endTime - startTime;
        }

        void multMatrix(){
            if(a.length == 0) return;
            if(a[0].length != b.length) return;

            int n = a[0].length;
            int p = b[0].length;

            for(int row = startRow; row < endRow; row++){
                for(int j = 0;j < p;j++){
                    for(int k = 0;k < n;k++){
                        ans[row][j] += a[row][k] * b[k][j];
                    }
                }
            }
        }
    }
}