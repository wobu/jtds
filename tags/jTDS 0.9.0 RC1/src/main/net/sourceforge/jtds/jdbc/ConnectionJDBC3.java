// jTDS JDBC Driver for Microsoft SQL Server
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
package net.sourceforge.jtds.jdbc;

import java.util.*;
import java.sql.*;

/**
 * Implements JDBC 3.0 specific functionality. Separated from {@link
 * ConnectionJDBC2} in order to allow the same classes to run under both J2SE 1.3
 * (<code>ConnectionJDBC2</code>)and 1.4 (<code>ConnectionJDBC3</code>).
 *
 * @author Alin Sinpalean
 * @author Brian Heineman
 * @author Mike Hutchinson
 *  created    March 30, 2004
 * @version $Id: ConnectionJDBC3.java,v 1.4 2004-08-05 01:45:22 ddkilzer Exp $
 */
public class ConnectionJDBC3 extends ConnectionJDBC2 {
    /** The list of savepoints. */
    private ArrayList savepoints = null;
    private Map savepointProcInTran = null;
    private int savepointId = 0;

    /**
     * Create a new database connection.
     *
     * @param url The connection URL starting jdbc:jtds:.
     * @param props The additional connection properties.
     * @throws SQLException
     */
    ConnectionJDBC3(String url, Properties props) throws SQLException {
        super(url, props);
    }

    /**
     * Add a savepoint to the list maintained by this connection.
     *
     * @param savepoint The savepoint object to add.
     * @throws SQLException
     */
    private void setSavepoint(SavepointImpl savepoint) throws SQLException {
        Statement statement = null;

        try {
            statement = createStatement();
            statement.execute("SAVE TRAN jtds" + savepoint.getId());
        } finally {
            statement.close();
        }

        synchronized (this) {
            if (savepoints == null) {
                savepoints = new ArrayList();
            }

            savepoints.add(savepoint);
        }
    }

    /**
     * Releases all savepoints. Used internally when committing or rolling back
     * a transaction.
     */
    synchronized void clearSavepoints() {
        if (savepoints != null) {
            savepoints.clear();
        }

        if (savepointProcInTran != null) {
            savepointProcInTran.clear();
        }

        savepointId = 0;
    }


// ------------- Methods implementing java.sql.Connection  -----------------

    public synchronized void releaseSavepoint(Savepoint savepoint)
            throws SQLException {
        checkOpen();

        if (savepoints == null) {
            throw new SQLException(
                Messages.get("error.connection.badsavep"), "25000");
        }

        int index = savepoints.indexOf(savepoint);

        if (index == -1) {
            throw new SQLException(
                Messages.get("error.connection.badsavep"), "25000");
        }

        Object tmpSavepoint = savepoints.remove(index);

        if (savepointProcInTran != null) {
            savepointProcInTran.remove(tmpSavepoint);
        }
    }

    public synchronized void rollback(Savepoint savepoint) throws SQLException {
        checkOpen();

        if (savepoints == null) {
            throw new SQLException(
                Messages.get("error.connection.badsavep"), "25000");
        }

        int index = savepoints.indexOf(savepoint);

        if (index == -1) {
            throw new SQLException(
                Messages.get("error.connection.badsavep"), "25000");
        } else if (getAutoCommit()) {
            throw new SQLException(
                Messages.get("error.connection.savenorollback"), "25000");
        }

        Statement statement = null;

        try {
            statement = createStatement();
            statement.execute("ROLLBACK TRAN jtds" + ((SavepointImpl) savepoint).getId());
        } finally {
            statement.close();
        }

        int size = savepoints.size();

        for (int i = size - 1; i >= index; i--) {
            Object tmpSavepoint = savepoints.remove(i);

            if (savepointProcInTran == null) {
                continue;
            }

            List keys = (List) savepointProcInTran.get(tmpSavepoint);

            if (keys == null) {
                continue;
            }

            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();

                removeCachedProcedure(key);
            }
        }
    }

    public Savepoint setSavepoint() throws SQLException {
        checkOpen();

        if (getAutoCommit()) {
            throw new SQLException(
                Messages.get("error.connection.savenoset"), "25000");
        }

        SavepointImpl savepoint = new SavepointImpl(getNextSavepointId());

        setSavepoint(savepoint);

        return savepoint;
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        checkOpen();

        if (getAutoCommit()) {
            throw new SQLException(
                Messages.get("error.connection.savenoset"), "25000");
        } else if (name == null) {
            throw new SQLException(
                Messages.get("error.connection.savenullname", "savepoint"),
                "25000");
        }

        SavepointImpl savepoint = new SavepointImpl(getNextSavepointId(), name);

        setSavepoint(savepoint);

        return savepoint;
    }

    /**
     * Returns the next savepoint identifier.
     *
     * @return the next savepoint identifier
     */
    private synchronized int getNextSavepointId() {
        return ++savepointId;
    }

    /**
     * Add a stored procedure to the cache.
     *
     * @param key The signature of the procedure to cache.
     * @param proc The stored procedure descriptor.
     */
    void addCachedProcedure(String key, ProcEntry proc) {
        super.addCachedProcedure(key, proc);

        addCachedProcedure(key);
    }

    /**
     * Add a stored procedure to the savepoint cache.
     *
     * @param key The signature of the procedure to cache.
     */
    synchronized void addCachedProcedure(String key) {
        if (savepoints == null || savepoints.size() == 0) {
            return;
        }

        if (savepointProcInTran == null) {
            savepointProcInTran = new HashMap();
        }

        // Retrieve the current savepoint
        Object savepoint = savepoints.get(savepoints.size() - 1);

        List keys = (List) savepointProcInTran.get(savepoint);

        if (keys == null) {
            keys = new ArrayList();
        }

        keys.add(key);

        savepointProcInTran.put(savepoint, keys);
    }
}