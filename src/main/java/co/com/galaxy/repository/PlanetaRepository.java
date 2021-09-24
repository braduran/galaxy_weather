package co.com.galaxy.repository;

import co.com.galaxy.model.Planeta;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PlanetaRepository extends CrudRepository<Planeta, String> {
}
