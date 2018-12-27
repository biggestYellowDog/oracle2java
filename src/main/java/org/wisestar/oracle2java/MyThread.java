package org.wisestar.oracle2java;

public class MyThread extends Thread{

    @Override
    public void run() {
       System.out.println(Thread.currentThread().getName()+"正在执行。。。");
 }

}
