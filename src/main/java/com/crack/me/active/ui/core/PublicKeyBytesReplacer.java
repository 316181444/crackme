package com.crack.me.active.ui.core;

import com.crack.me.active.ui.Loggable;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PublicKeyBytesReplacer extends JarReplacer {
	private Loggable log = null;

	private String defaultFileName = "publicKey.bytes";

	public PublicKeyBytesReplacer(Loggable log) {
		this.log = log;
	}

	public boolean replace(File f) {
		return false;
	}

	public boolean isFileLegal(File f) {
		if (f == null) {
			return false;
		}

		String fn = f.getName();
		if (fn.startsWith("com.genuitec.eclipse.core_")) {
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(f);
				Enumeration<? extends ZipEntry> zes = zipFile.entries();
				while (zes.hasMoreElements()) {
					ZipEntry ze = (ZipEntry) zes.nextElement();
					String name = ze.getName();
					if (name.endsWith(this.defaultFileName)) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					if (zipFile != null) {
						zipFile.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public String getProcessInfo() {
		return "Replacing [publicKey.bytes].";
	}
}
