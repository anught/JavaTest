package Map;
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;  
import java.util.Set;  
  
// 要研究下总共多少种遍历方式



public class TestMapRemove {  
    public static void main(String[] args){  
        new TestMapRemove().removeByIterator();  
//        new TestMapRemove().removeBymap();  
    }  
    public void removeByIterator(){//正确的删除方式  
        HashMap<Integer, String> map = new HashMap<Integer, String>();  
        map.put(1, "one");  
        map.put(2, "two");  
        map.put(3, "three");  
        System.out.println(map);  
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();  
        while(it.hasNext()){  
            Map.Entry<Integer, String> entry = it.next();  
            if(entry.getKey() == 2)  
                it.remove();//使用迭代器的remove()方法删除元素  
        }  
        System.out.println(map);  
    }  
    public void removeBymap(){//错误的删除方式  
        HashMap<Integer, String> map = new HashMap<Integer, String>();  
        map.put(1, "one");  
        map.put(2, "two");  
        map.put(3, "three");  
        System.out.println(map);  
        Set<Map.Entry<Integer, String>> entries = map.entrySet();  
        for(Map.Entry<Integer, String> entry : entries){  
            if(entry.getKey() == 2){  
                map.remove(entry.getKey());//ConcurrentModificationException  
            }  
        }  
        System.out.println(map);  
    }  
}  
