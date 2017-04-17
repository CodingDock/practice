package org.xmm.snowflake;


/**
 * 订单号生成器，用于生成分布式订单号，参考Twitter's SnowFlake算法。<br/>
 *  介绍见：http://www.cnblogs.com/relucent/p/4955340.html<br/>
 *  单例模式，由Spring容器托管，禁止自己创建实例
 * Created by 肖明明 on 2016/11/21.
 */
public class IDGenerator {


    private static volatile long workerId;   //机器序号从零开始
//    private final long epoch = 1479365583074L;   // 时间起始标记点，作为基准，一般取系统的最近时间
    private final long epoch = 0L;   // 时间起始标记点，作为基准，一般取系统的最近时间
    private final long workerIdBits = 10L;      // 机器标识位数 2^4-1个节点,共16个节点
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 机器ID最大值: 15
    private long sequence = 0L;                   // 0，并发控制
    private final long sequenceBits = 12L;      //毫秒内自增位，这个控制一毫秒生成的Id数量，2^4个

    private final long workerIdShift = this.sequenceBits;            // 4
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 8
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;          // 15,1111,4位
    private long lastTimestamp = -1L;

    public IDGenerator() {//构造器
    }

    public void initWorkId(int workerId){
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new RuntimeException("IDGENERATOR_COULD_NOT_GET_WORKID");
        }
        this.workerId = workerId;
    }



    public long getWorkerId() {
        return workerId;
    }

    public synchronized long nextId() {
        if(workerId==0) throw new RuntimeException("IDGENERATOR_COULD_NOT_GET_WORKID");
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) { // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
            System.out.println("Concurrent contention！");
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException("CLOCK_MOVED_BACKWARDS_REFUSING_TO_GENERATE_WORKID");
        }
        this.lastTimestamp = timestamp;
        return timestamp - this.epoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IDGenerator ig=new IDGenerator();
        ig.initWorkId(3);
        long id=ig.nextId();
        System.out.println(id);
        
    }

}
