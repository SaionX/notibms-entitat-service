package es.caib.notib.entitat.controller;

import es.caib.notib.entitat.dto.EntitatDto;
import es.caib.notib.entitat.service.EntitatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class EntitatController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final EntitatService entitatService;

    @GetMapping(produces = { "application/json" }, path = "entitat")
    public ResponseEntity<List<EntitatDto>> listEntitats() {
        log.debug("Llistant entitats");

        List<EntitatDto> entitats = entitatService.findAll();
        return new ResponseEntity<List<EntitatDto>>(entitats, HttpStatus.OK);
    }


//    @GetMapping(produces = { "application/json" }, path = "entitat")
//    public ResponseEntity<List<EntitatDto>> listEntitats(
//            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
//            @RequestParam(value = "pageSize", required = false) Integer pageSize
//            @RequestParam(value = "beerName", required = false) String beerName,
//            @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
//            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand
//            ){
//
//        log.debug("Llistant entitats");
//
//        if (pageNumber == null || pageNumber < 0){
//            pageNumber = DEFAULT_PAGE_NUMBER;
//        }
//        if (pageSize == null || pageSize < 1) {
//            pageSize = DEFAULT_PAGE_SIZE;
//        }
//
//        return entitatService.findAllPaginat(DatatablesHelper.getPaginacioDtoFromRequest(request));
//
//        if (RolHelper.isUsuariActualAdministrador(request)) {
//            return DatatablesHelper.getDatatableResponse(
//                    request,
//                    entitatService.findAllPaginat(
//                            DatatablesHelper.getPaginacioDtoFromRequest(request)));
//        } else if (RolHelper.isUsuariActualAdministradorEntitat(request)) {
//            EntitatDto entitat = EntitatHelper.getEntitatActual(request);
//            return DatatablesHelper.getDatatableResponse(
//                    request,
//                    Arrays.asList(entitat));
//        }
//
//        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
//
//        return new ResponseEntity<>(beerList, HttpStatus.OK);
//    }

//    @GetMapping(path = {"entitat/{entitatId}"}, produces = { "application/json" })
//    public ResponseEntity<BeerDto>  getBeerById(@PathVariable("beerId") Long entitatId,
//                                                @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
//
//        log.debug("Get Request for BeerId: " + beerId);
//
//        if (showInventoryOnHand == null) {
//            showInventoryOnHand = false;
//        }
//
//        return new ResponseEntity<>(beerService.findBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
//    }
//
//    @GetMapping(path = {"beerUpc/{upc}"}, produces = { "application/json" })
//    public ResponseEntity<BeerDto>  getBeerByUpc(@PathVariable("upc") String upc){
//        return new ResponseEntity<>(beerService.findBeerByUpc(upc), HttpStatus.OK);
//    }
//
//    @PostMapping(path = "beer")
//    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto){
//
//        BeerDto savedDto = beerService.saveBeer(beerDto);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        //todo hostname for uri
//        httpHeaders.add("Location", "/api/v1/beer_service/" + savedDto.getId().toString());
//
//        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
//    }
//
//    @PutMapping(path = {"beer/{beerId}"}, produces = { "application/json" })
//    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
//
//        beerService.updateBeer(beerId, beerDto);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping({"beer/{beerId}"})
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteBeer(@PathVariable("beerId") UUID beerId){
//        beerService.deleteById(beerId);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ResponseEntity<List> badReqeustHandler(ConstraintViolationException e){
//        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
//
//        e.getConstraintViolations().forEach(constraintViolation -> {
//            errors.add(constraintViolation.getPropertyPath().toString() + " : " + constraintViolation.getMessage());
//        });
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
}
