package net.hycollege.ljl.pleasedfood.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 这是一个临时存储菜谱信息的类
 */
public class OrderFoodBean {
    public static List<Map<String, Object>> foodBeans=null;
    //取从网络中缓存下来的菜谱
    public static List<Map<String, Object>> getFoodBean(){
        return foodBeans;
    }
    //存从网络中缓存下来的菜谱
    public static void setFoodBeans(List<Map<String, Object>> foodBean){
        foodBeans=foodBean;
    }
    public static List<Map<String, String>> foodBeansAndNum=new ArrayList<>();

    public static void setFoodBeansAndNum(List<Map<String, String>> foodBean){
        foodBeansAndNum=foodBean;
    }
    public static List<Map<String, String>> getFoodBeanAndNum(){
        return foodBeansAndNum;
    }


}
