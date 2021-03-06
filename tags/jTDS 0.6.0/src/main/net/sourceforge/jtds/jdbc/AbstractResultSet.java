package net.sourceforge.jtds.jdbc;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class implements all of the get and update methods, which are delegated to the row object.
 * <p>
 * This is so we can easily subclass this object to provide say cached result sets, or cursor-based
 * result sets.
 *
 * @author   chris
 * @author   Alin Sinpalean
 * @created  17 March 2001
 * @version  $Id: AbstractResultSet.java,v 1.6 2004-01-22 23:49:07 alin_sinpalean Exp $
 */
public abstract class AbstractResultSet implements ResultSet
{
    public final static String cvsVersion = "$Id: AbstractResultSet.java,v 1.6 2004-01-22 23:49:07 alin_sinpalean Exp $";

    public final static int DEFAULT_FETCH_SIZE = 100;

    /**
     * Number of rows to fetch once. An implementation may ignore this.
     */
    protected int fetchSize = DEFAULT_FETCH_SIZE;

    /**
     * The <code>ResultSet</code>'s warning chain.
     */
    protected SQLWarningChain warningChain = null;

    /**
     * The <code>ResultSet</code>'s meta data.
     */
    ResultSetMetaData metaData = null;

    /**
     * Used to format numeric values when scale is specified.
     */
    private static NumberFormat f = NumberFormat.getInstance();

    /**
     * Used to normalize date and time values.
     */
    private static Calendar staticCalendar = new GregorianCalendar();

    /**
     * Returns the <code>Context</code> of the <code>ResultSet</code> instance. A
     * <code>Context</code> holds information about the <code>ResultSet</code>'s columns.
     */
    public abstract Context getContext();

    /**
     * Returns the current row in the <code>ResultSet</code>, or <code>null</code> if there is no
     * current row.
     *
     * @exception  SQLException  if an SQL error occurs or there is no current row
     */
    public abstract PacketRowResult currentRow() throws SQLException;

    public ResultSetMetaData getMetaData() throws SQLException
    {
        if( metaData == null )
            metaData = new TdsResultSetMetaData(getContext().getColumnInfo());
        return metaData;
    }

    public java.io.InputStream getAsciiStream(String columnName) throws SQLException
    {
        return getAsciiStream(findColumn(columnName));
    }

    public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException
    {
        return getBigDecimal(findColumn(columnName), scale);
    }

    public java.io.InputStream getBinaryStream(String columnName) throws SQLException
    {
        return getBinaryStream(findColumn(columnName));
    }

    public boolean getBoolean(String columnName) throws SQLException
    {
        return getBoolean(findColumn(columnName));
    }

    public byte getByte(String columnName) throws SQLException
    {
        return getByte(findColumn(columnName));
    }

    public byte[] getBytes(String columnName) throws SQLException
    {
        return getBytes(findColumn(columnName));
    }

    public java.sql.Date getDate(String columnName) throws SQLException
    {
        return getDate(findColumn(columnName));
    }

    public double getDouble(String columnName) throws SQLException
    {
        return getDouble(findColumn(columnName));
    }

    public float getFloat(String columnName) throws SQLException
    {
        return getFloat(findColumn(columnName));
    }

    public int getInt(String columnName) throws SQLException
    {
        return getInt(findColumn(columnName));
    }

    public long getLong(String columnName) throws SQLException
    {
        return getLong(findColumn(columnName));
    }

    public Object getObject(String columnName) throws SQLException
    {
        return getObject(findColumn(columnName));
    }

    public short getShort(String columnName) throws SQLException
    {
        return getShort(findColumn(columnName));
    }

    public String getString(String columnName) throws SQLException
    {
        return getString(findColumn(columnName));
    }

    public java.sql.Time getTime(String columnName) throws SQLException
    {
        return getTime(findColumn(columnName));
    }

    public java.sql.Timestamp getTimestamp(String columnName) throws SQLException
    {
        return getTimestamp(findColumn(columnName));
    }

    public java.io.InputStream getUnicodeStream(String columnName) throws SQLException
    {
        return getUnicodeStream(findColumn(columnName));
    }

    public Ref getRef(String colName) throws SQLException
    {
        return getRef(findColumn(colName));
    }

