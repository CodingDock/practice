package org.xmm.snowflake;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by 肖明明 on 2017/4/17.
 */
public class IdGeneratorTest {
    
    public static class Task implements Runnable{
        
        private IDGenerator idg;
        private CyclicBarrier cb;
        private ConcurrentHashMap cmap;
        private CountDownLatch latch;
        
        public Task() {
        }

        public Task(IDGenerator idg,CyclicBarrier cb,ConcurrentHashMap cmap,CountDownLatch latch) {
            this.idg = idg;
            this.cmap=cmap;
            this.cb = cb;
            this.latch=latch;
        }

        @Override
        public void run() {
            try {
                cb.await();
                long id=idg.nextId();
                cmap.put(id,id);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static void main(String[] args) {

        IDGenerator ig=new IDGenerator();
        ig.initWorkId(3);
        int num=10000;
        CyclicBarrier cb=new CyclicBarrier(num, new Runnable() {
            @Override
            public void run() {
                System.out.println("All Thread Is Ready,Mission Start... ");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Start Time:"+System.currentTimeMillis());
            }
        });
        CountDownLatch latch = new CountDownLatch(num);
        ConcurrentHashMap cmap=new ConcurrentHashMap();
        try {
            for(int i=0;i<num;i++){
                Thread a= new Thread(new Task(ig,cb,cmap,latch));
                a.start();
            }
            latch.await();
            System.out.println("End Time:"+System.currentTimeMillis());
            System.out.println(cmap.size());
            System.out.println(cmap.values());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    
}
