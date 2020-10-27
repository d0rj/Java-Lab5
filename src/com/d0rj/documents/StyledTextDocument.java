package com.d0rj.documents;

import javax.swing.text.StyledDocument;


public final class StyledTextDocument extends TextDocument {

    private StyledDocument document;


    public StyledTextDocument(String name, String extension, StyledDocument document) {
        super(name, extension);
        this.document = document;
    }


    public StyledDocument getDocument() {
        return document;
    }
}
