package Filetest;

import java.util.Iterator;

public class TextFile implements Iterable<String> {

    private String filename;
    public TextFile(String filename) {
        this.filename = filename;
    }
    // one method of the Iterable interface
    @Override
    public Iterator<String> iterator() {
        return new TextFileIterator(filename);
    }

}
