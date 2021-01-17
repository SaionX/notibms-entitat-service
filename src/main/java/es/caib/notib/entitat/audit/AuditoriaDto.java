/**
 * 
 */
package es.caib.notib.entitat.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Informació d'auditoria.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaDto implements Serializable {

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";

	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;

	public String getCreatedDateAmbFormat() {
		if (createdDate == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(createdDate);
	}
	public String getLastModifiedDateAmbFormat() {
		if (lastModifiedDate == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(lastModifiedDate);
	}

	private static final long serialVersionUID = -139254994389509932L;

}
