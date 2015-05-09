package com.yuzhou.cloud.ssh;

import org.openstack4j.model.compute.Server;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzhou.cloud.openstack.CloudConfig;
import com.yuzhou.cloud.openstack.ReturnObject;

/**
 * 
 * Service controller to manage services on VMs (execute some commands to
 * detect/maintain/install/etc.)
 * 
 * @author Yu Zhou
 *
 */
public class ServiceController {
	@Autowired
	private CloudConfig cloudConfig;

	/**
	 * Check whether this server has no job to do at present (is idle)
	 * 
	 * @param server
	 * @return boolean
	 */
	public boolean checkServiceIsIdle(Server server) {
		ReturnObject ro = ShellScriptExecutor.execute(server, Integer
				.valueOf(cloudConfig.getVmPort()), cloudConfig.getVmUser(),
				cloudConfig.getVmPass(), new ShellScriptLoader()
						.getScriptContent(ShellScriptLoader.IDLE));

		return ro.getResult();
	}
}
