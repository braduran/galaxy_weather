package co.com.galaxy.model.test;

import co.com.galaxy.model.Punto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PuntoTest {

    @Test
    public void debeRetornarDistanciaEntreDosPuntos(){
        Punto punto1 = Punto.builder()
                .x(1).y(1).build();
        Punto punto2 = Punto.builder()
                .x(2).y(2).build();
        assertEquals(punto1.distancia(punto2), 1.4142135623730951);
    }
}
