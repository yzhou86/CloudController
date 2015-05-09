package com.yuzhou.cloud.openstack;

import java.util.HashMap;

/**
 * 
 * A name pool, for cluster service to auto name new build VM
 * 
 * @author Yu Zhou
 *
 */
public final class ClusterNamePool {

	private static ClusterNamePool instance = null;

	private static int nameSize = 1024;

	private static HashMap<Integer, Boolean> nameMap = null;

	private ClusterNamePool() {
		nameMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < nameSize; i++) {
			nameMap.put(i, false);
		}
	}

	public int getName() throws CloudException {
		for (int i = 0; i < nameSize; i++) {
			if (!nameMap.get(i)) {
				nameMap.put(i, true);
				return i;
			}
		}
		throw new CloudException("no name space.");
	}

	public void releaseName(int j) {
		nameMap.put(j, false);
	}

	public static ClusterNamePool instance() {
		if (instance == null) {
			instance = new ClusterNamePool();
		}
		return instance;

	}
}
