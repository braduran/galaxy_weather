package co.com.galaxy.model.test;

import co.com.galaxy.model.Punto;
import co.com.galaxy.model.Triangulo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrianguloTest {

    private static Triangulo triangulo;
    private static Punto p1;
    private static Punto p2;
    private static Punto p3;

    @BeforeAll
    public static void setUp(){
        p1 = getPunto(1, 1);
        p2 = getPunto(-1, 1);
        p3 = getPunto(0, 2);
        triangulo = getTriangulo(p1, p2, p3);
    }

    @Test
    public void debeRetornarArea(){
        assertEquals(1, triangulo.area());
    }

    @Test
    public void debeContenerPunto(){
        Punto p4 = getPunto(0, 1.5);
        assertEquals(true, triangulo.contieneUnPunto(p4));
    }

    @Test
    public void noDebeContenerPunto(){
        Punto p4 = getPunto(0, 5);
        assertEquals(false, triangulo.contieneUnPunto(p4));
    }

    @Test
    public void debeMedirElPerimetro(){
        assertEquals(4.82842712474619, triangulo.perimetro());
    }

    private static Punto getPunto(double xParam, double yParam){
        return Punto.builder()
                .x(xParam).y(yParam)
                .build();
    }

    private static Triangulo getTriangulo(Punto p1, Punto p2, Punto p3){
        return Triangulo.builder()
                .punto1(p1).punto2(p2)
                .punto3(p3).build();
    }
}
