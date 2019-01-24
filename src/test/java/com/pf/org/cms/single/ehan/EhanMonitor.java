package com.pf.org.cms.single.ehan;

public class EhanMonitor {


    /*
        1. 最简单的恶汉单例模式
        2. 也不会存在线程安全问题
        3. 但是为什么有时候不使用这个呢？
        4. 因为它不像懒汉模式是延迟加载，所谓延迟加载就说需要使用的时候才创建

        5. 其实还有一点为什么大部分不用恶汉，比如传参数的问题。
        6. 加入一个对象的创建需要传一个参数，满足要求的时候再创建，恶汉就做不到了

       */

    private  static EhanMonitor ehanMonitor =new EhanMonitor();

    private  EhanMonitor (){

    }

    public  static  EhanMonitor getEhanMonitor(){
        return  ehanMonitor ;
    }





}
