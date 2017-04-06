package tn.wevioo.model.authentication;

/**
 * The enumeration UserRole defines all the possible roles a user can be assigned to. These roles allows the Packager
 * Core to have different behaviors depending on the role of the currently authenticated user. For the moment, the
 * defined values are:
 * <ul>
 * <li>WS_USER (level=10): This is the default user role.</li>
 * <li>ADMIN (level=100): This is the administrator user role.</li>
 * </ul>
 * 
 * @author bflorea
 * @since 2.0.0
 */
public enum UserRole {

	/**
	 * This is the default user role.
	 */
	WS_USER(10L),

	/**
	 * This is the administrator user role.
	 */
	ADMIN(100L);

	/**
	 * Level corresponding to the user's role. The higher the level, the more rights for the user. Level can be used to
	 * compare two levels and see which one is higher.
	 */
	private final Long level;

	/**
	 * Default constructor.
	 * 
	 * @param level
	 *            Level to associate to the user role. Cannot be null.
	 */
	private UserRole(Long level) {
		this.level = level;
	}

	public Long level() {
		return level;
	}
}