package com.pf.org.cms.single.neibulei;

import org.springframework.cache.annotation.CacheEvict;

public class ClassMonitor {


    /*
        //1. 如果在懒汉单例模式里面添加一个它的全局属性
        //2. 如果想获取它的yourClass 可以调用直接调用yourClass
        //3. 但是这样对象会初始化，构造方法初始化，我不想让类初始化怎么办
        public static String yourClass = "通信工程1班";

        private static ClassMonitor classMonitor = new ClassMonitor();

        private ClassMonitor() {
            System.out.println("构造方法初始化");
        }

        public static ClassMonitor getClassMonitor() {
            System.out.println("获取对象");
            return classMonitor;
        }

        public static void main(String[] args) {

            System.out.println(yourClass);
        }
    */


    /*
      1. 当执行getInstance()的时候才会调用内部类里面的ClassMonitor实例
      2. 此时，内部类会被加载到内存中，在类加载的时候对monitor进行初始化
      3. 而且直接调用yourClass的时候不会对类初始化
      4. 这是懒汉模式和恶汉模式的结合体，而且线程安全
*/
    public static String yourClass = "通信工程";

    //静态内部类，用来创建班长对象
    private static class MonitorCreator {
        private static ClassMonitor classMonitor = new ClassMonitor();

    }

    private ClassMonitor() {
        System.out.println("构造方法初始化");
    }

    public static ClassMonitor getInstance() {
        System.out.println("静态内部类获取");
        return MonitorCreator.classMonitor;
    }

    public static void main(String[] args) {
        System.out.println(yourClass);
    }


}
