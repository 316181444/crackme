package com.hxms.crackme.agent;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


public class DynamicReplacePublicKeyAgent implements ClassFileTransformer {

	public static InputStream getResourceAsStream(String $1)
			throws FileNotFoundException {

		String name = $1;
		java.io.InputStream repeatedSteam = null;
		if (name.toLowerCase().contains("publickey")) {
			System.out.println("com.hxms.Agent.getResourceAsStream()::" + name);
			StringBuilder sb = new StringBuilder();
			sb.append("FindFileWith:").append(name).append("\r\n");

			java.io.File fakeFile = new java.io.File(name);
			if (fakeFile.exists()) {
				sb.append("Repeated:").append(fakeFile.getAbsolutePath())
						.append("\r\n");
				repeatedSteam = new java.io.FileInputStream(fakeFile);
			} else {
				sb.append("can't find file with path !! ")
						.append(fakeFile.getAbsolutePath()).append("\r\n");
			}

			System.out.println(sb.toString());
			java.nio.file.OpenOption[] options = {
					java.nio.file.StandardOpenOption.CREATE,
					java.nio.file.StandardOpenOption.WRITE };
			java.io.BufferedWriter writer = null;
			try {
				writer = java.nio.file.Files.newBufferedWriter(
						java.nio.file.Paths.get("HxmsAgent.log",
								new java.lang.String[0]),
						java.nio.charset.StandardCharsets.UTF_8, options);
				writer.write(sb.toString());
				writer.newLine();
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (repeatedSteam != null) {
			return repeatedSteam;
		}

		return null;
	}

	static void writeLog(java.lang.String text) {
		java.nio.file.OpenOption[] options = {
				java.nio.file.StandardOpenOption.CREATE,
				java.nio.file.StandardOpenOption.WRITE };
		java.io.BufferedWriter writer = null;
		try {
			writer = java.nio.file.Files.newBufferedWriter(java.nio.file.Paths
					.get("HxmsAgent.log", new java.lang.String[0]),
					java.nio.charset.StandardCharsets.UTF_8, options);
			writer.write(text);
			writer.newLine();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}
	}

	static void hookResourceCLass(Instrumentation inst) {
		try {
			Class<?> targetClasses = Class.class;
			ClassPool classPool = ClassPool.getDefault();
			CtClass targetCtClass = classPool.get(targetClasses.getName());
			CtMethod method = targetCtClass.getDeclaredMethod(
					"getResourceAsStream",
					new CtClass[] { classPool.get("java.lang.String") });

			method.insertBefore("{        String name = $1;\n"
					+ "        java.io.InputStream repeatedSteam = null;\n"
					+ "        if (name.toLowerCase().contains(\"publickey\")) {\n"
					+ "            System.out.println(\"com.hxms.Agent.getResourceAsStream()::\" + name);\n"
					+ "            StringBuilder sb = new StringBuilder();\n"
					+ "            sb.append(\"FindFileWith:\").append(name).append(\"\\r\\n\");\n"
					+ "\n"
					+ "            java.io.File fakeFile = new java.io.File(name);\n"
					+ "            if (fakeFile.exists()) {\n"
					+ "                sb.append(\"Repeated:\").append(fakeFile.getAbsolutePath()).append(\"\\r\\n\");\n"
					+ "                repeatedSteam = new java.io.FileInputStream(fakeFile);\n"
					+ "            } else {\n"
					+ "                sb.append(\"can't find file with path !! \").append(fakeFile.getAbsolutePath()).append(\"\\r\\n\");\n"
					+ "            }\n"
					+ "\n"
					+ "            System.out.println(sb.toString());\n"
					+ "            java.nio.file.OpenOption[] options = {java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.WRITE};\n"
					+ "            java.io.BufferedWriter writer = null;\n"
					+ "            try {\n"
					+ "                writer = java.nio.file.Files.newBufferedWriter(java.nio.file.Paths.get(\"HxmsAgent.log\", new java.lang.String[0]), java.nio.charset.StandardCharsets.UTF_8, options);\n"
					+ "                writer.write(sb.toString());\n"
					+ "                writer.newLine();\n"
					+ "            } catch (java.lang.Exception e) {\n"
					+ "                e.printStackTrace();\n"
					+ "            } finally {\n" + "                try {\n"
					+ "                    if (writer != null) {\n"
					+ "                        writer.close();\n"
					+ "                    }\n"
					+ "                } catch (java.lang.Exception e) {\n"
					+ "                    e.printStackTrace();\n"
					+ "                }\n" + "            }\n" + "        }\n"
					+ "        if (repeatedSteam != null) {\n"
					+ "            return repeatedSteam;\n" + "        } }");

			inst.redefineClasses(new ClassDefinition(targetClasses,
					targetCtClass.toBytecode()));

			System.err.println("hookResourceCLass Success !!");
			writeLog("hookResourceCLass Success !!");
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new DynamicReplacePublicKeyAgent());
		hookResourceCLass(inst);

		for (Class<?> allLoadedClasse : inst.getAllLoadedClasses()) {
			if (allLoadedClasse.getName().contains("ActivationCodeDecryptor")) {
				System.err.println("com.hxms.Agent.premain()");
			}
		}
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.contains("ActivationCodeDecryptor")) {
			System.err.println("FIND::" + className);
		}
		return classfileBuffer;
	}
}
