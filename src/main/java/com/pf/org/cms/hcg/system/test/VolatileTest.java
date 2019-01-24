package com.pf.org.cms.hcg.system.test;

public class VolatileTest {

   public  static       long v1 =0L;

    public   void set (long lg){
        v1 =lg;
    }
    public   void getAndIncrement(){
        System.out.println("====start==="+v1);
        v1++;
        System.out.println("======end==="+v1);
    }

    public    long get(){
        System.out.println("get:"+v1);
        return v1;
    }


}

