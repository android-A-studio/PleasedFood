package net.hycollege.ljl.pleasedfood.bean;

public class Means {
    //设置卡片之间的间距
    public static String getNotetextOnViewPagerCard(String note){
        int length=note.length();
        if (length<=20){
            return note+"\n"+"\n"+"\n";
        }else if (length<=50){
            return note+"\n"+"\n";
        }else {
            return note.substring(0,50)+"..."+"\n";
        }
    }
}
