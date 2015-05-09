package com.yuzhou.cloud.openstack.api;

import java.util.List;

import org.openstack4j.model.identity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.OpenStackClient;

/**
 * 
 * Tenant service
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class TenantService {

	@Autowired
	private OpenStackClient openStackClient;

	public List<? extends Tenant> list() {
		List<? extends Tenant> tenants = openStackClient.getOs().identity()
				.tenants().list();
		return tenants;
	}

	public Tenant getById(String id) {
		Tenant tenant = openStackClient.getOs().identity().tenants().get(id);
		return tenant;
	}

	public Tenant getByName(String name) {
		Tenant tenant = openStackClient.getOs().identity().tenants()
				.getByName(name);
		return tenant;
	}

}
