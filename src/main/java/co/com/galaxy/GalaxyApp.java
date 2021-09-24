package co.com.galaxy;

import co.com.galaxy.controller.WeatherCalculations;
import co.com.galaxy.util.LocalCache;
import co.com.galaxy.model.Planeta;
import co.com.galaxy.repository.PlanetaRepository;
import co.com.galaxy.repository.PronosticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.com.galaxy.util.Utils;

@EnableScheduling
@SpringBootApplication
public class GalaxyApp {

    public static void main(String[] args) {
        SpringApplication.run(GalaxyApp.class, args);
    }

    @Value("${dias_totales}")
    private int diasTotales;

    @Autowired
    LocalCache localCache;

    @Bean
    public CommandLineRunner commandLineRunner(WeatherCalculations calculos, PlanetaRepository planetaRepository,
                                               PronosticoRepository pronosticoRepository){
        return args -> {
            long numPronosticosBD = pronosticoRepository.count();
            if(numPronosticosBD < diasTotales){
                Utils.LOG.info("-> Inicio proceso de carga de datos");
                List<Planeta> planetaList = new ArrayList<>();
                Iterator<Planeta> iterator = planetaRepository.findAll().iterator();
                iterator.forEachRemaining(planetaList::add);

                if(Utils.TRES.equals(planetaList.size())){
                    calculos.start(diasTotales, planetaList.get(0), planetaList.get(1), planetaList.get(2));
                }
            }
        };
    }
}
