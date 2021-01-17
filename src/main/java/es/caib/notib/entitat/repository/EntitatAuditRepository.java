/**
 * 
 */
package es.caib.notib.entitat.repository;

import es.caib.notib.entitat.entity.EntitatAudit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Definició dels mètodes necessaris per a gestionar una entitat de base
 * de dades del tipus entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public interface EntitatAuditRepository extends JpaRepository<EntitatAudit, Long> {

}
