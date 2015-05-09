package com.yuzhou.cloud.controller;

import java.util.List;

import org.openstack4j.model.compute.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuzhou.cloud.openstack.ReturnObject;
import com.yuzhou.cloud.openstack.api.FlavorService;
import com.yuzhou.cloud.openstack.api.IPService;
import com.yuzhou.cloud.openstack.api.ImageService;
import com.yuzhou.cloud.openstack.api.ServerService;
import com.yuzhou.cloud.openstack.api.TenantService;

@Controller
@RequestMapping("/ocp/vm")
public class VMController {

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

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody List<Server> listVM() {

		List<Server> servers = (List<Server>) serverService.list();

		return servers;

	}

	@RequestMapping(value = "simplelist", method = RequestMethod.GET)
	public @ResponseBody List<Server> simpleListVM() {

		List<Server> servers = (List<Server>) serverService.simpleList();

		return servers;

	}

	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public @ResponseBody Server getVM(@PathVariable String name) {
		return serverService.getById(name);
	}

	@RequestMapping(value = "{name}", method = RequestMethod.DELETE)
	public @ResponseBody ReturnObject deleteVM(@PathVariable String name) {
		// TODO
		return new ReturnObject();

	}

	@RequestMapping(value = "{name}", method = RequestMethod.POST)
	public @ResponseBody Server addVM(@PathVariable String name,
			@RequestBody String vmJson) {
		// TODO
		return null;

	}

	@RequestMapping(value = "{name}", method = RequestMethod.PUT)
	public @ResponseBody Server updateVM(@PathVariable String name,
			@RequestBody String vmJson) {

		// TODO
		return null;

	}

}