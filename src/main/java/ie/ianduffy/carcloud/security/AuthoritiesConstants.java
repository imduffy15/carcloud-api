package ie.ianduffy.carcloud.security;

/**
 * Constants for Spring Security authorities.
 *
 * SPAM 0.0.1 - Usage of constants
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
