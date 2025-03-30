package cst8218.n041092398.lab4;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@BasicAuthenticationMechanismDefinition
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "${'java:app/lab4JNDI'}",
    callerQuery = "#{'select password from appuser where userid = ?'}",
    groupsQuery = "select groupname from appuser where userid = ?",
    hashAlgorithm = PasswordHash.class,
    priority = 10
)
/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@Named
@ApplicationScoped
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    
}

