package com.crack.me.active.ui.core;

import com.crack.me.active.ui.Loggable;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class MeReplacer {
	private Loggable log = null;

	private List<JarReplacer> replacers = null;

	public MeReplacer(Loggable log) {
		this.log = log;
		initReplacers();
	}

	public void initReplacers() {
		this.replacers = new ArrayList<JarReplacer>(5);
		this.replacers.add(new SignatureVerifierReplacer(this.log));
		this.replacers.add(new PublicKeyBytesReplacer(this.log));
	}

	public void addReplacer(JarReplacer replacer) {
		if (replacer != null) {
			this.replacers.add(replacer);
		}
	}

	public boolean replace(File fileFolder) {
		List<File> allFiles = findFiles(fileFolder);
		return replace(allFiles);
	}

	private boolean replace(List<File> allFiles) {
		if ((this.replacers == null) || (allFiles == null)) {
			return false;
		}
		boolean success = true;
		for (JarReplacer rp : this.replacers) {
			this.log.log(rp.getProcessInfo());
			success &= rp.replace(allFiles);
		}
		return success;
	}

	private List<File> findFiles(File file) {
		this.log.log("finding files in [" + file.getAbsolutePath() + "]");
		return findFiles(file, JarReplacer.getFileFilter());
	}

	private List<File> findFiles(File file, FileFilter fileFliter) {
		if (!file.exists()) {
			return null;
		}

		if (file.isFile()) {
			List<File> tmpFiles = new ArrayList<File>(1);
			tmpFiles.add(file);
			return tmpFiles;
		}

		File[] files = file.listFiles(fileFliter);
		if ((files == null) || (files.length == 0)) {
			return null;
		}
		List<File> fff = new ArrayList<File>(100);
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = files).length;
		for (int i = 0; i < j; i++) {
			File f = arrayOfFile1[i];
			List<File> tmpFile = findFiles(f, fileFliter);
			if (tmpFile != null) {
				fff.addAll(tmpFile);
			}
		}
		return fff;
	}
}
