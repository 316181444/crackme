package com.crack.me.active;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MACAddressUtil {
	String macAddress = null;
	String computeMacAddress = null;

	private static final Pattern HARDWARE_PATTERN = Pattern
			.compile(
					"(.*wireless.*)|(.*tunnel.*)|(.*atapi.*)|(.*bluetooth.*)|(.*vnic.*)|(.*vmnet.*)",
					2);

	public String getMacAddressWithNetworkInterface() {
		String computeMacAddress = null;
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) networkInterfaces
						.nextElement();

				if ((ni != null) && (!ni.isVirtual()) && (!ni.isLoopback())
						&& (ni.isUp())) {
					byte[] hardwareAddress = ni.getHardwareAddress();

					if ((hardwareAddress != null)
							&& (hardwareAddress.length != 2)) {

						boolean isMacAddressLegal = false;
						byte[] arrayOfByte1;
						int j = (arrayOfByte1 = hardwareAddress).length;
						for (int i = 0; i < j; i++) {
							byte b = arrayOfByte1[i];
							if (b <= 0) {
								isMacAddressLegal = true;
								break;
							}
						}

						if (isMacAddressLegal) {
							String hardwareName = ni.getDisplayName();
							if ((hardwareName != null)
									&& (hardwareName.length() != 0)) {
								Matcher matcher = HARDWARE_PATTERN
										.matcher(hardwareName);
								if (!matcher.lookingAt()) {
									computeMacAddress = String
											.format("%02x%02x",
													new Object[] {

															Byte.valueOf(hardwareAddress[(hardwareAddress.length - 2)]),
															Byte.valueOf(hardwareAddress[(hardwareAddress.length - 1)]) });

									this.macAddress = buildMacAddress(hardwareAddress);
								}
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return computeMacAddress == null ? "0000" : computeMacAddress;
	}

	public String buildMacAddress(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		if (bytes != null) {
			byte[] arrayOfByte;
			int j = (arrayOfByte = bytes).length;
			for (int i = 0; i < j; i++) {
				byte b = arrayOfByte[i];
				sb.append(String.format("%02x",
						new Object[] { Byte.valueOf(b) }) + ":");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String executeCommand(String[] commands) {
		return null;
	}

	public String getMacAddressWithOS() {
		return null;
	}

	public String macAddressParser(String line) {
		String out = line;

		int hexStart = out.indexOf("0x");
		if ((hexStart != -1) && (out.indexOf("ETHER") != -1)) {
			int hexEnd = out.indexOf(' ', hexStart);
			if (hexEnd > hexStart + 2) {
				out = out.substring(hexStart, hexEnd);
			}
		} else {
			int octets = 0;

			if (out.indexOf('-') > -1) {
				out = out.replace('-', ':');
			}
			int lastIndex = out.lastIndexOf(':');
			if (lastIndex > out.length() - 2) {
				out = null;
			} else {
				int end = Math.min(out.length(), lastIndex + 3);
				octets++;
				int old = lastIndex;
				while ((octets != 5) && (lastIndex != -1) && (lastIndex > 1)) {
					lastIndex = out.lastIndexOf(':', --lastIndex);
					if ((old - lastIndex == 3) || (old - lastIndex == 2)) {
						octets++;
						old = lastIndex;
					}
				}
				if ((octets == 5) && (lastIndex > 1)) {
					out = out.substring(lastIndex - 2, end).trim();
				} else {
					out = null;
				}
			}
		}

		if ((out != null) && (out.startsWith("0x"))) {
			out = out.substring(2);
		}

		return out;
	}

	public String getMacAddress() {
		if (this.computeMacAddress != null) {
			return this.computeMacAddress;
		}
		String macAddr = getMacAddressWithNetworkInterface();
		if ((macAddr == null) || ("0000".equals(macAddr))) {
			macAddr = getMacAddressWithOS();
		}
		this.computeMacAddress = macAddr;
		return macAddr;
	}
}
