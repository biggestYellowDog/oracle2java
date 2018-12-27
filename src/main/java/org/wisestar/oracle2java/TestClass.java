package org.wisestar.oracle2java;

import java.text.ParseException;

public class TestClass {

    public static  void main(String [] args) throws java.io.FileNotFoundException, ParseException {
        /*Calendar cal = Calendar.getInstance();
        // 每天定点执行
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);*/
       /* List<List<String>> lists = new ArrayList<List<String>>();
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -8);//当前时间前去一个月，即一个月前的时间
        date = calendar.getTime();//获取一年前的时间，或者一个月前的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时");
        String eightClock = format.format(date);
        System.out.println("hhhhhhhhhhh"+eightClock);
        System.out.println("时间时间时间"+eightClock.substring(4));*/
       /* String s = "1";
        String[] BigType = s.split(",");
        for(int i=0;i<BigType.length;i++){
            if(i == BigType.length-1){
                System.out.println("--------------");
            }
            System.out.println("tttt"+BigType[i]);
        }*/
        //StringBuilder messageString = new StringBuilder();
        //System.out.println("----"+messageString.length());
       // Double a = 5.555D;
       // System.out.println("dddd"+CalculationUtils.round(a,2));
        /*Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, 8);//当前时间前去一个月，即一个月前的时间
        date = calendar.getTime();//获取一年前的时间，或者一个月前的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String eightClock = format.format(date);
        System.out.println("hhhhhhhhhhh"+eightClock);
        System.out.println("rrrrrrrrrrr"+date.getMonth());*/
        //String s = "99%";
        //String s1 = s.substring(0,s.lastIndexOf("%"));
        //System.out.println("hhhhhhhhhhh"+s1);
        //String s = "1c004c6bff344910009f608f5e7bc84c6e2161a2dcf0503027ac4996";
        //System.out.println("-----"+s.substring(16));
        //String userCode = "spdadmin";
        //String timestamp = "1531200134424";
        //String verification = "6c175d44e56d584f22e7efb0f73786b3";
        //String sourceSign = userCode+timestamp+"spdPortal";
       /* String [] userCode2 = userCode.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0;i<userCode2.length;i++){
            if(i == userCode2.length-1){
                stringBuilder.append(userCode2[i]);
            }else{
                stringBuilder.append(userCode2[i]+"_");
            }

        }
        System.out.println("-----\n\n"+stringBuilder.toString());
*/
       //String de = "[1,2]";
       //System.out.println(de.substring(1,de.length()-1));
       /* String yearmonth = "2018-01";
        Date date = new Date();
        int month = date.getMonth();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(2018, month, 1); //输入类型为int类型
        c.get(Calendar.DAY_OF_MONTH);*/
        //long time = 2592000000L;
        //Date t =new Date(time);
        //t.getTime();
        /*Double c = 0.01D;
        boolean f = c==0;
        System.out.println("days---"+);*/
//        String userCode = "999999#15300#15324#15589#";
//        String [] userCode2 = userCode.split("#");
//        for(int i =0;i<userCode2.length;i++){
//            System.out.println("-----\n\n"+userCode2[i]);
//
//        }
        //String s = "广州办事处-2";
        //System.out.println("-----"+s.substring(s.indexOf("-")));

//        Double a=0.03D;
//        Double b=0.02D;
//        System.out.println("----"+(a>b));
//        String s = "11111:444";
//        String f = s.substring(0,s.indexOf(":"));
//        String l = s.substring(s.indexOf(":")+1);
//        System.out.println("fffff---"+f);
//        System.out.println("lllll---"+l);

//        String s = "惠州办事处-张三";
//        System.out.println(s.substring(s.indexOf("-")));
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date nowDate = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
//        Date date = dateFormat.parse("2018-10");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MONTH,1);
//        cal.set(Calendar.DAY_OF_MONTH,0);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        nowDate = cal.getTime();  //获取查询年月的最后一天日期
//        System.out.println("-----"+format.format(nowDate));
        String s = "乌市办事处-陈泽林";
        System.out.println("sss"+s.substring(0,s.indexOf("-")));
    }



}
