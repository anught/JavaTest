package Filetest;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class TextFileIterator implements Iterator<String> {

    // stream being read from
    BufferedReader in;
    // return value of next call to next()
    String nextline;

    // the structure method of TextFileItrator
    public TextFileIterator(String filename) {
        // 打开文件并读取第一行 如果第一行存在获得第一行
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);

            in = new BufferedReader(inputStreamReader);
            nextline = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public TextFileIterator(String filename , Charset charset) throws IOException {
        // 打开文件并读取第一行 如果第一行存在获得第一行
        in = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), charset));
        nextline = in.readLine();
    }
    // if the next line is non-null then we have a next line
    @Override
    public boolean hasNext() {
        return nextline != null;
    }

    // return the next line,but first read the line that follows it
    @Override
    public String next() {
        try {
            String result = nextline;
            //if we dont have reached EOF yet
            if (nextline != null) {
                nextline = in.readLine(); // read another line
                if (nextline == null)
                    in.close(); // and close on EOF
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
    // we dont need remove the line just read it
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void fileClose(){
        try {
            in.close();
        } catch (Exception e) {
        }
    }

    public static void main(String []agrs){
        String filename="F:\\tmp\\ces.txt";
        //使用增强for循环进行文件的读取


//        for(String line:new TextFile(filename)){
//            System.err.println(line);
//        }

        //直接使用next也行
//        TextFile a = new TextFile(filename);
//        Iterator m = a.iterator();
//        m.hasNext();
//        while(m.hasNext()){
//            System.out.println(m.next());
//        }

        //或者
        TextFileIterator mm = null;
        try {
            mm = new TextFileIterator(filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(;mm.hasNext();)
            System.out.println(mm.next());

//        while(mm != null && mm.hasNext()){
//            System.out.println(mm.next());
//        }
    }
}