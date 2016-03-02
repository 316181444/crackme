package com.crack.me.active;

import com.jniwrapper.util.ProcessorInfo;
import java.net.InetAddress;

public class SystemIdFactory {
	public static final int HD_FIELD_LENGTH = "0000".length();

	public String generateHostId() {
		String hostName = null;
		try {
			InetAddress localInetAddress = InetAddress.getLocalHost();
			hostName = localInetAddress.getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int hashCode = (hostName == null) || ("".equals(hostName)) ? 0 : Math
				.abs(hostName.hashCode());
		String hostId = String.format("%02X", new Object[] { new Integer(
				hashCode % 256) });
		return hostId;
	}

	public String generateSystemInfoId() throws Exception {
		ProcessorInfo pi = null;
		try {
			pi = ProcessorInfo.getInstance();
		} catch (Throwable e) {
			pi = ProcessorInfo.getInstance();
		}
		if (pi != null) {
			String familyId = Integer.toHexString(pi.getFamily() % 16);
			String modelId = Integer.toHexString(pi.getModel() % 16);
			String stepId = Integer.toHexString(pi.getStepping() % 16);
			String frequencyId = String.format("%04X", new Object[] { new Long(
					pi.getFrequency() % 65536L) });
			return familyId + modelId + stepId + frequencyId;
		}
		return "0000";
	}

	public String generateMacAddress() {
		return new MACAddressUtil().getMacAddress();
	}

	public String generateHDId() {
		String home = System.getenv("HOME");
		String user = System.getenv("USER");
		String logname = System.getenv("LOGNAME");
		String shell = System.getenv("SHELL");
		String currentUse = null;
		if (home != null) {
			currentUse = home;
		} else if (user != null) {
			currentUse = user;
		} else if (logname != null) {
			currentUse = logname;
		} else if (shell != null) {
			currentUse = shell;
		} else {
			currentUse = "0000";
		}
		String hdId = Integer.toString(Math.abs(currentUse.hashCode()));
		int length = currentUse.length();
		if (length > HD_FIELD_LENGTH) {
			hdId = hdId.substring(0, HD_FIELD_LENGTH);
		} else if (length < HD_FIELD_LENGTH)
			hdId = hdId + "FEED".substring(0, HD_FIELD_LENGTH - length);
		return hdId;
	}

	public boolean isNodeLocked() {
		return true;
	}

	public String generateSystemId() throws Exception {
		int i = isNodeLocked() ? 1 : 2;
		int j = 0;
		j++;
		j += 2;
		j += 4;
		j += 8;

		return Integer.toString(i, 16) + Integer.toString(j, 16)
				+ generateHostId() + generateSystemInfoId()
				+ generateMacAddress() + generateHDId();
	}
}
