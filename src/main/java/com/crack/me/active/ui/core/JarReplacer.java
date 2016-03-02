package com.crack.me.active.ui.core;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public abstract class JarReplacer {
	public static final FileFilter jarFileFilter = new FileFilter() {
		public boolean accept(File paramFile) {
			return (paramFile.isDirectory())
					|| (paramFile.getName().endsWith(".jar"));
		}
	};

	public boolean replace(List<File> fs) {
		if (fs == null) {
			return false;
		}
		boolean success = true;
		for (File file : fs) {
			if (isFileLegal(file)) {
				success &= replace(file);
			}
		}
		return success;
	}

	public abstract boolean replace(File paramFile);

	public abstract boolean isFileLegal(File paramFile);

	public abstract String getProcessInfo();

	public static FileFilter getFileFilter() {
		return jarFileFilter;
	}

	public List<File> findLegalFiles(List<File> files) {
		if (files == null) {
			return null;
		}
		List<File> needFiles = new ArrayList<File>();
		for (File file : files) {
			if (isFileLegal(file)) {
				needFiles.add(file);
			}
		}
		return needFiles;
	}

	public File createBakFile(File file) {
		return null;
	}

	public void writeStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] data = new byte['Ѐ'];
		int len = -1;
		while ((len = in.read(data)) != -1) {
			out.write(data, 0, len);
		}
		out.flush();
	}

	public void writeJarEntry(InputStream in, JarOutputStream out)
			throws IOException {
		writeStream(in, out);
		in.close();
		out.flush();
		out.closeEntry();
	}

	public void addJarFile(String entryName, InputStream in, JarOutputStream out)
			throws IOException {
		JarEntry je = new JarEntry(entryName);
		out.putNextEntry(je);
		writeJarEntry(in, out);
	}

	public void addJarFile(String className, JarOutputStream out)
			throws IOException {
		addJarFile(className, ClassLoader.getSystemResourceAsStream(className),
				out);
	}
}
