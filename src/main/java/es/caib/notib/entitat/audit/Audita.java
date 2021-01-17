package es.caib.notib.entitat.audit;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target(METHOD)
public @interface Audita {

	TipusOperacio operationType();
	TipusObjecte returnType() default TipusObjecte.ENTITAT;

}
