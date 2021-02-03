package co.com.csanvel.api;

import co.com.csanvel.model.commons.HeaderName;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class UtilHeader {

    public UtilHeader(){
        throw new IllegalStateException("Utility Class");
    }

    public static Object getHeaders(ServerRequest request, Class<?> spec){
        try {
            return buildHeader(request, spec);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            return null;
        }
    }
    /**
     * Metodo para construir objeto Header
     * @param
     * @return
     */
    public static Object buildHeader(ServerRequest request, Class<?> spec) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        HttpHeaders httpHeaders;
        httpHeaders = request.headers().asHttpHeaders();
        //Crear un nuevo objeto del modelo de validación de cabecera (spec)
        Object object = spec.getDeclaredConstructor().newInstance();
        //recorrer los campos de la clase modelo (cabeceras)
        for (Field f : spec.getFields()) {
            //Se valida que la cabecera no tenga un valor por defecto
            if(f.get(object)==null){
                //Si la anotacion está presente se toma el valor, de lo contrario se toma el nombre del campo
                String name = httpHeaders.getFirst(f.isAnnotationPresent(HeaderName.class) ?
                        f.getAnnotation(HeaderName.class).value() : f.getName());
                f.set(object, name);
            }
        }

        return object;

    }
}
