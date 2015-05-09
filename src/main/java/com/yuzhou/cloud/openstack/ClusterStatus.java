package com.yuzhou.cloud.openstack;

import java.util.List;

import org.openstack4j.model.compute.Server;

public class ClusterStatus {

	public int serverCount;

	public int activeServerCount;

	public List<Server> allServers;

}
