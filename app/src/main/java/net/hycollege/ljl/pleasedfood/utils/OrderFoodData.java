package net.hycollege.ljl.pleasedfood.utils;

import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.bean.OrderFoodBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFoodData {
    public static List<FoodBean> list = null;
    public static List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();;
    //保存食物信息
    public static void initFoodData(List<FoodBean> foodBeans) {
        list = foodBeans;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map.put("name", list.get(i).getName_f());
            map.put("money", list.get(i).getPrice_f());
            map.put("pic", list.get(i).getPicture_f());
        }
        listitem.add(map);
        //把菜谱信息存储起来
        OrderFoodBean.setFoodBeans(listitem);
    }

}
