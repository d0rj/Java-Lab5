package com.d0rj.documents;


public abstract class TextDocument implements IDocument {

    protected String name;
    protected String extension;
    protected int lengthBytes;


    protected TextDocument(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public int getLengthBytes() {
        return lengthBytes;
    }


    @Override
    public boolean setName(String name) {
        return true;
    }

    @Override
    public boolean setExtension(String extension) {
        return true;
    }
}
