package launching;

import classes.*;
import configuration.DataBaseConnection;
import configuration.TableCreatorIntoDB;

import java.sql.*;

public class AnnotationApp {
    public static void main(String[] args) throws Exception {

        DataBaseConnection connector = new DataBaseConnection();
        Connection connection = connector.connect();

        User user = new User();
        user.idUser = 1;
        user.nameUser = "Alex";
        user.surnameUser = "Kitov";
        user.userPassword = "12345678";
        String query = new TableCreatorIntoDB(connection, User.class).toSQLString();
        System.out.println(query);
        connector.createTableInDb(User.class);
        connector.insertCOlumns(user);

    }
}