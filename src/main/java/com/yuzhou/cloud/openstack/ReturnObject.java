package com.yuzhou.cloud.openstack;

/**
 * 
 * Common return object
 * 
 * @author Yu Zhou
 *
 */
public class ReturnObject {

	private boolean result = true;

	private String message = "";

	public ReturnObject() {
		this.result = true;
		this.message = "";
	}

	public ReturnObject(boolean res, String msg) {
		this.result = res;
		this.message = msg;
	}

	public void appendMessage(String msg) {
		message = message + "." + msg;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
