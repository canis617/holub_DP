package com.holub.database;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class XMLExporterTest {
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

        // xml exporter test
        Writer xmlOut = new FileWriter("people.xml");
        people.export(new XMLExporter(xmlOut));
        xmlOut.close();
    }
}