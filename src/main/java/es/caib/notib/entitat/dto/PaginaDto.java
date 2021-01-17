/**
 * 
 */
package es.caib.notib.entitat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Informació resultant d'executar una consulta paginada.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginaDto<T> implements Iterable<T>, Serializable {

	private int numero; // De la pàgina actual
	private int tamany; // De la pàgina actual
	private int total; // De pàgines
	private long elementsTotal;
	private boolean anteriors;
	private boolean primera;
	private boolean posteriors;
	private boolean darrera;
	private List<T> contingut = new ArrayList<T>();

	public PaginaDto(Page<?> page) {
		setPageParams(page);
	}
	public PaginaDto(List<?> list) {
		setPageParams(list);
	}

	public void setPageParams(Page<?> page) {
		this.setNumero(page.getNumber());
		this.setTamany(page.getSize());
		this.setTotal(page.getTotalPages());
		this.setElementsTotal(page.getTotalElements());
		this.setAnteriors(page.hasPrevious());
		this.setPrimera(page.isFirst());
		this.setPosteriors(page.hasNext());
		this.setDarrera(page.isLast());
	}

	public void setPageParams(List<?> list) {
		this.setNumero(0);
		this.setTamany(list.size());
		this.setTotal(1);
		this.setElementsTotal(list.size());
		this.setAnteriors(false);
		this.setPrimera(true);
		this.setPosteriors(false);
		this.setDarrera(true);
	}

	public int getElementsNombre() {
		if (isBuida())
			return 0;
		else
			return contingut.size();
	}
	public boolean isBuida() {
		return contingut == null || contingut.size() == 0;
	}

	public Iterator<T> iterator() {
		if (contingut != null)
			return getContingut().iterator();
		else
			return new ArrayList<T>().iterator();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private static final long serialVersionUID = -139254994389509932L;

}
