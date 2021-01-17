/**
 * 
 */
package es.caib.notib.entitat.entity;

import es.caib.notib.entitat.audit.NotibAuditable;
import es.caib.notib.entitat.dto.TipusDocumentEnumDto;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Classe del model de dades que representa una entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Entity
@Table(name="not_entitat_tipus_doc")
@EntityListeners(AuditingEntityListener.class)
public class EntitatTipusDocEntity extends NotibAuditable<Long> {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entitat_id", foreignKey = @ForeignKey(name = "not_entitat_tipus_doc_fk"))
	protected EntitatEntity entitat;
	
	@Column(name = "tipus_doc")
	protected TipusDocumentEnumDto tipusDocEnum;
	
	public EntitatEntity getEntitat() {
		return entitat;
	}
	public TipusDocumentEnumDto getTipusDocEnum() {
		return tipusDocEnum;
	}
	public String getEnumValue() {
		return tipusDocEnum.name();
	}
	public void update(
			EntitatEntity entitat,
			TipusDocumentEnumDto tipusDocEnum) {
		this.entitat = entitat;
		this.tipusDocEnum = tipusDocEnum;
	}

	public static Builder getBuilder(
			EntitatEntity entitat,
			TipusDocumentEnumDto tipusDocEnum) {
		return new Builder(
				entitat,
				tipusDocEnum);
	}

	public static class Builder {
		EntitatTipusDocEntity built;
		Builder(
				EntitatEntity entitat,
				TipusDocumentEnumDto tipusDocEnum) {
			built = new EntitatTipusDocEntity();
			built.entitat = entitat;
			built.tipusDocEnum = tipusDocEnum;
		}
		public EntitatTipusDocEntity build() {
			return built;
		}
	}

	private static final long serialVersionUID = -2299453443943600172L;

}
