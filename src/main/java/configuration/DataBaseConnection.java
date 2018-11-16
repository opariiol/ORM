package configuration;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DataBaseConnection {

    private static final String CREATE_DB = "Create new DataBase, if it no exist";
    private static final String CONNECT_TO_DB = "jdbc:mysql://localhost:3306/";
    private static String dataBaseName = null;
    private static String dataBaseLogin = null;
    private static String dataBasePassword = null;
    private final static Logger log = Logger.getLogger(DataBaseConnection.class);


    static {
        try {
            getInitialConnectWithDB();
            log.debug("Connection to database completed successful");
        } catch (SQLException | IOException e) {
            log.error(e.getMessage());
        }
    }

    public static Connection getConnect() throws SQLException, IOException {
        return DriverManager.getConnection(CONNECT_TO_DB + dataBaseName, dataBaseLogin, dataBasePassword);
    }

    private static void getInitialConnectWithDB () throws SQLException, IOException {
        getInfoFromProperties();

            try (Connection connectionForCreateDbIfNotExists = DriverManager.getConnection(String.valueOf(
                    new StringBuilder("jdbc:mysql://localhost/?user=").append(dataBaseLogin)
                            .append("&password=").append(dataBasePassword)))) {

                Statement statement = connectionForCreateDbIfNotExists.createStatement();
                statement.executeUpdate(CREATE_DB + dataBaseName);
            }
    }



    private static void getInfoFromProperties () throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("src/main/resources/db.properties")));
        dataBaseLogin = properties.get("login").toString();
        dataBasePassword = properties.get("password").toString();
        dataBaseName = properties.get("dbName").toString();
    }
}
