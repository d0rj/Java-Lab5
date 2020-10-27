package com.d0rj.documents;


public interface IDocument {

    String getName();
    String getExtension();
    int getLengthBytes();

    boolean setName(String name);
    boolean setExtension(String extension);
}
