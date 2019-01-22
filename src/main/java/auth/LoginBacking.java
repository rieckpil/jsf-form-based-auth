package auth;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author vasou
 */
@Named
@RequestScoped
public class LoginBacking {

    @NotEmpty
    @Size(min = 4, message = "Password must have at least 8 characters")
    private String password;

    @NotEmpty
    @Email(message = "Please provide a valid e-mail")
    private String email;

    @Inject
    private SecurityContext securityContext;

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();

    public void submit() throws IOException {

        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                break;
            case SUCCESS:
                System.out.println(securityContext.getCallerPrincipal() + " is admin: " + securityContext.isCallerInRole("ADMIN"));
                System.out.println(securityContext.getCallerPrincipal() + " is user: " + securityContext.isCallerInRole("USER"));
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeded", null));
                facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/app/index.xhtml");
                break;
            case NOT_DONE:
        }

    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams()
                        .credential(new UsernamePasswordCredential(email, password)));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
