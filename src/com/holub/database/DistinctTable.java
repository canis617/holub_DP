package com.holub.database;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class DistinctTable implements Table{
    private Table wrapped;
    DistinctTable (Table table) {
        this.wrapped = doDistinct(table);
    }

    Table doDistinct(Table table){
        Cursor rows = table.rows();

        int columnCount = rows.columnCount();
        String[] columns = new String[columnCount];
        for (int i=0; i < columnCount; i++){
            columns[i] = rows.columnName(i);
        }

        Table distinctTable = TableFactory.create(null, columns);
        while(rows.advance()) {
            Object[] row = new Object[columnCount];
            Iterator rowIter = rows.columns();

            for (int i = 0; rowIter.hasNext(); i++) {
                row[i] = rowIter.next();
            }

            if(!contains(distinctTable, row))
                distinctTable.insert(row);
        }
        return distinctTable;
    }

    public boolean contains(Table table, Object[] values) {
        Cursor cursor = table.rows();
        int columnCount = table.getColumns().length;
        if(columnCount != values.length) return false;

        while (cursor.advance()) {
            Iterator iter = cursor.columns();
            int i=0;
            for (i = 0; i< values.length; i++) {
                Object item = iter.next();
                if (!item.equals(values[i])) break;
                if (!iter.hasNext()) return true;
            }
        }
        return false;
    }

    /** Return an UnmodifieableTable that wraps a clone of the
     *  currently wrapped table. (A deep copy is used.)
     */
    public Object clone() throws CloneNotSupportedException
    {	DistinctTable copy = (DistinctTable) super.clone();
        copy.wrapped = (Table)( wrapped.clone() );
        return copy;
    }

    public int  insert(String[] c, Object[] v 	 ){	illegal(); return 0;}
    public int  insert(Object[] v			   	 ){	illegal(); return 0;}
    public int  insert(Collection c,Collection v ){	illegal(); return 0;}
    public int  insert(Collection v			 	 ){	illegal(); return 0;}
    public int  update(Selector w				 ){	illegal(); return 0;}
    public int  delete( Selector w				 ){	illegal(); return 0;}

    public void begin	 (			 ){ illegal(); }
    public void commit	 (boolean all){ illegal(); }
    public void rollback (boolean all){ illegal(); }

    private final void illegal()
    {	throw new UnsupportedOperationException();
    }

    public Table select(Selector w,String[] r,Table[] o)
    {	return wrapped.select( w, r, o );
    }
    public Table select(Selector where, String[] requestedColumns)
    {	return wrapped.select(where, requestedColumns );
    }
    public Table select(Selector where)
    {	return wrapped.select(where);
    }
    public Table select(Selector w,Collection r,Collection o)
    {	return wrapped.select( w, r, o );
    }
    public Table select(Selector w, Collection r)
    {	return wrapped.select(w, r);
    }
    public Cursor rows()
    {	return wrapped.rows();
    }
    public void  export(Table.Exporter exporter) throws IOException
    {	wrapped.export(exporter);
    }

    @Override
    public String[] getColumns() {
        return wrapped.getColumns();
    }

    public String	toString() 		{ return wrapped.toString();	}
    public String	name()			{ return wrapped.name(); 		}
    public void		rename(String s){ 		 wrapped.rename(s);		}
    public boolean	isDirty()		{ return wrapped.isDirty();		}

    /** Extract the wrapped table. The existence of this method is
     *  problematic, since it allows someone to defeat the unmodifiability
     *  of the table. On the other hand, the wrapped table came in from
     *  outside, so external access is possible through the reference
     *  that was passed to the constructor. Use the method with care.
     */
    public Table extract(){ return wrapped;	}
}
