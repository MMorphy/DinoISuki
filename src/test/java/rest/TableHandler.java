package rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TableHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DataSource dataSource;
    private String ip;

    private Connection connection;

    public TableHandler(String ip) {
        this.ip = ip;
        dataSource = dataSource();
    }

    public void clearTables() {
        try {
                connection = dataSource.getConnection();
            tryToClearTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToClearTables() throws SQLException {
        List<String> tableNames = getTableNames();
        clear(tableNames);
    }

    private List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(
                connection.getCatalog(), null, null, new String[]{"TABLE"});

        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
        }

        return tableNames;
    }

    private void clear(List<String> tableNames) throws SQLException {
        Statement statement = buildSqlStatement(tableNames);

        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();

        String tables = "";
        for(String name : tableNames) {
            tables = tables + name + ", ";
        }
        tables = tables.substring(0, tables.length() - 2);

        statement.addBatch("truncate table " + tables + " cascade");

        return statement;
    }

    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + ip + ":5432/dinoisuki");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

}
