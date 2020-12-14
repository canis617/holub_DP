package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private int	 width;
    private String type;

    public HTMLExporter( Writer out ) {
        this.out = out;
    }

    public void storeMetadata( String tableName,
                               int width,
                               int height,
                               Iterator columnNames ) throws IOException {
        this.width = width;
        this.type = "thead";
        out.write(String.format("\t\t<caption>%s</caption>\n", tableName == null ? "<anonymous>" : tableName));
        storeRow( columnNames ); // comma separated list of columns ids
        this.type = "tbody";
    }

    public void storeRow( Iterator data ) throws IOException {
        int i = width;

        out.write(String.format("\t\t<%s>\n", this.type));

        while( data.hasNext() ) {
            Object datum = data.next();

            // Null columns are represented by an empty field
            // (two commas in a row). There's nothing to write
            // if the column data is null.
            out.write("\t\t\t<th>");
            if( datum != null )
                out.write( datum.toString() );

            out.write("</th>\n");
        }
        out.write(String.format("\t\t</%s>\n", this.type));
    }

    public void startTable() throws IOException {
        out.write("<!DOCTYPE html>\n");
        out.write(
                "<html>\n" +
                        "<head>\n" +
                        "\t<title>HTML Exporter</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\t<table>\n"
        );
    }
    public void endTable()   throws IOException {
        out.write(
                "\t</table>\n" +
                        "</body>\n" +
                        "</html>"
        );
    }
}