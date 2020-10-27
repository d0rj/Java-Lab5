package com.d0rj.documents;

import javax.swing.text.DefaultStyledDocument;
import java.io.*;


public class CreateStyledTextDocument implements ICreateDocument<StyledTextDocument> {

    @Override
    public StyledTextDocument CreateNew() {
        return new StyledTextDocument("NewDocument.text", "text", new DefaultStyledDocument());
    }


    @Override
    public StyledTextDocument CreateOpen(File file) throws FileNotFoundException {

        try (InputStream fis = new FileInputStream(file);
             var ois = new ObjectInputStream(fis)) {

            var doc = (DefaultStyledDocument) ois.readObject();
            String fileName = file.getName();
            String extension = extractFileExtension(fileName);

            return new StyledTextDocument(fileName, extension, doc);
        }
        catch (ClassNotFoundException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    private String extractFileExtension(String fileName) {
        String result = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0)
            result = fileName.substring(i + 1);

        return result;
    }
}
