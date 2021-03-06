// jTDS JDBC Driver for Microsoft SQL Server and Sybase
// Copyright (C) 2004 The jTDS Project
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
package net.sourceforge.jtds.test;

import java.sql.*;
import java.math.BigDecimal;
import java.io.InputStream;

/**
 * @version 1.0
 */
public class ResultSetTest extends TestBase {
    public ResultSetTest(String name) {
        super(name);
    }

    /**
     * Test BIT data type.
     */
    public void testGetObject1() throws Exception {
        boolean data = true;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #getObject1 (data BIT, minval BIT, maxval BIT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #getObject1 (data, minval, maxval) VALUES (?, ?, ?)");

        pstmt.setBoolean(1, data);
        pstmt.setBoolean(2, false);
        pstmt.setBoolean(3, true);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT data, minval, maxval FROM #getObject1");

        assertTrue(rs.next());

        assertTrue(rs.getBoolean(1));
        assertTrue(rs.getByte(1) == 1);
        assertTrue(rs.getShort(1) == 1);
        assertTrue(rs.getInt(1) == 1);
        assertTrue(rs.getLong(1) == 1);
        assertTrue(rs.getFloat(1) == 1);
        assertTrue(rs.getDouble(1) == 1);
        assertTrue(rs.getBigDecimal(1).byteValue() == 1);
        assertEquals("1", rs.getString(1));

        Object tmpData = rs.getObject(1);

        assertTrue(tmpData instanceof Boolean);
        assertEquals(true, ((Boolean) tmpData).booleanValue());

        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        assertNotNull(resultSetMetaData);
        assertEquals(Types.BIT, resultSetMetaData.getColumnType(1));

        assertFalse(rs.getBoolean(2));
        assertTrue(rs.getBoolean(3));

        assertFalse(rs.next());
        stmt2.close();
        rs.close();
    }

    /**
     * Test TINYINT data type.
     */
    public void testGetObject2() throws Exception {
        byte data = 1;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #getObject2 (data TINYINT, minval TINYINT, maxval TINYINT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #getObject2 (data, minval, maxval) VALUES (?, ?, ?)");

        pstmt.setByte(1, data);
        pstmt.setByte(2, Byte.MIN_VALUE);
        pstmt.setByte(3, Byte.MAX_VALUE);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT data, minval, maxval FROM #getObject2");

        assertTrue(rs.next());

        assertTrue(rs.getBoolean(1));
        assertTrue(rs.getByte(1) == 1);
        assertTrue(rs.getShort(1) == 1);
        assertTrue(rs.getInt(1) == 1);
        assertTrue(rs.getLong(1) == 1);
        assertTrue(rs.getFloat(1) == 1);
        assertTrue(rs.getDouble(1) == 1);
        assertTrue(rs.getBigDecimal(1).byteValue() == 1);
        assertEquals("1", rs.getString(1));

        Object tmpData = rs.getObject(1);

        assertTrue(tmpData instanceof Integer);
        assertEquals(data, ((Integer) tmpData).byteValue());

        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        assertNotNull(resultSetMetaData);
        assertEquals(Types.TINYINT, resultSetMetaData.getColumnType(1));

        assertEquals(rs.getByte(2), Byte.MIN_VALUE);
        assertEquals(rs.getByte(3), Byte.MAX_VALUE);

        assertFalse(rs.next());
        stmt2.close();
        rs.close();
    }

    /**
     * Test SMALLINT data type.
     */
    public void testGetObject3() throws Exception {
        short data = 1;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #getObject3 (data SMALLINT, minval SMALLINT, maxval SMALLINT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #getObject3 (data, minval, maxval) VALUES (?, ?, ?)");

        pstmt.setShort(1, data);
        pstmt.setShort(2, Short.MIN_VALUE);
        pstmt.setShort(3, Short.MAX_VALUE);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT data, minval, maxval FROM #getObject3");

        assertTrue(rs.next());

        assertTrue(rs.getBoolean(1));
        assertTrue(rs.getByte(1) == 1);
        assertTrue(rs.getShort(1) == 1);
        assertTrue(rs.getInt(1) == 1);
        assertTrue(rs.getLong(1) == 1);
        assertTrue(rs.getFloat(1) == 1);
        assertTrue(rs.getDouble(1) == 1);
        assertTrue(rs.getBigDecimal(1).shortValue() == 1);
        assertEquals("1", rs.getString(1));

        Object tmpData = rs.getObject(1);

        assertTrue(tmpData instanceof Integer);
        assertEquals(data, ((Integer) tmpData).shortValue());

        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        assertNotNull(resultSetMetaData);
        assertEquals(Types.SMALLINT, resultSetMetaData.getColumnType(1));

        assertEquals(rs.getShort(2), Short.MIN_VALUE);
        assertEquals(rs.getShort(3), Short.MAX_VALUE);

        assertFalse(rs.next());
        stmt2.close();
        rs.close();
    }

