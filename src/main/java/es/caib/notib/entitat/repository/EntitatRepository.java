/**
 * 
 */
package es.caib.notib.entitat.repository;

import es.caib.notib.entitat.entity.EntitatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Definició dels mètodes necessaris per a gestionar una entitat de base
 * de dades del tipus entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public interface EntitatRepository extends JpaRepository<EntitatEntity, Long> {

	Optional<EntitatEntity> findByCodi(String codi);

	Optional<EntitatEntity> findByDir3Codi(String dir3Codi);

	List<EntitatEntity> findByActiva(boolean activa);

	@Query(	"from " +
			"     EntitatEntity eu " +
			"where " +
			"    lower(eu.codi) like concat('%', lower(:filtre), '%') " +
			" or lower(eu.nom) like concat('%', lower(:filtre), '%') " +
			" or lower(eu.dir3Codi) like concat('%', lower(:filtre), '%') ")
	public Page<EntitatEntity> findByFiltre(
			@Param("filtre") String filtre,
			Pageable paginacio);

}
