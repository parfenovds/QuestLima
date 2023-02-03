package com.javarush.parfenov.util;

import com.javarush.parfenov.exception.QException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class ConnectionManager {
    private static final Integer DEFAULT_POOL_SIZE = 10;
    public static final String URL = "db.url";
    public static final String USER = "db.user";
    public static final String PASSWORD = "db.password";
    public static final String POOL_SIZE = "db.pool.size";
    private static BlockingQueue<Connection> pool;
    private static List<Connection> realConnections;

    static {
        loadDriver();
        init();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void init() {
        String customPoolSize = PropertiesUtil.get(POOL_SIZE);
        int poolSize = customPoolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(customPoolSize);
        realConnections = new ArrayList<>(poolSize);
        pool = IntStream.range(0, poolSize)
                .mapToObj(i -> {
                    Connection connection = open();
                    addConnectionToRealConnections(connection);
                    return getProxyConnection(connection);
                })
                .collect(Collectors.toCollection(() -> new ArrayBlockingQueue<>(poolSize)));
    }

    private static Connection getProxyConnection(Connection connection) {
        ClassLoader classLoader = ConnectionManager.class.getClassLoader();
        Class<?>[] classes = {Connection.class};
        return (Connection) Proxy.newProxyInstance(classLoader, classes,
                (proxy, method, args) ->
                        method.getName().equals("close")
                                ? pool.add((Connection) proxy)
                                : method.invoke(connection, args));
    }

    private static void addConnectionToRealConnections(Connection connection) {
        realConnections.add(connection);
    }

    private static Connection open() {
        try {
            return DriverManager
                    .getConnection(PropertiesUtil.get(URL),
                            PropertiesUtil.get(USER),
                            PropertiesUtil.get(PASSWORD)
                    );
        } catch (SQLException e) {
            throw new QException(e);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new QException(e);
        }
    }

    public static void closePool() {
        realConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new QException(e);
            }
        });
    }
}
