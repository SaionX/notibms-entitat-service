package es.caib.notib.entitat.entity;

import es.caib.notib.entitat.audit.NotibAuditoria;
import es.caib.notib.entitat.audit.TipusOperacio;
import es.caib.notib.entitat.dto.EntitatDto;
import es.caib.notib.entitat.dto.EntitatTipusEnumDto;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Classe del model de dades que representa la informació de auditoria d'una entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Getter
@Entity
@Table(name="not_entitat_audit")
@EntityListeners(AuditingEntityListener.class)
public class EntitatAudit extends NotibAuditoria<Long> {

	@Column(name = "entitat_id")
	private Long entitatId;
	@Column(name = "codi", length = 64)
	private String codi;
	@Column(name = "nom", length = 256)
	private String nom;
	@Column(name = "tipus")
	@Enumerated(EnumType.STRING)
	private EntitatTipusEnumDto tipus;
	@Column(name = "dir3_codi", length = 9)
	private String dir3Codi;
	@Column(name = "dir3_codi_reg", length = 9)
	private String dir3CodiReg;
	@Column(name = "api_key", length = 64)
	private String apiKey;
	@Column(name = "amb_entrega_deh")
	private boolean ambEntregaDeh;
	@Column(name = "amb_entrega_cie")
	private boolean ambEntregaCie;
	@Column(name = "descripcio", length = 1024)
	private String descripcio;
	@Column(name = "activa")
	private boolean activa;
	@Column(name = "oficina", length = 255)
	private String oficina;
	@Column(name = "llibre_entitat")
	private boolean llibreEntitat;
	@Column(name = "llibre")
	private String llibre;
	@Column(name = "llibre_nom")
	private String llibreNom;

	
	public static Builder getBuilder(
			EntitatDto entitatDto,
			TipusOperacio tipusOperacio,
			String joinPoint) {
		return new Builder(
				entitatDto,
				tipusOperacio,
				joinPoint);
	}

	public static class Builder {
		EntitatAudit built;
		Builder(
				EntitatDto entitatDto,
				TipusOperacio tipusOperacio, 
				String joinPoint) {
			built = new EntitatAudit();
			built.tipusOperacio = tipusOperacio;
			built.joinPoint = joinPoint;
			built.entitatId = entitatDto.getId();
			built.codi = entitatDto.getCodi();
			built.nom = entitatDto.getNom();
			built.descripcio = entitatDto.getDescripcio();
			built.tipus = entitatDto.getTipus();
			built.activa = entitatDto.isActiva();
			built.dir3Codi = entitatDto.getDir3Codi();
			built.dir3CodiReg = entitatDto.getDir3CodiReg();
			built.apiKey = entitatDto.getApiKey();
			built.ambEntregaDeh = entitatDto.isAmbEntregaDeh();
			built.ambEntregaCie = entitatDto.isAmbEntregaCie();
			built.oficina = entitatDto.getOficina();
			built.llibreEntitat = entitatDto.isLlibreEntitat();
			built.llibre = entitatDto.getLlibre();
			built.llibreNom = entitatDto.getLlibreNom();
		}
		public EntitatAudit build() {
			return built;
		}
	}

	private static final long serialVersionUID = -2299453443943600172L;

}
