package com.yuzhou.cloud.openstack;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 
 * Load configuration items from cloud.properties
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class CloudConfig {

	@Value("${openstack.url.mapping}")
	private String suffixUrlMapping = "/v2/e33e65c9cb7d46399f67ddf498fdd40c,https://novaapihavana.qa.webex.com:443/v2/e33e65c9cb7d46399f67ddf498fdd40c;/v2,https://keystoneservicehavana.qa.webex.com:443/v2.0";

	public Map<String, String> getUrlBySuffix() {
		if (suffixUrlMapping == null || suffixUrlMapping.length() == 0) {
			return null;
		} else {
			Map<String, String> urlMaps = new HashMap();
			String[] mappings = suffixUrlMapping.split(";");
			for (String mapping : mappings) {
				String[] maps = mapping.split(",");
				urlMaps.put(maps[0], maps[1]);
			}
			return urlMaps;
		}
	}

	@Value("${openstack.flavor.name}")
	private String flavorId;

	public String getFlavorId() {
		return flavorId;
	}

	@Value("${openstack.image.name}")
	private String imageId;

	public String getImageId() {
		return imageId;
	}

	@Value("${openstack.vm.number.init}")
	private String vmNumberInit;

	public int getVmNumberInit() {
		return Integer.parseInt(vmNumberInit);
	}

	@Value("${openstack.endpoint}")
	private String endpoint;

	public String getEndpoint() {
		return endpoint;
	}

	@Value("${openstack.tenant}")
	private String tenant;

	public String getTenant() {
		return tenant;
	}

	@Value("${openstack.user}")
	private String user;

	public String getUser() {
		return user;
	}

	@Value("${openstack.pass}")
	private String pass;

	public String getPass() {
		return pass;
	}

	@Value("${openstack.vm.user}")
	private String vmUser;

	public String getVmUser() {
		return vmUser;
	}

	@Value("${openstack.vm.pass}")
	private String vmPass;

	public String getVmPass() {
		return vmPass;
	}

	@Value("${openstack.vm.ssh.port}")
	private String vmPort;

	public String getVmPort() {
		return vmPort;
	}

}
