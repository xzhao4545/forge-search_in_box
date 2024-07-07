package cn.xzhao.search_in_box.Utils;

import java.util.TreeMap;

public class ItemsCounterMap extends TreeMap<String,Byte> {
    public void add(String key,int number){
        Byte b=this.get(key);
        int res = (b==null?0:b) + number;
        if(res>0){
            this.put(key,(byte) res);
        }else{
            this.remove(key);
        }
    }
    public void sub(String key,int number){
        Byte b=this.get(key);
        int res = (b==null?0:b) - number;
        if(res>0){
            this.put(key,(byte) res);
        }else{
            this.remove(key);
        }
    }

}
