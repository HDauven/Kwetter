package com.kwetter.JSF;

import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hein on 5/28/17.
 */
@Named
@Dependent
public class AuthBean {

    public void doLogout() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public boolean isNotLoggedIn() {
        return !isLoggedIn();
    }

    public String getPrincipalName() {
        if (isLoggedIn()) {
            return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        } else {
            return "ANONYMOUS";
        }
    }

    public boolean isAdmin() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().isUserInRole("AdminRole");
    }
}
