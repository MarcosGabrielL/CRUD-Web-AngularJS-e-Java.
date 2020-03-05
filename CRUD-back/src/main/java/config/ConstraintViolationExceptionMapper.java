package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> erros = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        for(ConstraintViolation erro : constraintViolations) {
            String mensagem = erro.getMessage();
            erros.add(mensagem);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erros).build();
    }

}
