package co.com.galaxy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class Punto {

    private double x;
    private double y;

    /*
     * @return double
     * calcula distancia entre dos puntos
     */
    public double distancia(Punto punto){
        return Math.sqrt(Math.pow(punto.getX()-this.x, 2) + Math.pow(punto.getY()-this.y, 2));
    }
}
