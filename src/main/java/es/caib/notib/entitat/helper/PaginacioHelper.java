/**
 * 
 */
package es.caib.notib.entitat.helper;

import es.caib.notib.entitat.dto.PaginacioParamsDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helper per a convertir les dades de paginació entre el DTO
 * i Spring-Data.
 * 
 * @author Limit Tecnologies <limit@limit.es>
 */
//@Component
public class PaginacioHelper {

	public static boolean esPaginacioActivada(PaginacioParamsDto dto) {
		return dto.getPaginaTamany() > 0;
	}

	public static <T> Pageable toSpringDataPageable(
			PaginacioParamsDto dto,
			Map<String, String[]> mapeigPropietatsOrdenacio) {
		return PageRequest.of(
				dto.getPaginaNum(),
				dto.getPaginaTamany(),
				toSpringDataSort(dto.getOrdres(), mapeigPropietatsOrdenacio));
	}
	public static  <T> Pageable toSpringDataPageable(
			PaginacioParamsDto dto) {
		return toSpringDataPageable(dto, null);
	}

	public static <T> Sort toSpringDataSort(
			PaginacioParamsDto dto) {
		return toSpringDataSort(dto.getOrdres(), null);
	}
	public static Sort toSpringDataSort(
			List<PaginacioParamsDto.OrdreDto> ordres,
			Map<String, String[]> mapeigPropietatsOrdenacio) {
		List<Order> orders = new ArrayList<Order>();
		if (ordres != null) {
			for (PaginacioParamsDto.OrdreDto ordre: ordres) {
				Direction direccio = PaginacioParamsDto.OrdreDireccioDto.DESCENDENT.equals(ordre.getDireccio()) ? Direction.DESC : Direction.ASC;
				if (mapeigPropietatsOrdenacio != null) {
					String[] mapeig = mapeigPropietatsOrdenacio.get(ordre.getCamp());
					if (mapeig != null) {
						for (String prop: mapeig) {
							orders.add(new Order(
									direccio,
									prop));
						}
					} else {
						orders.add(new Order(
								direccio,
								ordre.getCamp()));
					}
				} else {
					orders.add(new Order(
							direccio,
							ordre.getCamp()));
				}
			}
		}
		if (!orders.isEmpty())
			return Sort.by(orders);
		else
			return Sort.unsorted();
	}

//	public <T> PaginaDto<T> toPaginaDto(
//			Page<?> page,
//			List<?> llista,
//			Class<T> targetType) {
//		PaginaDto<T> dto = new PaginaDto<T>();
//		dto.setNumero(page.getNumber());
//		dto.setTamany(page.getSize());
//		dto.setTotal(page.getTotalPages());
//		dto.setElementsTotal(page.getTotalElements());
//		dto.setAnteriors(page.hasPrevious());
//		dto.setPrimera(page.isFirst());
//		dto.setPosteriors(page.hasNext());
//		dto.setDarrera(page.isLast());
//		if (targetType != null) {
//			dto.setContingut(
//					conversioTipusHelper.convertirList(
//							llista,
//							targetType));
//		}
//		return dto;
//	}
	
//	public <S, T> PaginaDto<T> toPaginaDto(
//			Page<S> page,
//			Class<T> targetType) {
//		return toPaginaDto(page, targetType, null);
//	}
//	public <S, T> PaginaDto<T> toPaginaDto(
//			Page<S> page,
//			Class<T> targetType,
//			Converter<S, T> converter) {
//		PaginaDto<T> dto = new PaginaDto<T>();
//		dto.setNumero(page.getNumber());
//		dto.setTamany(page.getSize());
//		dto.setTotal(page.getTotalPages());
//		dto.setElementsTotal(page.getTotalElements());
//		dto.setAnteriors(page.hasPrevious());
//		dto.setPrimera(page.isFirst());
//		dto.setPosteriors(page.hasNext());
//		dto.setDarrera(page.isLast());
//		if (page.hasContent()) {
//			if (converter == null) {
//				dto.setContingut(
//						conversioTipusHelper.convertirList(
//								page.getContent(),
//								targetType));
//			} else {
//				List<T> contingut = new ArrayList<T>();
//				for (S element: page.getContent()) {
//					contingut.add(
//							converter.convert(element));
//				}
//				dto.setContingut(contingut);
//			}
//		}
//		return dto;
//	}
//	public <T> PaginaDto<T> toPaginaDto(
//			List<?> llista,
//			Class<T> targetType) {
//		PaginaDto<T> dto = new PaginaDto<T>();
//		dto.setNumero(0);
//		dto.setTamany(llista.size());
//		dto.setTotal(1);
//		dto.setElementsTotal(llista.size());
//		dto.setAnteriors(false);
//		dto.setPrimera(true);
//		dto.setPosteriors(false);
//		dto.setDarrera(true);
//		if (targetType != null) {
//			dto.setContingut(
//					conversioTipusHelper.convertirList(
//							llista,
//							targetType));
//		}
//		return dto;
//	}
//
//	public <T> PaginaDto<T> getPaginaDtoBuida(
//			Class<T> targetType) {
//		PaginaDto<T> dto = new PaginaDto<T>();
//		dto.setNumero(0);
//		dto.setTamany(0);
//		dto.setTotal(1);
//		dto.setElementsTotal(0);
//		dto.setAnteriors(false);
//		dto.setPrimera(true);
//		dto.setPosteriors(false);
//		dto.setDarrera(true);
//		return dto;
//	}

//	public interface Converter<S, T> {
//	    T convert(S source);
//	}

}
