package com.yuzhou.cloud.openstack.api;

import java.util.List;

import org.openstack4j.model.compute.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuzhou.cloud.openstack.OpenStackClient;

/**
 * 
 * Image service
 * 
 * @author Yu Zhou
 *
 */
@Repository
public class ImageService {

	@Autowired
	private OpenStackClient openStackClient;

	public List<? extends Image> list() {
		List<? extends Image> images = openStackClient.getOs().compute()
				.images().list();
		return images;
	}

	public Image get(String id) {
		Image img = openStackClient.getOs().compute().images().get(id);
		return img;
	}

}
