/**
 * 
 */
package es.caib.notib.entitat.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Especifica els mètodes que s'han d'emprar per obtenir i modificar la
 * informació relativa a una entitat que està emmagatzemada a dins la base de
 * dades.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public class NotibAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Optional<String> auditorActual = Optional.empty();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
			auditorActual = Optional.of(auth.getName());
		return auditorActual;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(NotibAuditorAware.class);

}
