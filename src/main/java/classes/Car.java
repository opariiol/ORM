package classes;

import annotations.*;

@Table ("cars")

public class Car {

    @PrimaryKey
    @Column("id_car")
    public int idCar;

    @Column ("name_of_car")
    public String nameCar;

    @Column ("speed_limit")
    public int speed_limit;

    public Car (){}

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }


    public int getSpeed_limit() {
        return speed_limit;
    }

    public void setSpeed_limit(int speed_limit) {
        this.speed_limit = speed_limit;
    }

}

