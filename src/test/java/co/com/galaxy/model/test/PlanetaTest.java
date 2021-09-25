package co.com.galaxy.model.test;

import co.com.galaxy.model.Planeta;
import co.com.galaxy.model.Punto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetaTest {

    @Test
    public void debeRetornarPosicion(){
        Planeta planeta = Planeta.builder()
                .velocidadAngular(5)
                .sentido(1)
                .distancia(500)
                .build();
        Punto punto = planeta.getPosicion(100);
        assertEquals(Math.round(punto.getX()), -383);
        assertEquals(Math.round(punto.getY()), 321);
    }
}
