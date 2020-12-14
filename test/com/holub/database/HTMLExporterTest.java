package com.holub.database;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HTMLExporterTest {
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    public void report(Throwable t, String message) {
        System.out.println(message + " FAILED with exception toss");
        t.printStackTrace();
        System.exit(1);
    }

    @Test
    public void test() {
        try {
            testStore();
        } catch (Throwable t) {
            report(t, "Store/Load");
        }
    }

    public void testStore() throws IOException, ClassNotFoundException { // Flush the table to disk, then reread it.
        // Subsequent tests that use the
        // "people" table will
        // fail if this operation fails.

        // html exporter test
        Writer htmlOut = new FileWriter("people.html");
        people.export(new HTMLExporter(htmlOut));
        htmlOut.close();
    }
}