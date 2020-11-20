package JedisTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
 
public class BloomFilter {
    private static final int DEFAULT_SIZE = 2<<24;
    private static final int[] seeds = new int[] { 500000, 7, 11, 13, 31, 37, 61 };
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func = new SimpleHash[seeds.length];
 
    public BloomFilter(BitSet bits) {
    	this();
    	this.bits = bits;
    }
    
    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }
 
    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }
    
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
        	f.hash(value);
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }
 
    public long [] toDisk() {
    	
    	System.out.println("toDisk");
    	long[] a = bits.toLongArray();
    	System.out.println(a.length);
    	return a;
    	//for(long m :a) {
    		//System.out.println(m);
    	//}
    }
    
    // 内部类，simpleHash
    public static class SimpleHash {
        private int cap;//必须为2的次幂函数
        private int seed;
 
        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }
 
        public int hash(String value) {
//            int result = 0;
//            int len = value.length();
//            for (int i = 0; i < len; i++) {
//                result = seed * result + value.charAt(i);
//            }
        	int result = seed + value.hashCode();
            //System.out.println((cap - 1) & result);
            return (cap - 1) & result;//cap 必须为2的次幂 ，才能用这种方式取余
        }
    }
 

    public static void main(String[] args) throws IOException {
    	
        BloomFilter bf = new BloomFilter();
        List<String> strs = new ArrayList<String>();
        strs.add("123456");
        strs.add("hello word");
        strs.add("transDocId");


        for (int i=0;i<strs.size();i++) {
            String s = strs.get(i);
            boolean bl = bf.contains(s);
            if(bl){
                System.out.println(i+","+s);
            }else{
            	System.out.println("  ===  " + s);
                bf.add(s);
            }
        }
        
        for (int i=0;i<strs.size();i++) {
            String s = strs.get(i);
            boolean bl = bf.contains(s);
            if(bl){
                System.out.println(i+","+s + ":Ture");
            }else{
            	System.out.println("  ===  " + s);
                bf.add(s);
            }
        }
        
        
        BloomFilter bff = new BloomFilter(BitSet.valueOf(bf.toDisk()));
        for (int i=0;i<strs.size();i++) {
            String s = strs.get(i);
            boolean bl = bff.contains(s);
            if(bl){
                System.out.println(i+","+s + ":Ture");
            }else{
            	System.out.println("  ===  " + s);
                bf.add(s);
            }
        }
    }
 
}