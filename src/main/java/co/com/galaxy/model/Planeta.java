package co.com.galaxy.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Builder
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Planeta")
public class Planeta {
    private String planetaId;
    private String nombre;
    private int distancia;
    private int velocidadAngular;
    private int sentido;

    @DynamoDBHashKey(attributeName = "PlanetaId")
    public String getPlanetaId() {
        return planetaId;
    }

    @DynamoDBAttribute(attributeName = "Nombre")
    public String getNombre() {
        return nombre;
    }

    @DynamoDBAttribute(attributeName = "Distancia")
    public int getDistancia() {
        return distancia;
    }

    @DynamoDBAttribute(attributeName = "VelocidadAngular")
    public int getVelocidadAngular() {
        return velocidadAngular;
    }

    @DynamoDBAttribute(attributeName = "Sentido")
    public int getSentido() {
        return sentido;
    }

    public Punto getPosicion(int dia) {
        double posicionEnGrados = (dia * this.velocidadAngular * this.sentido) % 360;
        double posicionEnRad = Math.toRadians(posicionEnGrados);

        double x = Math.cos(posicionEnRad) * this.distancia;
        double y = Math.sin(posicionEnRad) * this.distancia;

        return new Punto(x, y);
    }
}
