package co.com.galaxy.repository;

import co.com.galaxy.model.Pronostico;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface PronosticoRepository extends CrudRepository<Pronostico, String> {

    /**
     * Devuelve numero los los pronosticos del clima ingresado
     * @return List<Pronostico>
     */
    @EnableScanCount
    long countByClima(String clima);

    /**
     * Devuelve clima de un dia particular
     * @return Pronostico
     */
    Pronostico findByDia(int dia);

    /**
     *
     * Devuelve pronosticos de un determinado perimetro
     * @return List<Pronostico>
     */
    List<Pronostico> findByPerimetro(double perimetro);

    /**
     * Devuelve pronosticos de un clima determinado
     * @return List<Pronostico>
     */
    List<Pronostico> findByClima(String clima);
}
