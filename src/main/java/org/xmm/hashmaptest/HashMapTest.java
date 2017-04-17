package org.xmm.hashmaptest;

import java.util.HashMap;

/**
 * Created by 肖明明 on 2017/4/15.
 */
public class HashMapTest {

    public static void main(String[] args) {
        KeyWithSameHashTest();
    }
    
    public static void KeyWithSameHashTest(){
        KeyWithSameHash k1=new KeyWithSameHash(1);
        KeyWithSameHash k2=new KeyWithSameHash(2);
        KeyWithSameHash k3=new KeyWithSameHash(1);
        HashMap map=new HashMap();

        map.put(k1,"k1");
        map.put(k2,"k2");
        map.put(k3,"k3");

        System.out.println(map);
        
    }
    
}
