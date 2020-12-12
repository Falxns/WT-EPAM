package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectPool {
    private static final List<Connection> connections = new LinkedList<>();
    private static ConnectPool pool;
    private static final int POOL_SIZE = 4;

    public static ConnectPool getConnectionPool() throws ConnectExcept{
        if (pool == null) {
            try
            {
                InitialContext initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource dataSource = (DataSource) envContext.lookup("jdbc/database");
                for (int i = 0; i < POOL_SIZE; i++) {
                    connections.add(dataSource.getConnection());
                }
                pool = new ConnectPool();
            }
            catch (SQLException | NamingException e) {
                throw new ConnectExcept(e);
            }
        }
        return pool;
    }

    public Connection getConnection() {
        while (connections.size() == 0) {
            Thread.yield();
        }
        Connection connection = connections.stream().findFirst().orElseThrow();
        connections.remove(connection);
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }
}