    public java.sql.Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException
    {
        return getTimestamp(findColumn(columnName), cal);
    }

    public java.sql.Date getDate(String columnName, Calendar cal) throws SQLException
    {
        return getDate(findColumn(columnName), cal);
    }

    public java.sql.Time getTime(String columnName, Calendar cal) throws SQLException
    {
        return getTime(findColumn(columnName), cal);
    }

    public short getShort(int index) throws SQLException
    {
        return (short)getLong(index);
    }

    public Object getObject(String colName, java.util.Map map) throws SQLException
    {
        return getObject(findColumn(colName), map);
    }

    public Blob getBlob(String colName) throws SQLException
    {
        return getBlob(findColumn(colName));
    }

    public Clob getClob(String colName) throws SQLException
    {
        return getClob(findColumn(colName));
    }

    public Array getArray(String colName) throws SQLException
    {
        return getArray(findColumn(colName));
    }

    public float getFloat(int index) throws SQLException
    {
        return (float)getDouble(index);
    }

    public int getInt(int index) throws SQLException
    {
        return (int)getLong(index);
    }

    public java.io.InputStream getAsciiStream(int index) throws SQLException
    {
        String val = getString(index);
        if( val == null )
            return null;

        try
        {
            return new ByteArrayInputStream(val.getBytes("ASCII"));
        }
        catch (UnsupportedEncodingException ue)
        {
            // plain impossible with encoding ASCII
            return null;
        }
    }

    public BigDecimal getBigDecimal(int index, int scale) throws SQLException
    {
        return currentRow().getBigDecimal(index, scale);
    }

    public java.io.InputStream getBinaryStream(int index) throws SQLException
    {
        byte[] bytes = getBytes(index);
        if( bytes != null )
            return new ByteArrayInputStream(bytes);
        return null;
    }

    public boolean getBoolean(int index) throws SQLException
    {
        return currentRow().getBoolean(index);
    }

    public byte getByte(int index) throws SQLException
    {
        return (byte) getLong(index);
    }

    public byte[] getBytes(int index) throws SQLException
    {
        return currentRow().getBytes(index);
    }

    public java.sql.Date getDate(int index) throws SQLException
    {
        java.sql.Date result = null;
        java.sql.Timestamp tmp = getTimestamp(index);

        if( tmp != null )
            synchronized( staticCalendar )
            {
                staticCalendar.setTime(tmp);
                staticCalendar.set(Calendar.HOUR_OF_DAY, 0);
                staticCalendar.set(Calendar.MINUTE, 0);
                staticCalendar.set(Calendar.SECOND, 0);
                staticCalendar.set(Calendar.MILLISECOND, 0);
                result = new java.sql.Date(staticCalendar.getTime().getTime());
            }
        return result;
    }

    public double getDouble(int index) throws SQLException
    {
        return currentRow().getDouble(index);
    }

    public long getLong(int index) throws SQLException
    {
        return currentRow().getLong(index);
    }

    public Object getObject(int index) throws SQLException
    {
        if( currentRow() == null )
            throw new SQLException("No current row in the result set.");
        return currentRow().getObject(index);
    }

    public String getString(int index) throws SQLException
    {
        Object tmp = getObject(index);

        if( tmp == null )
            return null;
        // Binary value, generate hex string
        else if( tmp instanceof byte[] )
        {
            byte[] b = (byte[])tmp;
            StringBuffer buf = new StringBuffer(2*b.length);

            for( int i=0; i<b.length; i++ )
            {
                int n=((int)b[i])&0xFF, v=n/16;
                buf.append((char)(v<10 ? '0'+v : 'A'+v-10));
                v = n%16;
                buf.append((char)(v<10 ? '0'+v : 'A'+v-10));
            }
            return buf.toString();
        }
        else if( tmp instanceof Boolean )
        {
            return ((Boolean)tmp).booleanValue() ? "1" : "0";
        }
        else
            return tmp.toString();
    }

