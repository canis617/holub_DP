package com.holub.database;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class DistinctTableTest {
    Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });

    public void report(Throwable t, String message) {
        System.out.println(message + " FAILED with exception toss");
        t.printStackTrace();
        System.exit(1);
    }

    @Test
    public void selectTest() {
        try {
            testInsert();
        } catch (Throwable t) {
            report(t, "Store/Load");
        }
        try {
            testSelect();
        } catch (Throwable t) {
            report(t, "Select");
        }
    }

    public void testInsert() {
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Holub", "Allen", "2" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "3", "Fred", "Flintstone" });

        System.out.println(people.toString());

    }

    public void testSelect() {
        // First test a two-way join

        System.out.println("\nSELECT distinct last,first" + " FROM people");
        // Collection version chains to String[] version,
        // so this code tests both:
        List columns = new ArrayList();
        columns.add("last");
        columns.add("first");

        List tables = new ArrayList();

        Table result = // WHERE people.addrID = address.addrID
                people.select(new Selector.Adapter() {
                    public boolean approve(Cursor[] tables) {
                        return true;
                    }
                }, columns, tables);

        result = new DistinctTable(((UnmodifiableTable)result).extract());
        print(result);
        System.out.println("");
    }

    public void print(Table t) { // tests the table iterator
        Cursor current = t.rows();
        while (current.advance()) {
            for (Iterator columns = current.columns(); columns.hasNext();)
                System.out.print((String) columns.next() + " ");
            System.out.println("");
        }
    }
}