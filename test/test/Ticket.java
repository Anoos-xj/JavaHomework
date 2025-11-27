package test;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String name;
    private String idCard;
    private String startCity;
    private String endCity;

    private static final long serialVersionUID = 1L;

    public Ticket() {

    }
    public Ticket(String name, String idCard, String startCity, String endCity) {
        this.name = name;
        this.idCard = idCard;
        this.startCity = startCity;
        this.endCity = endCity;
    }
    public Ticket(String[] info) {
        this.name = info[0];
        this.idCard = info[1];
        this.startCity = info[2];
        this.endCity = info[3];
    }
    public String getName() {
        return name;
    }
    public String getIdCard() {
        return idCard;
    }
    public String getStartCity() {
        return startCity;
    }
    public String getEndCity() {
        return endCity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }
    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }
    @Override
    public String toString() {
        return name + "," + idCard + "," + startCity + "," + endCity;
    }
}
