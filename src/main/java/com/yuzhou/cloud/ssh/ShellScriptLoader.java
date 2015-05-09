package com.yuzhou.cloud.ssh;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * 
 * Load defined shell scripts from resource files
 * 
 * @author Yu Zhou
 *
 */
public class ShellScriptLoader {
	public static final String CONFIG = "scripts/config_service.sh";
	public static final String INSTALL = "scripts/install_service.sh";
	public static final String KEEPRUN = "scripts/keep_run_service.sh";
	public static final String VERIFY = "scripts/verify_service.sh";
	public static final String IDLE = "scripts/check_idle_service.sh";

	/**
	 * Get content of file
	 * 
	 * @param file
	 * @return String
	 */
	public String getScriptContent(String file) {
		try {
			InputStream in = getClass().getResourceAsStream(file);
			return IOUtils.toString(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
