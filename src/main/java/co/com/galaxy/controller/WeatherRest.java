package co.com.galaxy.controller;

import co.com.galaxy.model.Periodo;
import co.com.galaxy.model.Pronostico;
import co.com.galaxy.repository.PronosticoRepository;
import co.com.galaxy.util.LocalCache;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static co.com.galaxy.util.Utils.*;

@Validated
@RestController
@RequiredArgsConstructor
public class WeatherRest {

    private final PronosticoRepository pronosticoRepository;
    private final LocalCache localCache;

    @GetMapping("/clima/maxima-lluvia")
    public List<Pronostico> picoMaximoLluvia(){
        return pronosticoRepository.findByClima(TORMENTA);
    }

    @GetMapping("/clima")
    public Pronostico climaPorDia(@RequestParam(value="dia")
                                      @Positive int dia){
        Pronostico pronostico = Optional.ofNullable(pronosticoRepository.findByDia(dia))
                .orElse(Pronostico.builder().clima(NO_ENCONTRADO).build());
        pronostico.setDia(dia);
        return pronostico;
    }

    @GetMapping("/periodos")
    public Periodo periodosPorClima(@RequestParam(value="clima")
                                    @Pattern(regexp = "sequia|optimo|lluvia|desconocido|tormenta")
                                                String clima){
        long diasPorClima = pronosticoRepository.countByClima(clima);
        return new Periodo(clima, diasPorClima);
    }

    @GetMapping("/periodos/todos")
    public List<Periodo> periodos(){
        List<Periodo> periodos = new ArrayList<>();
        periodos.add(countPronostico(SEQUIA));
        periodos.add(countPronostico(OPTIMO));
        periodos.add(countPronostico(LLUVIA));
        periodos.add(countPronostico(DESCONOCIDO));
        periodos.add(countPronostico(TORMENTA));
        return periodos;
    }

    private Periodo countPronostico(String clima){
        long dias = pronosticoRepository.countByClima(clima);
        return Periodo.builder()
                .clima(clima)
                .dias(dias).build();
    }
}
