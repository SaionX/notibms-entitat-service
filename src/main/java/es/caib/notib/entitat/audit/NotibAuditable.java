package es.caib.notib.entitat.audit;

import org.springframework.data.jpa.domain.AbstractAuditable;

import java.io.Serializable;

/**
 * Classe basse de on extendre per a activar les auditories.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class NotibAuditable<PK extends Serializable> extends AbstractAuditable<String, PK> {

	private static final long serialVersionUID = 5373083799666869320L;

}
