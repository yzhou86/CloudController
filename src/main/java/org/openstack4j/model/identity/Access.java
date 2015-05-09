package org.openstack4j.model.identity;

import java.util.List;

import org.openstack4j.api.types.ServiceType;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.Link;

/**
 * Access is the entity returned when Authenticated by the Identity service
 * 
 * @author Jeremy Unruh
 */
public interface Access extends ModelEntity {

	/**
	 * @return the authorized token
	 */
	Token getToken();

	/**
	 * @return the service catalog
	 */
	List<? extends Service> getServiceCatalog();

	/**
	 * @return details about the current user
	 */
	UserDetails getUser();

	public interface UserDetails {
		String getId();

		String getName();

		String getUsername();

		boolean isEnabled();

		List<? extends Role> getRoles();

		List<? extends Link> getRolesLinks();
	}

	public interface Service {
		String getType();

		String getName();

		ServiceType getServiceType();

		List<? extends Endpoint> getEndpoints();

		List<? extends Link> getEndpointsLinks();
	}
}
