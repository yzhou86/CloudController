package com.yuzhou.cloud.controller;

import java.util.List;

import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuzhou.cloud.openstack.ClusterStatus;
import com.yuzhou.cloud.openstack.api.FlavorService;
import com.yuzhou.cloud.openstack.api.IPService;
import com.yuzhou.cloud.openstack.api.ImageService;
import com.yuzhou.cloud.openstack.api.ServerService;
import com.yuzhou.cloud.openstack.api.TenantService;

@Controller
@RequestMapping("/cluster")
public class ClusterController {

	@Autowired
	private FlavorService flavorService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private IPService ipService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private TenantService tenantService;

	@RequestMapping(value = "status", method = RequestMethod.GET)
	public @ResponseBody ClusterStatus status() {
		ClusterStatus clusterStatus = new ClusterStatus();
		List<Server> servers = (List<Server>) serverService.list();
		clusterStatus.allServers = servers;
		clusterStatus.serverCount = servers.size();
		int activeCount = 0;
		for (Server s : servers) {
			if (Status.ACTIVE.equals(s.getStatus())) {
				activeCount++;
			}
		}
		clusterStatus.activeServerCount = activeCount;

		return clusterStatus;

	}

}