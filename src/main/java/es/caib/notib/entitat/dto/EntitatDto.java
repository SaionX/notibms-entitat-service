package es.caib.notib.entitat.dto;

import es.caib.notib.entitat.audit.AuditoriaDto;
import es.caib.notib.model.PermisDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Informació d'una entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EntitatDto extends AuditoriaDto {

	private Long id;
	@EqualsAndHashCode.Include
	private String codi;
	private String nom;
	private EntitatTipusEnumDto tipus;
	@EqualsAndHashCode.Include
	private String dir3Codi;
	private String dir3CodiReg;
	private String apiKey;
	private boolean ambEntregaDeh;
	private boolean ambEntregaCie;
	private String descripcio;
	private boolean activa;
	private byte[] logoCapBytes;
	private boolean eliminarLogoCap;
	private byte[] logoPeuBytes;
	private boolean eliminarLogoPeu;
	private String colorFons;
	private String colorLletra;
	private List<TipusDocumentDto> tipusDoc;
	private TipusDocumentDto tipusDocDefault;
	private List<PermisDto> permisos;
	private boolean usuariActualAdministradorEntitat;
	private boolean usuariActualAdministradorOrgan;
	private Long numAplicacions;
	private String oficina;
	private String nomOficinaVirtual;
	private boolean llibreEntitat;
	private String llibre;
	private String llibreNom;

	public String getLlibreCodiNom() {
		if (llibre != null)
			return llibre + " - " + (llibreNom != null ? llibreNom : "");
		return "";
	}
	
	public int getPermisosCount() {
		if  (permisos == null)
			return 0;
		else
			return permisos.size();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private static final long serialVersionUID = -139254994389509932L;

}
