package com.yuzhou.cloud.openstack;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.identity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.api.FlavorService;
import com.yuzhou.cloud.openstack.api.IPService;
import com.yuzhou.cloud.openstack.api.ImageService;
import com.yuzhou.cloud.openstack.api.ServerService;
import com.yuzhou.cloud.openstack.api.TenantService;

/**
 * 
 * Main program for this project, cluster service is called by external
 * component to controll openstack
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class ClusterService {

	private int size = 0;

	private Tenant tenant = null;

	private Flavor flavor = null;

	private Image image = null;

	private String namePrefix = "TTAP-AnalysisPlatform-";

	private int activeCount = 0;

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
	@Autowired
	private CloudConfig cloudConfig;

	@SuppressWarnings("serial")
	private List<Server> allServers = new ArrayList<Server>() {
	};

	public int getSize() {
		return size;
	}

	@SuppressWarnings("unchecked")
	public void buildServers(int count) throws CloudException {
		List<FloatingIP> floatIps = (List<FloatingIP>) ipService
				.listFloatingIps();
		if (floatIps == null || floatIps.size() < count) {
			throw new CloudException("Floating IPs not enough.");
		}
		for (int i = 0; i < count; i++) {
			Server newServer = serverService.build(namePrefix
					+ ClusterNamePool.instance().getName(),
					cloudConfig.getFlavorId(), cloudConfig.getImageId());
			serverService.start(newServer);
		}
		allServers = (List<Server>) serverService.list();
	}

	public void startServers(int count) {
		allServers = (List<Server>) serverService.list();
		int number = 0;
		for (Server server : allServers) {
			if (number == count) {
				break;
			}
			if (!Status.ACTIVE.equals(server.getStatus())) {
				serverService.start(server);
				number++;

			}
		}
	}

	public void stopServers(int count) {
		allServers = (List<Server>) serverService.list();
		int number = 0;
		for (Server server : allServers) {
			if (number == count) {
				break;
			}
			if (Status.ACTIVE.equals(server.getStatus())
					&& isServerIdle(server)) {
				serverService.stop(server);
				number++;
			}
		}
	}

	public boolean isServerIdle(Server server) {
		// TODO check if server is idle
		return false;
	}

	public void startAllServers() {
		allServers = (List<Server>) serverService.list();
		for (Server server : allServers) {
			if (!Status.ACTIVE.equals(server.getStatus())) {
				serverService.start(server);
			}
		}
	}

	public int refreshActiveCount() {
		activeCount = 0;
		allServers = (List<Server>) serverService.list();
		for (Server server : allServers) {
			if (Status.ACTIVE.equals(server.getStatus())) {
				activeCount++;
			}
		}
		return activeCount;
	}

	/**
	 * call this method to initialize openstack VM cluster, prepare and start up
	 * required VMs
	 * 
	 * @return ReturnObject
	 */
	@SuppressWarnings("unchecked")
	public ReturnObject init() {
		ReturnObject ro = new ReturnObject();
		try {
			// TODO initialize cluster, start up necessary servers
			size = cloudConfig.getVmNumberInit();

			tenant = tenantService.getByName(cloudConfig.getTenant());
			if (tenant == null) {
				throw new CloudException("Tenant '" + cloudConfig.getTenant()
						+ "' not found.");
			}

			flavor = flavorService.getById(cloudConfig.getFlavorId());
			if (flavor == null) {
				throw new CloudException("Flavor '" + cloudConfig.getFlavorId()
						+ "' not found.");
			}

			image = imageService.get(cloudConfig.getImageId());
			if (image == null) {
				throw new CloudException("Image '" + cloudConfig.getImageId()
						+ "' not found.");
			}

			allServers = (List<Server>) serverService.list();
			this.refreshActiveCount();

			reSizeCluster(size);

		} catch (Exception e) {
			ro.setResult(false);
			ro.setMessage(e.getMessage());
		}

		return ro;
	}

	/**
	 * change cluster VMs to match size request, build and start more VM, or
	 * stop some VM
	 * 
	 * @param newSize
	 *            : the size of active VM excepted
	 * @return ReturnObject: the deal result
	 */
	public ReturnObject reSizeCluster(int newSize) {
		ReturnObject ro = new ReturnObject();
		// TODO resize the cluster, scale up or scale down
		try {
			allServers = (List<Server>) serverService.list();
			int realSize = allServers == null ? 0 : allServers.size();
			this.refreshActiveCount();
			ro.appendMessage("Current VM count is " + realSize + ".");
			if (realSize >= size) {
				if (this.activeCount >= size) {
					ro.appendMessage("Have to stop servers number is "
							+ (this.activeCount - size) + ".");
					stopServers(this.activeCount - size);
				} else {
					ro.appendMessage("Have to start servers number is "
							+ (size - activeCount) + ".");
					startServers(size - activeCount);
				}

			} else {
				ro.appendMessage("Have to build servers number is "
						+ (size - realSize) + ".");
				int serverCount = size - realSize;
				buildServers(serverCount);
				// and start all servers
				startAllServers();
			}
		} catch (Exception e) {
			ro.setResult(false);
			ro.appendMessage("error:" + e.getMessage());
		}
		return ro;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
