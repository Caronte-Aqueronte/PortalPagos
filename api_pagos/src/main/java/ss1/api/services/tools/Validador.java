/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services.tools;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ss1.api.excepciones.BadRequestException;
import ss1.api.excepciones.NotFoundException;
import ss1.api.models.Auditor;

/**
 *
 * @author luid
 */
@Component
public class Validador {

    @Autowired
    private Validator validator;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Si se falla la validacion se lanza Exception con los motivos
     *
     * @param object
     * @return
     * @throws BadRequestException
     */
    public boolean validarModelo(Object object) throws BadRequestException {
        // El validador automáticamente valida las restricciones anotadas en el modelo
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            // Si hay violaciones, construimos el mensaje de error
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<Object> violation : violations) {
                errors.append(violation.getMessage()).append("\n");
            }
            // Lanzamos la excepción con los errores encontrados
            throw new BadRequestException(errors.toString());
        }

        return true;
    }

    /**
     * Extrae la lista de errores de una validacion hecha
     *
     * @param validaciones
     * @return
     */
    private String extraerErrores(Set<ConstraintViolation<Object>> validaciones) {
        String fallas = "";
        for (ConstraintViolation<Object> item : validaciones) {
            fallas += item.getMessage().concat("\n");
        }
        return fallas;
    }

    /**
     * Valida el estado de un solo atributo de un modelo.
     *
     * @param objeto
     * @param attributeName
     * @return
     * @throws Exception
     */
    public boolean validarAtributo(Object objeto, String attributeName) throws BadRequestException {
        Set<ConstraintViolation<Object>> validaciones = validator.validateProperty(
                objeto, attributeName);
        if (!validaciones.isEmpty()) {
            throw new BadRequestException(extraerErrores(validaciones));
        }
        return true;
    }

    /**
     * Valida si el id del objeto no es nulo o menor a cero, si lo es entonces
     * lanzaa una excepcion.
     *
     * @param <T>
     * @param entidad entidad que extiende de auditor
     * @throws Exception
     */
    public <T extends Auditor> void validarId(T entidad, String mensaje) throws BadRequestException {
        if (entidad == null || entidad.getId() == null || entidad.getId() <= 0) {
            throw new BadRequestException(mensaje);
        }
    }

    /**
     * Verifica si el objeto tiene deleted = null
     *
     * @param <T> Tipo que extiende de Auditor
     * @param entidad Objeto que extiende de Auditor
     */
    public <T extends Auditor> void isDeleted(T entidad) throws NotFoundException {
        if (entidad.getDeletedAt() != null) {
            throw new NotFoundException("Informacion no encontrada.");
        }
    }
}
