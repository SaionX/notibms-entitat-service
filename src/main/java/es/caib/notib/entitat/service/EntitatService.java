package es.caib.notib.entitat.service;

import es.caib.notib.entitat.audit.TipusObjecte;
import es.caib.notib.entitat.audit.TipusOperacio;
import es.caib.notib.entitat.dto.EntitatDto;
import es.caib.notib.entitat.dto.PaginaDto;
import es.caib.notib.entitat.dto.PaginacioParamsDto;
import es.caib.notib.entitat.exception.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Service per a la gestió d'entitats.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
public interface EntitatService {

	@PreAuthorize("hasRole('NOT_SUPER')")
	EntitatDto create(EntitatDto entitat);

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN')")
	EntitatDto update(EntitatDto entitat) throws NotFoundException;

	@PreAuthorize("hasRole('NOT_SUPER')")
	EntitatDto updateActiva(
			Long id,
			boolean activa) throws NotFoundException;

	@PreAuthorize("hasRole('NOT_SUPER')")
	EntitatDto delete(
			Long id) throws NotFoundException;

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
	EntitatDto findById(Long id);

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
	EntitatDto findByCodi(String codi);

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
	EntitatDto findByDir3codi(String dir3Codi);

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
	List<EntitatDto> findAll();

	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
	PaginaDto<EntitatDto> findAllPaginat(PaginacioParamsDto paginacioParams);





//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom')")
//	public List<TipusDocumentDto> findTipusDocumentByEntitat(Long entitatId);
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom')")
//	public TipusDocumentEnumDto findTipusDocumentDefaultByEntitat(Long entitatId);
//
//
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
//	public List<EntitatDto> findAccessiblesUsuariActual(String rolActual);
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
//	public boolean hasPermisUsuariEntitat();
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
//	public boolean hasPermisAdminEntitat();
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom') or hasRole('NOT_APL')")
//	public boolean hasPermisAplicacioEntitat();
//
//	public Map<RolEnumDto, Boolean> getPermisosEntitatsUsuariActual();
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN')")
//	public List<PermisDto> permisFindByEntitatId(
//			Long entitatId) throws NotFoundException;
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN')")
//	public void permisUpdate(
//			Long entitatId,
//			PermisDto permis) throws NotFoundException;
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN')")
//	public void permisDelete(
//			Long entitatId,
//			Long permisId) throws NotFoundException;
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN')")
//	public List<OficinaDto> findOficinesEntitat(
//			String dir3codi);
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom')")
//	byte[] getCapLogo() throws NoSuchFileException, IOException;
//
//	@PreAuthorize("hasRole('NOT_SUPER') or hasRole('NOT_ADMIN') or hasRole('tothom')")
//	byte[] getPeuLogo() throws NoSuchFileException, IOException;
//
//	@PreAuthorize("hasRole('NOT_ADMIN') or hasRole('tothom')")
//	public LlibreDto getLlibreEntitat(String dir3Codi);
	
//	@PreAuthorize("hasRole('tothom')")
//	public Map<String, OrganismeDto> findOrganigramaByEntitat(String entitatCodi);

	void audita(
			Object objecteAuditar,
			TipusOperacio tipusOperacio,
			TipusObjecte tipusObjecte,
			String joinPoint);
}