    /**
     * Test INT data type.
     */
    public void testGetObject4() throws Exception {
        int data = 1;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #getObject4 (data INT, minval INT, maxval INT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #getObject4 (data, minval, maxval) VALUES (?, ?, ?)");

        pstmt.setInt(1, data);
        pstmt.setInt(2, Integer.MIN_VALUE);
        pstmt.setInt(3, Integer.MAX_VALUE);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT data, minval, maxval FROM #getObject4");

        assertTrue(rs.next());

        assertTrue(rs.getBoolean(1));
        assertTrue(rs.getByte(1) == 1);
        assertTrue(rs.getShort(1) == 1);
        assertTrue(rs.getInt(1) == 1);
        assertTrue(rs.getLong(1) == 1);
        assertTrue(rs.getFloat(1) == 1);
        assertTrue(rs.getDouble(1) == 1);
        assertTrue(rs.getBigDecimal(1).intValue() == 1);
        assertEquals("1", rs.getString(1));

        Object tmpData = rs.getObject(1);

        assertTrue(tmpData instanceof Integer);
        assertEquals(data, ((Integer) tmpData).intValue());

        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        assertNotNull(resultSetMetaData);
        assertEquals(Types.INTEGER, resultSetMetaData.getColumnType(1));

        assertEquals(rs.getInt(2), Integer.MIN_VALUE);
        assertEquals(rs.getInt(3), Integer.MAX_VALUE);

        assertFalse(rs.next());
        stmt2.close();
        rs.close();
    }

    /**
     * Test BIGINT data type.
     */
    public void testGetObject5() throws Exception {
        long data = 1;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #getObject5 (data DECIMAL(28, 0), minval DECIMAL(28, 0), maxval DECIMAL(28, 0))");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #getObject5 (data, minval, maxval) VALUES (?, ?, ?)");

        pstmt.setLong(1, data);
        pstmt.setLong(2, Long.MIN_VALUE);
        pstmt.setLong(3, Long.MAX_VALUE);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        ResultSet rs = stmt2.executeQuery("SELECT data, minval, maxval FROM #getObject5");

        assertTrue(rs.next());

        assertTrue(rs.getBoolean(1));
        assertTrue(rs.getByte(1) == 1);
        assertTrue(rs.getShort(1) == 1);
        assertTrue(rs.getInt(1) == 1);
        assertTrue(rs.getLong(1) == 1);
        assertTrue(rs.getFloat(1) == 1);
        assertTrue(rs.getDouble(1) == 1);
        assertTrue(rs.getBigDecimal(1).longValue() == 1);
        assertEquals("1", rs.getString(1));

        Object tmpData = rs.getObject(1);

        assertTrue(tmpData instanceof BigDecimal);
        assertEquals(data, ((BigDecimal) tmpData).longValue());

        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        assertNotNull(resultSetMetaData);
        assertEquals(Types.DECIMAL, resultSetMetaData.getColumnType(1));

        assertEquals(rs.getLong(2), Long.MIN_VALUE);
        assertEquals(rs.getLong(3), Long.MAX_VALUE);

        assertFalse(rs.next());
        stmt2.close();
        rs.close();
    }

    /**
     * Test for bug [961594] ResultSet.
     */
    public void testResultSetScroll1() throws Exception {
    	int count = 125;

        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #resultSetScroll1 (data INT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #resultSetScroll1 (data) VALUES (?)");

        for (int i = 1; i <= count; i++) {
            pstmt.setInt(1, i);
            assertEquals(1, pstmt.executeUpdate());
        }

        pstmt.close();

        Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
        		ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt2.executeQuery("SELECT data FROM #resultSetScroll1");

        assertTrue(rs.last());
        assertEquals(count, rs.getRow());

        stmt2.close();
        rs.close();
    }

