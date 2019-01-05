package net.hycollege.ljl.pleasedfood.utils;

import java.util.HashMap;
import java.util.Map;

public class StringToMap {
    /**
     * String格式
     * @param key { name=酸甜排骨,num=3,money=25}
     */
    public static Map<String,String> stringToMap(String key){
        //string转map
        Map<String,String> countryMap = new HashMap<String,String>();
        String cms =key.replace("{","").replace("}","");
        //以逗号分割
        String[] countryMapStr = cms.split(",");
        for(String  s:countryMapStr ){
            String[] ms = s.split("=");
            countryMap.put(ms[0], ms[1]);
        }
        return countryMap;
    }
}
