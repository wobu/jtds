package freetds;

import java.sql.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ScrollableStatement {

    Statement stmt;

    public ScrollableStatement( Statement stmt )
    {
        this.stmt = stmt;
    }

    public ScrollableResultSet executeQuery( String sql )
    throws SQLException
    {
        return new ScrollableResultSet ( this, sql );
    }

    public void close()
    throws SQLException
    {
        stmt.close();
    }
}