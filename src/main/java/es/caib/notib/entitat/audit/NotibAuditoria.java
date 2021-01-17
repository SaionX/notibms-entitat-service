package es.caib.notib.entitat.audit;

import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Classe basse de on extendre per a activar les auditories.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@MappedSuperclass
public class NotibAuditoria<PK extends Serializable> extends NotibAuditable<PK> {

	private static final long serialVersionUID = 5025928932008668460L;

	@Getter
	@Enumerated(EnumType.STRING)
	protected TipusOperacio tipusOperacio;
	@Getter
	protected String joinPoint;

}
