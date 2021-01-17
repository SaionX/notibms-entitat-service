package es.caib.notib.entitat.service;

import es.caib.notib.entitat.audit.Audita;
import es.caib.notib.entitat.audit.TipusObjecte;
import es.caib.notib.entitat.audit.TipusOperacio;
import es.caib.notib.entitat.dto.EntitatDto;
import es.caib.notib.entitat.dto.PaginaDto;
import es.caib.notib.entitat.dto.PaginacioParamsDto;
import es.caib.notib.entitat.dto.TipusDocumentDto;
import es.caib.notib.entitat.entity.EntitatAudit;
import es.caib.notib.entitat.entity.EntitatEntity;
import es.caib.notib.entitat.entity.EntitatTipusDocEntity;
import es.caib.notib.entitat.helper.PaginacioHelper;
import es.caib.notib.entitat.mapper.EntitatMapper;
import es.caib.notib.entitat.repository.EntitatAuditRepository;
import es.caib.notib.entitat.repository.EntitatRepository;
import es.caib.notib.entitat.repository.EntitatTipusDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementació del servei de gestió d'entitats.
 *
 * @author Limit Tecnologies <limit@limit.es>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EntitatServiceImpl implements EntitatService {

    private final EntitatRepository entitatRepository;
    private final EntitatTipusDocRepository entitatTipusDocRepository;
    private final EntitatAuditRepository entitatAuditRepository;
    private final EntitatMapper entitatMapper;

//	@Resource
//	private PaginacioHelper paginacioHelper;
//	@Resource
//	private PermisosHelper permisosHelper;
//	@Resource
//	private CacheHelper cacheHelper;
//	@Resource
//	private MetricsHelper metricsHelper;


    @Audita(operationType = TipusOperacio.CREATE, returnType = TipusObjecte.DTO)
    @Transactional
    @Override
//	@CacheEvict(value = "entitatsUsuari", allEntries = true)
    public EntitatDto create(EntitatDto entitat) {
        EntitatDto result;
//		Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Creant una nova entitat (entitat=" + entitat + ")");

            EntitatEntity entity = EntitatEntity.getBuilder(
                    entitat.getCodi(),
                    entitat.getNom(),
                    entitat.getTipus(),
                    entitat.getDir3Codi(),
                    entitat.getDir3CodiReg(),
                    entitat.getApiKey(),
                    entitat.isAmbEntregaDeh(),
                    entitat.isAmbEntregaCie(),
                    entitat.getLogoCapBytes(),
                    entitat.getLogoPeuBytes(),
                    entitat.getColorFons(),
                    entitat.getColorLletra(),
                    entitat.getTipusDocDefault().getTipusDocEnum(),
                    entitat.getOficina(),
                    entitat.getNomOficinaVirtual(),
                    entitat.isLlibreEntitat(),
                    entitat.getLlibre(),
                    entitat.getLlibreNom()).
                    descripcio(entitat.getDescripcio()).
                    build();

            EntitatEntity entitatSaved = entitatRepository.save(entity);

            if (entitat.getTipusDoc() != null) {
                for (TipusDocumentDto tipusDocument : entitat.getTipusDoc()) {
                    EntitatTipusDocEntity tipusDocEntity = EntitatTipusDocEntity.getBuilder(
                            entitatSaved,
                            tipusDocument.getTipusDocEnum()).build();
                    entitatTipusDocRepository.save(tipusDocEntity);
                }
            }

            result = entitatMapper.entitatToDto(entitatSaved);

        } finally {
//			metricsHelper.fiMetrica(timer);
        }
        return result;
    }

    @Audita(operationType = TipusOperacio.UPDATE, returnType = TipusObjecte.DTO)
    @Transactional
    @Override
    public EntitatDto update(EntitatDto entitat) {
        EntitatDto result;
//		Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Actualitzant entitat existent (entitat=" + entitat + ")");
            // TODO: comprovar permisos
//			entityComprovarHelper.comprovarEntitat(
//					entitat.getId(),
//					true,
//					true,
//					false,
//					false);
            byte[] logoCapActual = null;
            byte[] logoPeuActual = null;
            EntitatEntity entity = entitatRepository.findById(entitat.getId()).get();
            List<EntitatTipusDocEntity> tipusDocsEntity = entitatTipusDocRepository.findByEntitat(entity);

            if (tipusDocsEntity != null && !tipusDocsEntity.isEmpty()) {
                for (TipusDocumentDto tipusDocDto : entitat.getTipusDoc()) {
                    entitatTipusDocRepository.deleteNotInList(
                            entitat.getId(),
                            tipusDocDto.getTipusDocEnum());
                }
            }
            if (entitat.getTipusDoc() == null || entitat.getTipusDoc().isEmpty()) {
                entitatTipusDocRepository.deleteAll(tipusDocsEntity);
            }

            if ((entitat.getTipusDoc() != null && entitat.getTipusDoc().size() > 1) || tipusDocsEntity.isEmpty()) {
                if (entitat.getTipusDoc() != null)
                    for (TipusDocumentDto tipusDocument : entitat.getTipusDoc()) {
                        EntitatTipusDocEntity tipusDocumentActual = entitatTipusDocRepository.findByEntitatAndTipus(
                                entity.getId(),
                                tipusDocument.getTipusDocEnum());
                        if (tipusDocumentActual == null) {
                            EntitatTipusDocEntity tipusDocEntity = EntitatTipusDocEntity.getBuilder(
                                    entity,
                                    tipusDocument.getTipusDocEnum()).build();
                            entitatTipusDocRepository.save(tipusDocEntity);
                        }
                    }
            }
            if (!entitat.isEliminarLogoCap()) {
                if (entitat.getLogoCapBytes() != null && entitat.getLogoCapBytes().length != 0) {
                    logoCapActual = entitat.getLogoCapBytes();
                } else {
                    logoCapActual = entity.getLogoCapBytes();
                }
            }

            if (!entitat.isEliminarLogoPeu()) {
                if (entitat.getLogoPeuBytes() != null && entitat.getLogoPeuBytes().length != 0) {
                    logoPeuActual = entitat.getLogoPeuBytes();
                } else {
                    logoPeuActual = entity.getLogoPeuBytes();
                }
            }

            entity.update(
                    entitat.getCodi(),
                    entitat.getNom(),
                    entitat.getTipus(),
                    entitat.getDir3Codi(),
                    entitat.getDir3CodiReg(),
                    entitat.getApiKey(),
                    entitat.isAmbEntregaDeh(),
                    entitat.isAmbEntregaCie(),
                    entitat.getDescripcio(),
                    logoCapActual,
                    logoPeuActual,
                    entitat.getColorFons(),
                    entitat.getColorLletra(),
                    entitat.getTipusDocDefault().getTipusDocEnum(),
                    entitat.getOficina(),
                    entitat.getNomOficinaVirtual(),
                    entitat.isLlibreEntitat(),
                    entitat.getLlibre(),
                    entitat.getLlibreNom());
            result = entitatMapper.entitatToDto(entity);

        } finally {
//			metricsHelper.fiMetrica(timer);
        }
        return result;
    }

    @Audita(operationType = TipusOperacio.UPDATE, returnType = TipusObjecte.DTO)
    @Transactional
    @Override
    public EntitatDto updateActiva(
            Long id,
            boolean activa) {
        EntitatDto result;
        //		Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Actualitzant propietat activa d'una entitat existent ("
                    + "id=" + id + ", "
                    + "activa=" + activa + ")");
            // TODO: Comprovar permisos
            //			entityComprovarHelper.comprovarPermisos(
            //					null,
            //					true,
            //					false,
            //					false );
            EntitatEntity entitat = entitatRepository.findById(id).get();
            entitat.updateActiva(activa);
            result = entitatMapper.entitatToDto(entitat);
        } finally {
            //			metricsHelper.fiMetrica(timer);
        }
        return result;
    }

    @Audita(operationType = TipusOperacio.DELETE, returnType = TipusObjecte.DTO)
    @Transactional
    @Override
