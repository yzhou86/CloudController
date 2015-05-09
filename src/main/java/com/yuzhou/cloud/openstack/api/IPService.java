package com.yuzhou.cloud.openstack.api;

import java.util.List;

import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.OpenStackClient;

/**
 * 
 * IP service
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class IPService {

	@Autowired
	private OpenStackClient openStackClient;

	public List<String> listPoolNames() {
		List<String> pools = openStackClient.getOs().compute().floatingIps()
				.getPoolNames();
		return pools;
	}

	public List<? extends FloatingIP> listFloatingIps() {
		List<? extends FloatingIP> ips = openStackClient.getOs().compute()
				.floatingIps().list();
		return ips;
	}

	public FloatingIP allocateIP(String poolName) {
		FloatingIP ip = openStackClient.getOs().compute().floatingIps()
				.allocateIP(poolName);
		return ip;
	}

	public void deallocateIP(String floatingIpId) {
		openStackClient.getOs().compute().floatingIps()
				.deallocateIP(floatingIpId);
	}

	public ActionResponse addFloatingIP(Server server, String fixedIp,
			String floatingIp) {
		ActionResponse r = openStackClient.getOs().compute().floatingIps()
				.addFloatingIP(server, fixedIp, floatingIp);
		return r;
	}

	public ActionResponse removeFloatingIP(Server server, String floatingIp) {
		ActionResponse r = openStackClient.getOs().compute().floatingIps()
				.removeFloatingIP(server, floatingIp);
		return r;
	}

}