    /**
     * Test for bug [945462] getResultSet() return null if you use scrollable/updatable.
     */
    public void testResultSetScroll2() throws Exception {
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #resultSetScroll2 (data INT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #resultSetScroll2 (data) VALUES (?)");

        pstmt.setInt(1, 1);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        stmt2.executeQuery("SELECT data FROM #resultSetScroll2");

        ResultSet rs = stmt2.getResultSet();

        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        assertFalse(rs.next());

        stmt2.close();
        rs.close();
    }

    /**
     * Test for bug [1028881] statement.execute() causes wrong ResultSet type.
     */
    public void testResultSetScroll3() throws Exception {
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #resultSetScroll3 (data INT)");
        stmt.execute("CREATE PROCEDURE #procResultSetScroll3 AS SELECT data FROM #resultSetScroll3");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #resultSetScroll3 (data) VALUES (?)");
        pstmt.setInt(1, 1);
        assertEquals(1, pstmt.executeUpdate());
        pstmt.close();

        // Test plain Statement
        Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        assertTrue("Was expecting a ResultSet", stmt2.execute("SELECT data FROM #resultSetScroll3"));

        ResultSet rs = stmt2.getResultSet();
        assertEquals("ResultSet not scrollable", ResultSet.TYPE_SCROLL_INSENSITIVE, rs.getType());

        rs.close();
        stmt2.close();

        // Test PreparedStatement
        pstmt = con.prepareStatement("SELECT data FROM #resultSetScroll3", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        assertTrue("Was expecting a ResultSet", pstmt.execute());

        rs = pstmt.getResultSet();
        assertEquals("ResultSet not scrollable", ResultSet.TYPE_SCROLL_INSENSITIVE, rs.getType());

        rs.close();
        pstmt.close();

        // Test CallableStatement
        CallableStatement cstmt = con.prepareCall("{call #procResultSetScroll3}",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        assertTrue("Was expecting a ResultSet", cstmt.execute());

        rs = cstmt.getResultSet();
        assertEquals("ResultSet not scrollable", ResultSet.TYPE_SCROLL_INSENSITIVE, rs.getType());

        rs.close();
        cstmt.close();
    }

    /**
     * Test for bug [1008208] 0.9-rc1 updateNull doesn't work.
     */
    public void testResultSetUpdate1() throws Exception {
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #resultSetUpdate1 (id INT PRIMARY KEY, dsi SMALLINT NULL, di INT NULL)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #resultSetUpdate1 (id, dsi, di) VALUES (?, ?, ?)");

        pstmt.setInt(1, 1);
        pstmt.setShort(2, (short) 1);
        pstmt.setInt(3, 1);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        stmt.executeQuery("SELECT id, dsi, di FROM #resultSetUpdate1");

        ResultSet rs = stmt.getResultSet();

        assertNotNull(rs);
        assertTrue(rs.next());
        rs.updateNull("dsi");
        rs.updateNull("di");
        rs.updateRow();
        rs.moveToInsertRow();
        rs.updateInt(1, 2);
        rs.updateNull("dsi");
        rs.updateNull("di");
        rs.insertRow();

        stmt.close();
        rs.close();

        stmt = con.createStatement();
        stmt.executeQuery("SELECT id, dsi, di FROM #resultSetUpdate1 ORDER BY id");

        rs = stmt.getResultSet();

        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        rs.getShort(2);
        assertTrue(rs.wasNull());
        rs.getInt(3);
        assertTrue(rs.wasNull());
        assertTrue(rs.next());
        assertEquals(2, rs.getInt(1));
        rs.getShort(2);
        assertTrue(rs.wasNull());
        rs.getInt(3);
        assertTrue(rs.wasNull());
        assertFalse(rs.next());

        stmt.close();
        rs.close();
    }

    /**
     * Test for bug [1009233] ResultSet getColumnName, getColumnLabel return wrong values
     */
    public void testResultSetColumnName1() throws Exception {
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #resultSetCN1 (data INT)");
        stmt.close();

        PreparedStatement pstmt = con.prepareStatement("INSERT INTO #resultSetCN1 (data) VALUES (?)");

        pstmt.setInt(1, 1);
        assertEquals(1, pstmt.executeUpdate());

        pstmt.close();

        Statement stmt2 = con.createStatement();
        stmt2.executeQuery("SELECT data as test FROM #resultSetCN1");

        ResultSet rs = stmt2.getResultSet();

        assertNotNull(rs);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt("test"));
        assertFalse(rs.next());

        stmt2.close();
        rs.close();
    }

    /**
     * Test for fixed bugs in ResultSetMetaData:
     * <ol>
     * <li>isNullable() always returns columnNoNulls.
     * <li>isSigned returns true in error for TINYINT columns.
     * <li>Type names for numeric / decimal have (prec,scale) appended in error.
     * <li>Type names for auto increment columns do not have "identity" appended.
     * </ol>
     * NB: This test assumes getColumnName has been fixed to work as per the suggestion
     * in bug report [1009233].
     *
     * @throws Exception
     */
    public void testResultSetMetaData() throws Exception {
        Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        stmt.execute("CREATE TABLE #TRSMD (id INT IDENTITY NOT NULL, byte TINYINT NOT NULL, num DECIMAL(28,10) NULL)");
        ResultSetMetaData rsmd = stmt.executeQuery("SELECT id as idx, byte, num FROM #TRSMD").getMetaData();
        assertNotNull(rsmd);
        // Check id
        assertEquals("idx", rsmd.getColumnName(1)); // no longer returns base name
        assertEquals("idx", rsmd.getColumnLabel(1));
        assertTrue(rsmd.isAutoIncrement(1));
        assertTrue(rsmd.isSigned(1));
        assertEquals(ResultSetMetaData.columnNoNulls, rsmd.isNullable(1));
        assertEquals("int identity", rsmd.getColumnTypeName(1));
        assertEquals(Types.INTEGER, rsmd.getColumnType(1));
        // Check byte
        assertFalse(rsmd.isAutoIncrement(2));
        assertFalse(rsmd.isSigned(2));
        assertEquals(ResultSetMetaData.columnNoNulls, rsmd.isNullable(2));
        assertEquals("tinyint", rsmd.getColumnTypeName(2));
        assertEquals(Types.TINYINT, rsmd.getColumnType(2));
        // Check num
        assertFalse(rsmd.isAutoIncrement(3));
        assertTrue(rsmd.isSigned(3));
        assertEquals(ResultSetMetaData.columnNullable, rsmd.isNullable(3));
        assertEquals("decimal", rsmd.getColumnTypeName(3));
        assertEquals(Types.DECIMAL, rsmd.getColumnType(3));
        stmt.close();
    }

    /**
     * Test for [1017164] - CallableStatement.executeQuery fails
     * when procedure returns more than one result set.
     *
     * @throws Exception
     */
    public void testTwoSelectCall() throws Exception {
        Statement stmt = con.createStatement();

        stmt.execute("CREATE PROC #TESTP @p1 INT OUTPUT AS\r\n"
        		     + "BEGIN\r\n"
					 + "SELECT 'RS ONE' as ID\r\n"
					 + "SELECT 'RS TWO' as ID\r\n"
					 + "SELECT @p1 = 100\r\n"
					 + "END\r\n");
        stmt.close();

        CallableStatement cstmt = con.prepareCall("{call #TESTP (?)}");
        cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

        ResultSet rs = cstmt.executeQuery();

        assertTrue(rs.next());
        assertEquals("RS ONE", rs.getString(1));
        assertFalse(rs.next());
        assertEquals(100, cstmt.getInt(1));

        rs.close();
        cstmt.close();
    }

    /**
     * Test for bug [1022445] Cursor downgrade warning not raised.
     */
    public void testCursorWarning() throws Exception
    {
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE #TESTCW (id INT PRIMARY KEY, DATA VARCHAR(255))");
        stmt.execute("CREATE PROC #SPTESTCW @P0 INT OUTPUT AS SELECT * FROM #TESTCW");
        stmt.close();
        CallableStatement cstmt = con.prepareCall("{call #SPTESTCW(?)}",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        cstmt.registerOutParameter(1, Types.INTEGER);
        ResultSet rs = cstmt.executeQuery();
        // This should generate a ResultSet type/concurrency downgraded error.
        assertNotNull(rs.getWarnings());
        cstmt.close();
    }

    /**
     * Test whether retrieval by name returns the first occurence (that's what
     * the spec requires).
     */
    public void testGetByName() throws Exception
    {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT 1 myCol, 2 myCol, 3 myCol");
        assertTrue(rs.next());
        assertEquals(1, rs.getInt("myCol"));
        assertFalse(rs.next());
        stmt.close();
    }

    /**
     * Test if COL_INFO packets are processed correctly for
     * <code>ResultSet</code>s with over 255 columns.
     */
    public void testMoreThan255Columns() throws Exception
    {
        Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);

        // create the table
        int cols = 260;
        StringBuffer create = new StringBuffer("create table #manycolumns (");
        for (int i=0; i<cols; ++i) {
            create.append("col" + i + " char(10), ") ;
        }
        create.append(")");
        stmt.executeUpdate(create.toString());

        String query = "select * from #manycolumns";
        ResultSet rs = stmt.executeQuery(query);
        rs.close();
        stmt.close();
    }

    /**
     * Test that <code>insertRow()</code> works with no values set.
     */
    public void testEmptyInsertRow() throws Exception
    {
        int rows = 10;
        Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeUpdate(
                "create table #emptyInsertRow (id int identity, val int default 10)");
        ResultSet rs = stmt.executeQuery("select * from #emptyInsertRow");

        for (int i=0; i<rows; i++) {
            rs.moveToInsertRow();
            rs.insertRow();
        }
        rs.close();

        rs = stmt.executeQuery("select count(*) from #emptyInsertRow");
        assertTrue(rs.next());
        assertEquals(rows, rs.getInt(1));
        rs.close();

        rs = stmt.executeQuery("select * from #emptyInsertRow order by id");
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        assertEquals(10, rs.getInt(2));
        rs.close();
        stmt.close();
    }

    /**
     * Test that inserted rows are visible in a scroll sensitive
     * <code>ResultSet</code>.
     */
    public void testInsertRowVisible() throws Exception
    {
        int rows = 10;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeUpdate(
                "create table #insertRowNotVisible (val int primary key)");
        ResultSet rs = stmt.executeQuery("select * from #insertRowNotVisible");

        for (int i = 1; i <= rows; i++) {
            rs.moveToInsertRow();
            rs.updateInt(1, i);
            rs.insertRow();
            rs.moveToCurrentRow();
            rs.last();
            assertEquals(i, rs.getRow());
        }

        rs.close();
        stmt.close();
    }

    /**
     * Test that updated rows are indeed deleted, and the new values inserted
     * at the end of the <code>ResultSet</code>.
     */
    public void testUpdateRowDuplicatesRow() throws Exception
    {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeUpdate(
                "create table #updateRowDuplicatesRow (val int primary key)");
        stmt.executeUpdate(
                "insert into #updateRowDuplicatesRow (val) values (1)");
        stmt.executeUpdate(
                "insert into #updateRowDuplicatesRow (val) values (2)");
        stmt.executeUpdate(
                "insert into #updateRowDuplicatesRow (val) values (3)");

        ResultSet rs = stmt.executeQuery(
                "select val from #updateRowDuplicatesRow order by val");

        for (int i = 0; i < 3; i++) {
            assertTrue(rs.next());
            assertFalse(rs.rowUpdated());
            assertFalse(rs.rowInserted());
            assertFalse(rs.rowDeleted());
            rs.updateInt(1, rs.getInt(1) + 10);
            rs.updateRow();
            rs.refreshRow();
            assertFalse(rs.rowUpdated());
            assertFalse(rs.rowInserted());
            assertTrue(rs.rowDeleted());
        }

        for (int i = 11; i <= 13; i++) {
            assertTrue(rs.next());
            assertFalse(rs.rowUpdated());
            assertFalse(rs.rowInserted());
            assertFalse(rs.rowDeleted());
            assertEquals(i, rs.getInt(1));
        }

        rs.close();
        stmt.close();
    }

    /**
     * Test that deleted rows are not removed but rather marked as deleted.
     */
    public void testDeleteRowMarksDeleted() throws Exception
    {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        stmt.executeUpdate(
                "create table #deleteRowMarksDeleted (val int primary key)");
        stmt.executeUpdate(
                "insert into #deleteRowMarksDeleted (val) values (1)");
        stmt.executeUpdate(
                "insert into #deleteRowMarksDeleted (val) values (2)");
        stmt.executeUpdate(
                "insert into #deleteRowMarksDeleted (val) values (3)");

        ResultSet rs = stmt.executeQuery(
                "select val from #deleteRowMarksDeleted order by val");

        for (int i = 0; i < 3; i++) {
            assertTrue(rs.next());
            assertFalse(rs.rowUpdated());
            assertFalse(rs.rowInserted());
            assertFalse(rs.rowDeleted());
            rs.deleteRow();
            rs.refreshRow();
            assertFalse(rs.rowUpdated());
            assertFalse(rs.rowInserted());
            assertTrue(rs.rowDeleted());
        }

        assertFalse(rs.next());
        rs.close();
        stmt.close();
    }

    /**
     * Test that <code>absolute(-1)</code> works the same as <code>last()</code>.
     */
    public void testAbsoluteMinusOne() throws Exception {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        stmt.executeUpdate(
                "create table #absoluteMinusOne (val int primary key)");
        stmt.executeUpdate(
                "insert into #absoluteMinusOne (val) values (1)");
        stmt.executeUpdate(
                "insert into #absoluteMinusOne (val) values (2)");
        stmt.executeUpdate(
                "insert into #absoluteMinusOne (val) values (3)");

        ResultSet rs = stmt.executeQuery(
                "select val from #absoluteMinusOne order by val");

        rs.absolute(-1);
        assertTrue(rs.isLast());
        assertEquals(3, rs.getInt(1));
        assertFalse(rs.next());

        rs.last();
        assertTrue(rs.isLast());
        assertEquals(3, rs.getInt(1));
        assertFalse(rs.next());

        rs.close();
        stmt.close();
    }

    /**
     * Test that <code>read()</code> works ok on the stream returned by
     * <code>ResultSet.getUnicodeStream()</code> (i.e. it doesn't always fill
     * the buffer, regardless of whether there's available data or not).
     */
    public void testUnicodeStream() throws Exception {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("create table #unicodeStream (val varchar(255))");
        stmt.executeUpdate("insert into #unicodeStream (val) values ('test')");
        ResultSet rs = stmt.executeQuery("select val from #unicodeStream");

        if (rs.next()) {
            byte[] buf = new byte[8000];
            InputStream is = rs.getUnicodeStream(1);
            int length = is.read(buf);
            assertEquals(4 * 2, length);
        }

        rs.close();
        stmt.close();
    }

    /**
     * Test that <code>Statement.setMaxRows()</code> works on cursor
     * <code>ResultSet</code>s.
     */
    public void testCursorMaxRows() throws Exception {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("create table #cursorMaxRows (val int)");
        stmt.close();

        // Insert 10 rows
        PreparedStatement pstmt = con.prepareStatement(
                "insert into #cursorMaxRows (val) values (?)");
        for (int i = 0; i < 10; i++) {
            pstmt.setInt(1, i);
            assertEquals(1, pstmt.executeUpdate());
        }
        pstmt.close();

        // Create a cursor ResultSet
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        // Set maxRows to 5
        stmt.setMaxRows(5);

        // Select all (should only return 5 rows)
        ResultSet rs = stmt.executeQuery("select * from #cursorMaxRows");
        rs.last();
        assertEquals(5, rs.getRow());
        rs.beforeFirst();

        int cnt = 0;
        while (rs.next()) {
            cnt++;
        }
        assertEquals(5, cnt);

        rs.close();
        stmt.close();
    }

    /**
     * Test for bug [1075977] <code>setObject()</code> causes SQLException.
     * <p>
     * Conversion of <code>float</code> values to <code>String</code> adds
     * grouping to the value, which cannot then be parsed.
     */
    public void testSetObjectScale() throws Exception {
        Statement stmt = con.createStatement();
        stmt.execute("create table #testsetobj (i int)");
        PreparedStatement pstmt =
                con.prepareStatement("insert into #testsetobj values(?)");
        // next line causes sqlexception
        pstmt.setObject(1, new Float(1234.5667), Types.INTEGER, 0);
        assertEquals(1, pstmt.executeUpdate());
        ResultSet rs = stmt.executeQuery("select * from #testsetobj");
        assertTrue(rs.next());
        assertEquals("1234", rs.getString(1));
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ResultSetTest.class);
    }
}