    public java.sql.Time getTime(int index) throws SQLException
    {
        java.sql.Time result = null;
        java.sql.Timestamp tmp = getTimestamp(index);

        if( tmp != null )
            synchronized( staticCalendar )
            {
                staticCalendar.setTime(tmp);
                staticCalendar.set(Calendar.ERA, GregorianCalendar.AD);
                staticCalendar.set(Calendar.YEAR, 1970);
                staticCalendar.set(Calendar.MONTH, 0);
                staticCalendar.set(Calendar.DAY_OF_MONTH, 1);
                result = new java.sql.Time(staticCalendar.getTime().getTime());
            }
        return result;
    }

    public java.io.Reader getCharacterStream(int index) throws SQLException
    {
        String val = getString(index);

        if( val == null )
            return null;
        return new java.io.StringReader(val);
    }

    public java.io.Reader getCharacterStream(String columnName) throws SQLException
    {
        return getCharacterStream(findColumn(columnName));
    }

    public BigDecimal getBigDecimal(int index) throws SQLException
    {
        return currentRow().getBigDecimal(index);
    }

    public BigDecimal getBigDecimal(String columnName) throws SQLException
    {
        return currentRow().getBigDecimal(findColumn(columnName));
    }

    public Object getObject(int i, java.util.Map map) throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public Ref getRef(int i) throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public Blob getBlob(int i) throws SQLException
    {
        byte[] value = getBytes(i);

        if( value == null )
            return null;
        else
            return new BlobImpl(value);
    }

    public Clob getClob(int i) throws SQLException
    {
        String value = getString(i);

        if( value == null )
            return null;
        else
            return new ClobImpl(value);
    }

    public Array getArray(int i) throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public java.sql.Timestamp getTimestamp(int index, Calendar cal)
             throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public java.sql.Date getDate(int index, Calendar cal) throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public java.sql.Time getTime(int index, Calendar cal)
             throws SQLException
    {
        throw new java.lang.UnsupportedOperationException("Not Implemented");
    }

    public java.sql.Timestamp getTimestamp(int index) throws SQLException
    {
        return currentRow().getTimestamp(index);
    }

    public java.io.InputStream getUnicodeStream(int index) throws SQLException
    {
        String val = getString(index);
        if( val == null )
            return null;

        try
        {
            return new ByteArrayInputStream(val.getBytes("UTF-8"));
        }
        catch( UnsupportedEncodingException e )
        {
            // plain impossible with UTF-8
            return null;
        }
    }

    public int findColumn(String columnName) throws SQLException
    {
        int i;
        Columns info = getContext().getColumnInfo();

        for( i=1; i<=info.fakeColumnCount(); i++ )
            /** @todo Also need to look at the fully qualified name, i.e. table.column */
            if( info.getName(i).equalsIgnoreCase(columnName) )
                return i;

        throw new SQLException("No such column " + columnName);
    }

    public void updateNull(int index) throws SQLException
    {
        updateObject(index, null);
    }

    public void updateNull(String columnName) throws SQLException
    {
        updateNull(findColumn(columnName));
    }

    public void updateBoolean(String columnName, boolean x) throws SQLException
    {
        updateBoolean(findColumn(columnName), x);
    }

    public void updateByte(String columnName, byte x) throws SQLException
    {
        updateByte(findColumn(columnName), x);
    }

    public void updateShort(String columnName, short x) throws SQLException
    {
        updateShort(findColumn(columnName), x);
    }

    public void updateInt(String columnName, int x) throws SQLException
    {
        updateInt(findColumn(columnName), x);
    }

    public void updateLong(String columnName, long x) throws SQLException
    {
        updateLong(findColumn(columnName), x);
    }

    public void updateFloat(String columnName, float x) throws SQLException
    {
        updateFloat(findColumn(columnName), x);
    }

    public void updateDouble(String columnName, double x) throws SQLException
    {
        updateDouble(findColumn(columnName), x);
    }

    public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException
    {
        updateBigDecimal(findColumn(columnName), x);
    }

    public void updateString(String columnName, String x) throws SQLException
    {
        updateString(findColumn(columnName), x);
    }

    public void updateBytes(String columnName, byte x[]) throws SQLException
    {
        updateBytes(findColumn(columnName), x);
    }

    public void updateDate(String columnName, java.sql.Date x) throws SQLException
    {
        updateDate(findColumn(columnName), x);
    }

