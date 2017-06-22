package com.kwetter.exception;

import com.kwetter.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by hein on 6/4/17.
 */
@Provider
public class UserUnauthorizedExceptionMapper implements ExceptionMapper<UserUnauthorizedException> {

    @Override
    public Response toResponse(UserUnauthorizedException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 401, "");
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorMessage)
                .build();
    }
}
