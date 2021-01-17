/**
 * 
 */
package es.caib.notib.entitat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Informació d'una entitat.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipusDocumentDto implements Serializable{

	private Long id;
	private Long entitat;
	private TipusDocumentEnumDto tipusDocEnum;
	
	private static final long serialVersionUID = 5695764618684273126L;
}
