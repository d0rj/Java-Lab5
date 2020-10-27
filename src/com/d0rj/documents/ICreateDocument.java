package com.d0rj.documents;

import java.io.File;
import java.io.FileNotFoundException;


public interface ICreateDocument<T extends IDocument> {

    T CreateNew();
    T CreateOpen(File file) throws FileNotFoundException;
}
