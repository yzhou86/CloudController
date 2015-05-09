package com.yuzhou.cloud.utils;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class TestBase {

	public static void main(String[] args) throws InterruptedException {
		String endpoint = "https://keystoneservicehavana.qa.webex.com/v2.0";

		OSClient os = OSFactory.builder().endpoint(endpoint)
				.credentials("TTAP", "TTAP").tenantName("TTAP").authenticate();
		os.getSupportedServices();
		System.out.println(os.compute().servers().list(true).size());
		System.out.println(os.compute().flavors().list().size());
		System.out.println(os.compute().images().list().size());
	}

}
