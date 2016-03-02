package com.crack.me.active.ui.core;

import com.crack.me.active.ui.Loggable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SignatureVerifierReplacer extends JarReplacer {
	private List<String> legalFileList = null;
	public SignatureVerifierReplacer(Loggable log) {
		this.legalFileList = new ArrayList<String>();
		this.legalFileList.add("/SignatureVerifier.class");
		this.legalFileList.add("/SignatureVerifier$1.class");
		this.legalFileList.add("/SignatureVerifier$1$1.class");
	}

	public boolean isInLegalFile(String fileName) {
		for (String cn : this.legalFileList) {
			if (fileName.endsWith(cn)) {
				return true;
			}
		}
		return false;
	}

	public boolean replace(File f) {
		return false;
	}

	public boolean isFileLegal(File f) {
		boolean isFoundSV = false;
		boolean isFoundSV$1 = false;
		boolean isFoundSV$1$1 = false;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(f);
			Enumeration<? extends ZipEntry> zes = zipFile.entries();
			while (zes.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) zes.nextElement();
				String name = ze.getName();
				if (name.endsWith("/SignatureVerifier.class")) {
					isFoundSV = true;
				}
				if (name.endsWith("/SignatureVerifier$1.class")) {
					isFoundSV$1 = true;
				}
				if (name.endsWith("/SignatureVerifier$1$1.class")) {
					isFoundSV$1$1 = true;
				}
			}

			if ((isFoundSV) && (isFoundSV$1) && (isFoundSV$1$1)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		return false;
	}

	public String getProcessInfo() {
		return "Replacing [SignatureVerifier].";
	}
}
