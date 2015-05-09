package com.yuzhou.cloud.controller;

import java.util.List;

import org.openstack4j.model.compute.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuzhou.cloud.openstack.api.FlavorService;
import com.yuzhou.cloud.openstack.api.IPService;
import com.yuzhou.cloud.openstack.api.ImageService;
import com.yuzhou.cloud.openstack.api.ServerService;
import com.yuzhou.cloud.openstack.api.TenantService;

@Controller
@RequestMapping("/ocp/image")
public class ImageController {

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
	public @ResponseBody List<Image> list() {

		return (List<Image>) imageService.list();

	}

}