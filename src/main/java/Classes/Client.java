package Classes;


import annotations.*;

@Table("clients")

public class Client {

    @PrimaryKey
    @Column("id_client")
    public int id;

    @Column ("client_name")
    public String name;

    @Column ("client_surname")
    public String surnameClient;

   @Column ("order_number")
    public int orderNumber;

   @Column ("cost_Of_order")
    public double price;

   public Client (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnameClient() {
        return surnameClient;
    }

    public void setSurnameClient(String surnameClient) {
        this.surnameClient = surnameClient;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
