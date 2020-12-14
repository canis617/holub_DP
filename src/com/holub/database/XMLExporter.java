package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLExporter implements Table.Exporter{

    private final Writer out;
    private int	 width;
    private String tableName;
    private List<String> columnNames;

    public XMLExporter( Writer out ) {
        this.out = out;
    }

    @Override
    public void startTable() throws IOException {
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = tableName;
        this.width = width;
        out.write(String.format("<%s>\n", tableName));
        this.columnNames = new ArrayList<String>(width);
        while ( columnNames.hasNext() ) {
            this.columnNames.add(columnNames.next().toString());
        }
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        int i = width;
        int columnNamePos = 0;

        out.write("\t<item>\n");
        while( data.hasNext() ) {
            Object datum = data.next();

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.

            String columnName = this.columnNames.get(columnNamePos);
            out.write(String.format("\t\t<%s>",columnName));
            if( datum != null )
                out.write( datum.toString() );
            out.write(String.format("</%s>\n",columnName));
            columnNamePos++;
        }
        out.write("\t</item>\n");
    }

    @Override
    public void endTable() throws IOException {
        out.write(String.format("</%s>\n", tableName));
    }
}
