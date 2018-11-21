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
        new InsertColumns(connection,object).toPreparedStatement().executeUpdate();
    }

}





