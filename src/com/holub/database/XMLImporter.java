package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class XMLImporter implements Table.Importer {
    private BufferedReader in;            // null once end-of-file reached
    private List<String> columnNames;
    private String tableName;
    private List<String> bufferItem;

    XMLImporter(Reader in) {
        this.in = in instanceof BufferedReader
                ? (BufferedReader) in
                : new BufferedReader(in);
    }

    @Override
    public void startTable() throws IOException {
        columnNames = new ArrayList<String>();
        bufferItem = new ArrayList<String>();

        String XMLDocType = in.readLine().trim();
        String XMLHeaderExp = "^<\\?xml\\sversion\\S*\\sencoding\\S*\\?>$";

        if (Pattern.matches(XMLHeaderExp, XMLDocType)) {
            tableName = in.readLine().trim();
            tableName = tableName.substring(1, tableName.length()-1);

            String line;
            if ((in.readLine().trim().equals("<item>"))) {
                while (!(line = in.readLine().trim()).equals("</item>")) {
                    String[] lineSplit = line.split(">");
                    columnNames.add(lineSplit[0].substring(1));
                    bufferItem.add(lineSplit[1].split("<")[0]);
                }
            }
        }

    }

    @Override
    public String loadTableName() throws IOException {
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException {
        return columnNames.size();
    }

    @Override
    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(columnNames.toArray());
    }

    @Override
    public Iterator loadRow() throws IOException {
        ArrayList<String> rowItem = new ArrayList<String>(columnNames.size());

        if (!bufferItem.isEmpty()) {
            while(!bufferItem.isEmpty()) {
                String item = bufferItem.remove(0);
                rowItem.add(item);
            }
        }
        else {
            int i = 0;
            String line = in.readLine().trim();
            if (line != null && line.equals("<item>")) {
                while (!(line = in.readLine().trim()).equals("</item>")){
                    int startIndex = line.indexOf(">");
                    int endIndex = line.indexOf("</");
                    rowItem.add(line.substring(startIndex + 1, endIndex));
                }
            }
            else
                return null;
        }
        return new ArrayIterator(rowItem.toArray());
    }

    @Override
    public void endTable() throws IOException { /*nothing to implement */ }
}
