package org.xmm.hashmaptest;

/**
 * 不同实例，equals方法返回fasle，hash返值相同
 * Created by 肖明明 on 2017/4/15.
 */
public class KeyWithSameHash {
    
    private int i;

    public KeyWithSameHash() {
    }

    public KeyWithSameHash(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyWithSameHash that = (KeyWithSameHash) o;

        return i == that.i;

    }

    @Override
    public int hashCode() {
        return 1;
    }

    public static void main(String[] args) {
        KeyWithSameHash k1=new KeyWithSameHash(1);
        KeyWithSameHash k2=new KeyWithSameHash(2);
        KeyWithSameHash k3=new KeyWithSameHash(1);
        System.out.println("k1.hashCode() is---"+k1.hashCode());
        System.out.println("k2.hashCode() is---"+k2.hashCode());
        System.out.println("k3.hashCode() is---"+k3.hashCode());

        System.out.println("k1.equals(k2)=="+k1.equals(k2));
        System.out.println("k2.equals(k3)=="+k2.equals(k3));
        System.out.println("k1.equals(k3)=="+k1.equals(k3));
        
    }
    
}
