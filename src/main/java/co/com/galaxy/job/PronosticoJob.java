package co.com.galaxy.job;

import co.com.galaxy.controller.WeatherCalculations;
import co.com.galaxy.model.Planeta;
import co.com.galaxy.model.Pronostico;
import co.com.galaxy.model.Punto;
import co.com.galaxy.repository.PlanetaRepository;
import co.com.galaxy.repository.PronosticoRepository;
import co.com.galaxy.util.LocalCache;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static co.com.galaxy.util.Utils.*;

@Component
@RequiredArgsConstructor
public class PronosticoJob {

    private final LocalCache localCache;
    private final WeatherCalculations calculos;
    private final PronosticoRepository pronosticoRepository;
    private final PlanetaRepository planetaRepository;

    @Scheduled(cron = "${cron_job}")
    public void procesoJob(){
        Map<String, Object> map = localCache.getCache();
        String fechaActual = LocalDate.now().toString();

        if(!fechaActual.equals(map.get(FECHA_ULTIMO_DIA))){
            int ultimoDia = (int) map.get(ULTIMO_DIA) + 1;
            map.put(ULTIMO_DIA, ultimoDia);
            map.put(FECHA_ULTIMO_DIA, fechaActual);
            pronosticoRepository.save(this.calcularPronostico(ultimoDia));
            LOG.info("Job ejecutado con exito en la fecha " + fechaActual);
        }
    }

    private Pronostico calcularPronostico(int dia){
        List<Punto> puntoList = this.getPuntos(dia);
        return calculos.getPronostico(dia, puntoList.get(0),
                puntoList.get(1), puntoList.get(2));

    }

    private List<Punto> getPuntos(int dia){
        List<Punto> puntoList = new ArrayList<>();
        Iterator<Planeta> iterator = planetaRepository.findAll().iterator();
        while (iterator.hasNext()){
            puntoList.add(iterator.next().getPosicion(dia));
        }
        return puntoList;
    }
}
