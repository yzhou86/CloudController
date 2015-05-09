package com.yuzhou.cloud.openstack;

import org.apache.log4j.Logger;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.utils.CloudUtils;

/**
 * 
 * A openstack client for connect to openstack tenant
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class OpenStackClient {
	private static Logger LOGGER = Logger.getLogger(OpenStackClient.class);

	@Autowired
	private CloudConfig cloudConfig;

	public OSClient getOs() {
		LOGGER.info(cloudConfig.getEndpoint());
		LOGGER.info(cloudConfig.getUser());
		LOGGER.info(cloudConfig.getPass());
		LOGGER.info(cloudConfig.getTenant());
		OSClient os = null;
		CloudUtils.trustSelfSignedSSL();
		try {
			os = OSFactory.builder().endpoint(cloudConfig.getEndpoint())
					.credentials(cloudConfig.getUser(), cloudConfig.getPass())
					.tenantName(cloudConfig.getTenant()).authenticate();
		} catch (Exception e) {
			LOGGER.error("Initialize openstack connect error:", e);
			throw e;
		}
		return os;
	}

}
