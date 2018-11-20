package configuration;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DataBaseConnection {

    private  Connection connection;
    private Statement statement;

    public DataBaseConnection (){}


    public Connection connect() throws Exception{
        Properties properties = new Properties();
        FileInputStream stream = new FileInputStream("/home/olgaoparii/IdeaProjects/ORMitAcademy/src/main/resources/db");
        properties.load(stream);
        stream.close();
        Class.forName(properties.getProperty("jdbc.driver")).getDeclaredConstructor().newInstance();
        connection = null;
        connection = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"),properties.getProperty("jdbc.password"));
        statement = connection.createStatement();
        return connection;
        }

    public void disconnect () throws SQLException{
        statement.close();
        connection.close();
    }

    public void createTableInDb (Class classe) throws Exception{
        new TableCreatorIntoDB(connection, classe).toPrepare().execute();
    }

    public void insertCOlumns(Object object) throws Exception{
        new InsertColumns(connection,object).toPreparedStatement().executeUpdate();}

}














 /*  public Connection connect() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECT_TO_DB, "root", "");
            sout ("connect")
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }





 static {
        try {
            getInitialConnectWithDB();
            log.debug("Connection to database completed successful");
        } catch (SQLException | IOException e) {
            log.error(e.getMessage());
        }
    }*/

   /* public static Connection getConnect() throws SQLException, IOException {
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
        properties.load(new FileInputStream(new File("src/main/resources/db")));
        dataBaseLogin = properties.get("login").toString();
        dataBasePassword = properties.get("password").toString();
        dataBaseName = properties.get("dbName").toString();
    }*/





