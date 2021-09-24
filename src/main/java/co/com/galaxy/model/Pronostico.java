package co.com.galaxy.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName = "Pronostico")
public class Pronostico {

    private String pronosticoId;
    private String clima;
    private int dia;
    private double perimetro;

    @JsonIgnore
    @DynamoDBHashKey(attributeName = "PronosticoId")
    public String getPronosticoId() {
        return pronosticoId;
    }

    public void setPronosticoId(String pronosticoId) {
        this.pronosticoId = pronosticoId;
    }

    @DynamoDBAttribute(attributeName = "clima")
    public String getClima() {
        return clima;
    }

    @DynamoDBAttribute(attributeName = "dia")
    public int getDia() {
        return dia;
    }

    @JsonIgnore
    @DynamoDBAttribute(attributeName = "perimetro")
    public double getPerimetro() {
        return perimetro;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setPerimetro(double perimetro) {
        this.perimetro = perimetro;
    }
}
