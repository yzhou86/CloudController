package com.yuzhou.cloud.openstack.api;

import java.util.List;
import java.util.Map;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeService;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.OpenStackClient;

/**
 * 
 * Server service
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class ServerService {

	@Autowired
	private OpenStackClient openStackClient;

	public List<? extends Server> list() {

		OSClient os = openStackClient.getOs();
		ComputeService computeService = os.compute();
		org.openstack4j.api.compute.ServerService service = computeService
				.servers();
		List<? extends Server> servers = service.list();
		return servers;
	}

	public List<? extends Server> simpleList() {

		OSClient os = openStackClient.getOs();
		ComputeService computeService = os.compute();
		org.openstack4j.api.compute.ServerService service = computeService
				.servers();
		List<? extends Server> servers = service.list(false);
		return servers;
	}

	public Server getById(String id) {
		return openStackClient.getOs().compute().servers().get(id);
	}

	public void delete(String id) {
		openStackClient.getOs().compute().servers().delete(id);
	}

	public Map<String, ? extends Number> diagnostics(String id) {
		Map<String, ? extends Number> diagnostics = openStackClient.getOs()
				.compute().servers().diagnostics(id);
		return diagnostics;
	}

	public Server build(String name, String flavor, String image) {
		// Create a Server Model Object
		ServerCreate sc = Builders.server().name(name).flavor(flavor)
				.image(image).build();

		// Boot the Server
		Server server = openStackClient.getOs().compute().servers().boot(sc);

		return server;
	}

	public ActionResponse start(Server server) {
		ActionResponse r = openStackClient.getOs().compute().servers()
				.action(server.getId(), Action.START);
		return r;
	}

	public ActionResponse stop(Server server) {
		ActionResponse r = openStackClient.getOs().compute().servers()
				.action(server.getId(), Action.STOP);
		return r;
	}

	public ActionResponse reboot(Server server) {
		ActionResponse r = openStackClient.getOs().compute().servers()
				.reboot(server.getId(), RebootType.SOFT);
		return r;
	}

}
