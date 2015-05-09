package com.yuzhou.cloud.openstack;

/**
 * 
 * Common exception class
 * 
 * @author Yu Zhou
 *
 */
public class CloudException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5577186249842634333L;

	public CloudException() {
	}

	public CloudException(String message) {
		super(message);
	}
}