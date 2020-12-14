package com.holub.database;

import java.io.IOException;
import java.util.Iterator;

public class XMLImporter implements Table.Importer {
    @Override
    public void startTable() throws IOException {

    }

    @Override
    public String loadTableName() throws IOException {
        return null;
    }

    @Override
    public int loadWidth() throws IOException {
        return 0;
    }

    @Override
    public Iterator loadColumnNames() throws IOException {
        return null;
    }

    @Override
    public Iterator loadRow() throws IOException {
        return null;
    }

    @Override
    public void endTable() throws IOException {

    }
}