//    @CacheEvict(value = "entitatsUsuari", allEntries = true)
    public EntitatDto delete(Long id) {
        EntitatDto result;
        //        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Esborrant entitat (id=" + id + ")");
            //TODO: comprovar permisos
            //            entityComprovarHelper.comprovarPermisos(
            //                    null,
            //                    true,
            //                    false,
            //                    false);
            EntitatEntity entitat = entitatRepository.findById(id).get();
            List<EntitatTipusDocEntity> tipusDocsEntity = entitatTipusDocRepository.findByEntitat(entitat);
            if (!tipusDocsEntity.isEmpty()) {
                entitatTipusDocRepository.deleteAll(tipusDocsEntity);
            }
            entitatRepository.delete(entitat);
            // TODO: eliminar permisos
            //            permisosHelper.deleteAcl(
            //                    entitat.getId(),
            //                    EntitatEntity.class);
            result = entitatMapper.entitatToDto(entitat);
        } finally {
//            metricsHelper.fiMetrica(timer);
        }
        return result;
    }

//    @Override
//    public List<TipusDocumentDto> findTipusDocumentByEntitat(Long entitatId) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            List<TipusDocumentDto> tipusDocumentsDto = new ArrayList<TipusDocumentDto>();
//
//            EntitatEntity entitat = entitatRepository.findById(entitatId).get();
//
//            List<EntitatTipusDocEntity> tipusDocsEntity = entitatTipusDocRepository.findByEntitat(entitat);
//
//            for (EntitatTipusDocEntity entitatTipusDocEntity : tipusDocsEntity) {
//                TipusDocumentDto tipusDocumentDto = new TipusDocumentDto();
//                tipusDocumentDto.setTipusDocEnum(entitatTipusDocEntity.getTipusDocEnum());
//                tipusDocumentsDto.add(tipusDocumentDto);
//            }
//            return tipusDocumentsDto;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    public TipusDocumentEnumDto findTipusDocumentDefaultByEntitat(Long entitatId) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            EntitatEntity entitat = entitatRepository.findOne(entitatId);
//            return entitat.getTipusDocDefault();
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

    @Transactional(readOnly = true)
    @Override
    public EntitatDto findById(
            Long entitatId) {
        EntitatDto result;
        //        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Consulta de l'entitat (id=" + entitatId + ")");
            Optional<EntitatEntity> entitat = entitatRepository.findById(entitatId);
            if (entitat.isPresent()) {
                result = entitatMapper.entitatToDto(entitat.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. ID: " + entitatId);
            }
        } finally {
            //            metricsHelper.fiMetrica(timer);
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public EntitatDto findByCodi(String codi) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Consulta de l'entitat amb codi (codi=" + codi + ")");
            Optional<EntitatEntity> entitat = entitatRepository.findByCodi(codi);
            if (entitat.isPresent()) {
                // TODO: Comprovar permisos
//            entityComprovarHelper.comprovarPermisos(
//                    null,
//                    true,
//                    true,
//                    true);
                return entitatMapper.entitatToDto(entitat.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. Codi: " + codi);
            }
        } finally {
//            metricsHelper.fiMetrica(timer);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public EntitatDto findByDir3codi(
            String dir3codi) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Consulta de l'entitat amb codi DIR3 (dir3codi=" + dir3codi + ")");
            Optional<EntitatEntity> entitat = entitatRepository.findByDir3Codi(dir3codi);
            if (entitat.isPresent()) {
                return entitatMapper.entitatToDto(entitat.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. Dir3: " + dir3codi);
            }
        } finally {
//            metricsHelper.fiMetrica(timer);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<EntitatDto> findAll() {
//        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Consulta de totes les entitats");
            // TODO: Comprovar permisos
//            entityComprovarHelper.comprovarPermisos(
//                    null,
//                    true,
//                    false,
//                    false);
            List<EntitatEntity> entitats = entitatRepository.findAll();
            return entitats.stream().map(entitatMapper::entitatToDto).collect(Collectors.toList());
        } finally {
//            metricsHelper.fiMetrica(timer);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PaginaDto<EntitatDto> findAllPaginat(PaginacioParamsDto paginacioParams) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
        try {
            log.debug("Consulta de totes les entitats paginades (paginacioParams=" + paginacioParams + ")");
            // TODO: Comprovar permisos
//            entityComprovarHelper.comprovarPermisos(
//                    null,
//                    true,
//                    false,
//                    false);

            Page<EntitatEntity> page = entitatRepository.findByFiltre(
                    paginacioParams.getFiltre(),
                    PaginacioHelper.toSpringDataPageable(paginacioParams));
            PaginaDto<EntitatDto> resposta = new PaginaDto<EntitatDto>(page);
            if (page.hasContent()) {
                resposta.setContingut(page
                        .getContent()
                        .stream()
                        .map(entitatMapper::entitatToDto)
                        .collect(Collectors.toList()));
            }
            // TODO: Afegir permisos
//            for (EntitatDto entitat : resposta.getContingut()) {
//                // Permisos
//                List<PermisDto> permisos = permisosHelper.findPermisos(
//                        entitat.getId(),
//                        EntitatEntity.class);
//                entitat.setPermisos(permisos);

            // TODO: Afegir aplicacions
//            entitat.setNumAplicacions(aplicacioRepository.countByEntitatId(entitat.getId()));
            return resposta;
        } finally {
//            metricsHelper.fiMetrica(timer);
        }
    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<EntitatDto> findAccessiblesUsuariActual(String rolActual) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            logger.debug("Consulta les entitats accessibles per l'usuari actual (usuari=" + auth.getName() + ")");
//            return cacheHelper.findEntitatsAccessiblesUsuari(auth.getName(), rolActual);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional
//    @Override
//    public List<PermisDto> permisFindByEntitatId(
//            Long entitatId) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            logger.debug("Consulta dels permisos de l'entitat (entitatId=" + entitatId + ")");
//            entityComprovarHelper.comprovarPermisos(
//                    null,
//                    true,
//                    true,
//                    true);
//            return permisosHelper.findPermisos(
//                    entitatId,
//                    EntitatEntity.class);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional
//    @Override
//    @CacheEvict(value = "entitatsUsuari", allEntries = true)
//    public void permisUpdate(
//            Long entitatId,
//            PermisDto permis) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            logger.debug("Modificació com a superusuari del permis de l'entitat (" +
//                    "entitatId=" + entitatId + ", " +
//                    "permis=" + permis + ")");
//            entityComprovarHelper.comprovarEntitat(
//                    entitatId,
//                    true,
//                    true,
//                    false,
//                    false);
//            permisosHelper.updatePermis(
//                    entitatId,
//                    EntitatEntity.class,
//                    permis);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional
//    @Override
//    @CacheEvict(value = "entitatsUsuari", allEntries = true)
//    public void permisDelete(
//            Long entitatId,
//            Long permisId) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            logger.debug("Eliminació com a superusuari del permis de l'entitat (" +
//                    "entitatId=" + entitatId + ", " +
//                    "permisId=" + permisId + ")");
//            entityComprovarHelper.comprovarEntitat(
//                    entitatId,
//                    true,
//                    true,
//                    false,
//                    false);
//            permisosHelper.deletePermis(
//                    entitatId,
//                    EntitatEntity.class,
//                    permisId);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }


//    @Override
//    public boolean hasPermisUsuariEntitat() {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            List<EntitatDto> resposta = entityComprovarHelper.findPermisEntitat(
//                    new Permission[]{
//                            ExtendedPermission.USUARI}
//            );
//
//            return (resposta.isEmpty()) ? false : true;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    public boolean hasPermisAdminEntitat() {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            List<EntitatDto> resposta = entityComprovarHelper.findPermisEntitat(
//                    new Permission[]{
//                            ExtendedPermission.ADMINISTRADORENTITAT}
//            );
//
//            return (resposta.isEmpty()) ? false : true;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    public boolean hasPermisAplicacioEntitat() {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            List<EntitatDto> resposta = entityComprovarHelper.findPermisEntitat(
//                    new Permission[]{
//                            ExtendedPermission.APLICACIO}
//            );
//
//            return (resposta.isEmpty()) ? false : true;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    public Map<RolEnumDto, Boolean> getPermisosEntitatsUsuariActual() {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth != null) {
//                return entityComprovarHelper.getPermisosEntitatsUsuariActual(auth);
//            } else {
//                Map<RolEnumDto, Boolean> hasPermisos = new HashMap<RolEnumDto, Boolean>();
//                hasPermisos.put(RolEnumDto.tothom, false);
//                hasPermisos.put(RolEnumDto.NOT_ADMIN, false);
//                hasPermisos.put(RolEnumDto.NOT_APL, false);
//                hasPermisos.put(RolEnumDto.NOT_ADMIN_ORGAN, false);
//                return hasPermisos;
//            }
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<OficinaDto> findOficinesEntitat(String dir3codi) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            List<OficinaDto> oficines = new ArrayList<OficinaDto>();
//            try {
//                //Recupera les oficines d'una entitat
//                oficines = cacheHelper.llistarOficinesEntitat(dir3codi);
//            } catch (Exception e) {
//                String errorMessage = "No s'han pogut recuperar les oficines de l'entitat amb codi: " + dir3codi;
//                logger.error(
//                        errorMessage,
//                        e.getMessage());
//            }
//            return oficines;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional
//    @Override
//    public byte[] getCapLogo() throws NoSuchFileException, IOException {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            String filePath = PropertiesHelper.getProperties().getProperty("es.caib.notib.capsalera.logo");
//            Path path = Paths.get(filePath);
//
//            return Files.readAllBytes(path);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional
//    @Override
//    public byte[] getPeuLogo() throws NoSuchFileException, IOException {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            String filePath = PropertiesHelper.getProperties().getProperty("es.caib.notib.peu.logo");
//            Path path = Paths.get(filePath);
//
//            return Files.readAllBytes(path);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public LlibreDto getLlibreEntitat(
//            String dir3Codi) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            LlibreDto llibre = new LlibreDto();
//            try {
//                llibre = cacheHelper.getLlibreOrganGestor(
//                        dir3Codi,
//                        dir3Codi);
//            } catch (Exception e) {
//                String errorMessage = "No s'ha pogut recuperar el llibre de l'entitat amb codi Dir3: " + dir3Codi;
//                logger.error(
//                        errorMessage,
//                        e.getMessage());
//            }
//            return llibre;
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

//    @Transactional(readOnly = true)
//    @Override
//    public Map<String, OrganismeDto> findOrganigramaByEntitat(String entitatCodi) {
//        Timer.Context timer = metricsHelper.iniciMetrica();
//        try {
//            return cacheHelper.findOrganigramaByEntitat(entitatCodi);
//        } finally {
//            metricsHelper.fiMetrica(timer);
//        }
//    }

    @Transactional
    @Override
    public void audita(
            Object objecteAuditar,
            TipusOperacio tipusOperacio,
            TipusObjecte tipusObjecte,
            String joinPoint) {

        EntitatAudit audit = null;
        boolean isAuditar = true;

        if (objecteAuditar == null) {
            log.error("Error auditoria: No s'ha l'objecte a auditar de tipus: ENTITAT");
            isAuditar = false;
        } else if (!(objecteAuditar instanceof EntitatDto)) {
            log.error("Error auditoria: L'objecte a auditar no és del tipus correcte: EntitatDto");
            isAuditar = false;
        }
//		if (isAuditar) {
        audit = EntitatAudit.getBuilder(
                (EntitatDto) objecteAuditar,
                tipusOperacio,
                joinPoint).build();
        entitatAuditRepository.saveAndFlush(audit);
//		}

    }
}
