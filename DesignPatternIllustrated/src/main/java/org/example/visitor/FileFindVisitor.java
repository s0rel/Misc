package org.example.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileFindVisitor extends Visitor {
    private String fileType;

    private List<Entry> found = new ArrayList<>();

    public FileFindVisitor(String filetype) {
        this.fileType = filetype;
    }

    public Iterator getFoundFiles() {
        return found.iterator();
    }

    @Override
    public void visit(File file) {
        if (file.getName().endsWith(fileType)) {
            found.add(file);
        }
    }

    @Override
    public void visit(Directory directory) {
        Iterator it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            entry.accept(this);
        }
    }
}
