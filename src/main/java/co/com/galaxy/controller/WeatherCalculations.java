package co.com.galaxy.controller;

import co.com.galaxy.util.LocalCache;
import co.com.galaxy.model.Planeta;
import co.com.galaxy.model.Pronostico;
import co.com.galaxy.model.Punto;
import co.com.galaxy.model.Triangulo;
import co.com.galaxy.repository.PronosticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static co.com.galaxy.util.Utils.*;

@Configuration
@RequiredArgsConstructor
public class WeatherCalculations {

    private final PronosticoRepository pronosticoRepository;
    private final LocalCache localCache;
    private double perimetroMayor = 0;

    public void start(int diasTotales, Planeta p1, Planeta p2, Planeta p3){
        LOG.info("-> Eliminando registros para que los datos sean consistentes");
        pronosticoRepository.deleteAll();
        this.guardarDatosParaJob(diasTotales);

        List<Integer> integerList = IntStream.rangeClosed(1, diasTotales)
                .boxed().collect(Collectors.toList());

        Flux.fromIterable(integerList)
                .map(dia -> {
                    Pronostico pronostico = this.getPronostico(dia, p1.getPosicion(dia),
                            p2.getPosicion(dia), p3.getPosicion(dia));
                    if(pronostico.getPerimetro() > perimetroMayor){
                        perimetroMayor = pronostico.getPerimetro();
                    }
                    return pronostico;
                })
                .collectList()
                .subscribe(list -> pronosticoRepository.saveAll(list),
                           error -> LOG.error(error.getMessage()));

        this.calcularMaximaLluvia();
        LOG.info("-> Finalizo la carga inicial de los datos");
    }

    public Pronostico getPronostico(int dia, Punto p1, Punto p2, Punto p3) {
        Triangulo triangulo = Triangulo.builder()
                .punto1(p1)
                .punto2(p2)
                .punto3(p3).build();

        Punto posicionSol = new Punto(0, 0);
        double perimetro = triangulo.perimetro();

        String nombreClima = VACIO;
        if(triangulo.area() == 0){ //Es una linea
            nombreClima = triangulo.contieneUnPunto(posicionSol) ? SEQUIA : OPTIMO;
        } else if(triangulo.contieneUnPunto(posicionSol)){ //Sol esta alineado con planetas
           nombreClima = LLUVIA;
        } else {
           nombreClima = DESCONOCIDO;
        }
        return Pronostico.builder().pronosticoId(UUID.randomUUID().toString())
                .dia(dia).clima(nombreClima).perimetro(perimetro).build();
    }

    private void calcularMaximaLluvia() {
        LOG.info("-> Calculando los dias de maxima lluvia");
        List<Pronostico> tormentas = pronosticoRepository.findByPerimetro(perimetroMayor);
        for(int i = 0; i < tormentas.size(); i++) {
            Pronostico pronostico = tormentas.get(i);
            pronostico.setClima(TORMENTA);
            pronosticoRepository.save(pronostico);
        }
    }

    private void guardarDatosParaJob(int diasTotales){
        Map<String, Object> map = localCache.getCache();
        map.put(ULTIMO_DIA, diasTotales);
        map.put(FECHA_ULTIMO_DIA, LocalDate.now().toString());
        LOG.info("-> Datos guardados para el Job ", diasTotales, map.get(FECHA_ULTIMO_DIA));
    }
}
