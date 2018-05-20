package com.lingxi.framework.utils;

import com.lingxi.framework.domain.exception.ServiceException;
import org.springframework.util.ErrorHandler;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskUtils {

    /**
     * Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）
     * Executors.newFixedThreadPool(int)（固定大小线程池）
     * Executors.newSingleThreadExecutor()（单个后台线程）
     */
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void executor(Runnable run){
         executorService.execute(run);
    }

    public static void main(String[] args) {

        for(int i =0 ;i<10;i++){
            final int b = i;
            executor(()-> {
                int c = new Random().nextInt(3000);
                try {
                    Thread.sleep(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "---------" + c);
                if (b == 99) {
                    throw new ServiceException("a");
                }
            });
        }
        System.out.println("test");
    }

}
