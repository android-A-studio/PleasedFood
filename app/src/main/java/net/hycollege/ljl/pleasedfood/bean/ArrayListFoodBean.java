package net.hycollege.ljl.pleasedfood.bean;

import java.util.List;

/**
 * 这是一个临时存储菜谱信息的类
 */
public class ArrayListFoodBean {
    public static List<FoodBean> foodBeans=null;
    //取从网络中缓存下来的菜谱
    public static List<FoodBean> getFoodBean(){
        return foodBeans;
    }
    //存从网络中缓存下来的菜谱
    public static void setFoodBeans(List<FoodBean> foodBean){
        foodBeans=foodBean;
    }
}
