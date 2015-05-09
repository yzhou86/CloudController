package com.yuzhou.cloud.openstack.api;

import java.util.List;

import org.openstack4j.model.compute.Flavor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.OpenStackClient;

/**
 * 
 * Flavor service
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class FlavorService {

	@Autowired
	private OpenStackClient openStackClient;

	public List<? extends Flavor> list() {
		List<? extends Flavor> flavors = openStackClient.getOs().compute()
				.flavors().list();
		return flavors;
	}

	public Flavor getById(String id) {
		return openStackClient.getOs().compute().flavors().get(id);
	}

}
