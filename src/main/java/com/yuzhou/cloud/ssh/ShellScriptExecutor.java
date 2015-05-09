package com.yuzhou.cloud.ssh;

import java.io.InputStream;

import org.openstack4j.model.compute.Server;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.yuzhou.cloud.openstack.ReturnObject;

/**
 * 
 * Connect to VM via SSH channel and execute shell script
 * 
 * @author Yu Zhou
 *
 */
public class ShellScriptExecutor {

	/**
	 * Execute script on server, and return execute result
	 * 
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @param script
	 * @return ReturnObject
	 */
	public static ReturnObject execute(Server server, int port,
			String username, String password, String script) {
		ReturnObject ro = new ReturnObject();
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, server.getAccessIPv4(),
					port);
			session.setPassword(password);
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(script);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();

			StringBuffer response = new StringBuffer("");

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					response.append(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					int exitStatus = channel.getExitStatus();
					response.append("exit-status: " + exitStatus);
					ro.setResult(exitStatus == 0);
					break;
				}
				Thread.sleep(1000);

			}
			channel.disconnect();
			session.disconnect();
			ro.setMessage(response.toString());
		} catch (Exception e) {
			ro.setResult(false);
			ro.setMessage(e.getMessage());
		}

		return ro;
	}
}
