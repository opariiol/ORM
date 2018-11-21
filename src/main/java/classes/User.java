package classes;

import annotations.*;

@Table("users")
public class User {

    @PrimaryKey
    @Column("id_user")
    public int idUser;

    @Column ("user_name")
    public String nameUser;

    @Column ("user_surname")
    public String surnameUser;

    @Column ("password")
    public String userPassword;

    public User() { }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public void setSurnameUser(String surnameUser) {
        this.surnameUser = surnameUser;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