    public void updateTime(String columnName, java.sql.Time x) throws SQLException
    {
        updateTime(findColumn(columnName), x);
    }

    public void updateTimestamp(String columnName, java.sql.Timestamp x) throws SQLException
    {
        updateTimestamp(findColumn(columnName), x);
    }

    public void updateAsciiStream(String columnName, java.io.InputStream x, int length)
        throws SQLException
    {
        updateAsciiStream(findColumn(columnName), x, length);
    }

    public void updateBinaryStream(String columnName, java.io.InputStream x, int length)
        throws SQLException
    {
        updateBinaryStream(findColumn(columnName), x, length);
    }

    public void updateCharacterStream(String columnName, java.io.Reader reader, int length)
        throws SQLException
    {
        updateCharacterStream(findColumn(columnName), reader, length);
    }

    public void updateObject(String columnName, Object x, int scale) throws SQLException
    {
        updateObject(findColumn(columnName), x, scale);
    }

    public void updateObject(String columnName, Object x) throws SQLException
    {
        updateObject(findColumn(columnName), x);
    }

    public boolean wasNull() throws SQLException
    {
        return currentRow().wasNull();
    }

    public void updateBoolean(int index, boolean x) throws SQLException
    {
        updateObject(index, new Boolean(x));
    }

    public void updateByte(int index, byte x) throws SQLException
    {
        updateObject(index, new Byte(x));
    }

    public void updateShort(int index, short x) throws SQLException
    {
        updateObject(index, new Short(x));
    }

    public void updateInt(int index, int x) throws SQLException
    {
        updateObject(index, new Integer(x));
    }

    public void updateLong(int index, long x) throws SQLException
    {
        updateObject(index, new Long(x));
    }

    public void updateFloat(int index, float x) throws SQLException
    {
        updateObject(index, new Float(x));
    }

    public void updateDouble(int index, double x) throws SQLException
    {
        updateObject(index, new Double(x));
    }

    public void updateBigDecimal(int index, BigDecimal x) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateString(int index, String x) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateBytes(int index, byte x[]) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateDate(int index, java.sql.Date x) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateTime(int index, java.sql.Time x) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateTimestamp(int index, java.sql.Timestamp x) throws SQLException
    {
        updateObject(index, x);
    }

    public void updateAsciiStream(int index, java.io.InputStream x, int length) throws SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateBinaryStream(int index, java.io.InputStream x, int length) throws SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateCharacterStream(int index, java.io.Reader x, int length) throws SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateObject(int index, Object x, int scale) throws SQLException
    {
        if( x instanceof BigDecimal )
        {
            f.setMaximumFractionDigits(scale);
            updateObject(index, ((BigDecimal)x).setScale(scale));
        }
        else if( x instanceof Number )
        {
            f.setMaximumFractionDigits(scale);
            updateObject(index, f.format(x));
        }
        else
            updateObject( index, x );
    }

    public void updateObject(int index, Object x) throws SQLException
    {
        currentRow().setElementAt( index, x );
    }

    public void updateRef(int param, java.sql.Ref ref) throws java.sql.SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateRef(String columnName, java.sql.Ref ref) throws java.sql.SQLException
    {
        updateRef(findColumn(columnName), ref);
    }

    public void updateClob(int param, java.sql.Clob clob) throws java.sql.SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateClob(String columnName, java.sql.Clob clob) throws java.sql.SQLException
    {
        updateClob(findColumn(columnName), clob);
    }

    public void updateBlob(String columnName, java.sql.Blob blob) throws java.sql.SQLException
    {
        updateBlob(findColumn(columnName), blob);
    }

    public void updateBlob(int param, java.sql.Blob blob) throws java.sql.SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public void updateArray(String columnName, java.sql.Array array) throws java.sql.SQLException
    {
        updateArray(findColumn(columnName), array);
    }

    public void updateArray(int param, java.sql.Array array) throws java.sql.SQLException
    {
        throw new SQLException("Not Implemented");
    }

    public java.net.URL getURL(String columnName) throws java.sql.SQLException
    {
        return getURL(findColumn(columnName));
    }

    public java.net.URL getURL(int param) throws java.sql.SQLException
    {
        throw new SQLException("Not Implemented");
    }
}
