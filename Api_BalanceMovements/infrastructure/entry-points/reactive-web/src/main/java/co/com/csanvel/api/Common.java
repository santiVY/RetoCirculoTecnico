package co.com.csanvel.api;

import co.com.csanvel.model.commons.ErrorHeader;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Common {

    private static final String PUNTOYCOMA= ";";

    public Common(){
        throw new IllegalStateException("Utility Class");
    }


    /**
     * Valida que los campos del objeto cumplan las restricciones
     * @param object
     * @return
     */
    public static ErrorHeader validateObject(Object object){
        ValidatorFactory factory;
        factory = Validation.buildDefaultValidatorFactory();
        Validator validator;
        validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            String message = "";
            for (ConstraintViolation<Object> violation : constraintViolations) {
                message = violation.getMessage();
            }
           return ErrorHeader.builder().code("Error").message(message).build();
        }else{
            return null;
        }
    }

    public static <T> Mono<ServerResponse> responseErrorMessage(ErrorHeader message) {
        return ServerResponse
                .status(400)
                .body(Mono.just(message), ErrorHeader.class);
    }
}
