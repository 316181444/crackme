package com.crack.me.active.ui.core;

import com.crack.me.active.ui.Loggable;
import java.io.File;

public class PropertyWriter {
	private Loggable log = null;

	public PropertyWriter(Loggable log) {
		this.log = log;
	}

	public void write(String[][] data) {
		String home = System.getProperty("user.home");
		write(data, new File(home + "/.myeclipse.properties"));
	}

	public void write(String[][] data, String file) {
		write(data, new File(file));
	}

	public void write(String[][] data, File file) {
		// Byte code:
		// 0: aload_1
		// 1: ifnonnull +4 -> 5
		// 4: return
		// 5: aconst_null
		// 6: astore_3
		// 7: aload_0
		// 8: getfield 13 com/crack/me/active/ui/core/PropertyWriter:log
		// Lcom/crack/me/active/ui/Loggable;
		// 11: new 31 java/lang/StringBuilder
		// 14: dup
		// 15: ldc 62
		// 17: invokespecial 39 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 20: aload_2
		// 21: invokevirtual 64 java/io/File:getAbsolutePath
		// ()Ljava/lang/String;
		// 24: invokevirtual 44 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 27: ldc 67
		// 29: invokevirtual 44 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 32: invokevirtual 48 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 35: invokeinterface 69 2 0
		// 40: new 73 java/io/BufferedWriter
		// 43: dup
		// 44: new 75 java/io/FileWriter
		// 47: dup
		// 48: aload_2
		// 49: invokespecial 77 java/io/FileWriter:<init> (Ljava/io/File;)V
		// 52: invokespecial 80 java/io/BufferedWriter:<init>
		// (Ljava/io/Writer;)V
		// 55: astore_3
		// 56: aload_3
		// 57: ldc 83
		// 59: invokevirtual 85 java/io/BufferedWriter:write
		// (Ljava/lang/String;)V
		// 62: aload_3
		// 63: invokevirtual 87 java/io/BufferedWriter:newLine ()V
		// 66: aload_3
		// 67: new 31 java/lang/StringBuilder
		// 70: dup
		// 71: ldc 90
		// 73: invokespecial 39 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 76: new 92 java/util/Date
		// 79: dup
		// 80: invokespecial 94 java/util/Date:<init> ()V
		// 83: invokevirtual 95 java/util/Date:toLocaleString
		// ()Ljava/lang/String;
		// 86: invokevirtual 44 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 89: invokevirtual 48 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 92: invokevirtual 85 java/io/BufferedWriter:write
		// (Ljava/lang/String;)V
		// 95: aload_3
		// 96: invokevirtual 87 java/io/BufferedWriter:newLine ()V
		// 99: aload_1
		// 100: dup
		// 101: astore 7
		// 103: arraylength
		// 104: istore 6
		// 106: iconst_0
		// 107: istore 5
		// 109: goto +50 -> 159
		// 112: aload 7
		// 114: iload 5
		// 116: aaload
		// 117: astore 4
		// 119: aload_3
		// 120: new 31 java/lang/StringBuilder
		// 123: dup
		// 124: aload 4
		// 126: iconst_0
		// 127: aaload
		// 128: invokestatic 33 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 131: invokespecial 39 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 134: ldc 98
		// 136: invokevirtual 44 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 139: aload 4
		// 141: iconst_1
		// 142: aaload
		// 143: invokevirtual 44 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 146: invokevirtual 48 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 149: invokevirtual 85 java/io/BufferedWriter:write
		// (Ljava/lang/String;)V
		// 152: aload_3
		// 153: invokevirtual 87 java/io/BufferedWriter:newLine ()V
		// 156: iinc 5 1
		// 159: iload 5
		// 161: iload 6
		// 163: if_icmplt -51 -> 112
		// 166: aload_3
		// 167: ldc 100
		// 169: invokevirtual 85 java/io/BufferedWriter:write
		// (Ljava/lang/String;)V
		// 172: aload_3
		// 173: invokevirtual 87 java/io/BufferedWriter:newLine ()V
		// 176: aload_3
		// 177: ldc 102
		// 179: invokevirtual 85 java/io/BufferedWriter:write
		// (Ljava/lang/String;)V
		// 182: aload_3
		// 183: invokevirtual 87 java/io/BufferedWriter:newLine ()V
		// 186: goto +54 -> 240
		// 189: astore 4
		// 191: aload 4
		// 193: invokevirtual 104 java/io/IOException:printStackTrace ()V
		// 196: aload_3
		// 197: ifnull +61 -> 258
		// 200: aload_3
		// 201: invokevirtual 109 java/io/BufferedWriter:close ()V
		// 204: goto +54 -> 258
		// 207: astore 9
		// 209: aload 9
		// 211: invokevirtual 104 java/io/IOException:printStackTrace ()V
		// 214: goto +44 -> 258
		// 217: astore 8
		// 219: aload_3
		// 220: ifnull +17 -> 237
		// 223: aload_3
		// 224: invokevirtual 109 java/io/BufferedWriter:close ()V
		// 227: goto +10 -> 237
		// 230: astore 9
		// 232: aload 9
		// 234: invokevirtual 104 java/io/IOException:printStackTrace ()V
		// 237: aload 8
		// 239: athrow
		// 240: aload_3
		// 241: ifnull +17 -> 258
		// 244: aload_3
		// 245: invokevirtual 109 java/io/BufferedWriter:close ()V
		// 248: goto +10 -> 258
		// 251: astore 9
		// 253: aload 9
		// 255: invokevirtual 104 java/io/IOException:printStackTrace ()V
		// 258: return
		// Line number table:
		// Java source line #29 -> byte code offset #0
		// Java source line #30 -> byte code offset #4
		// Java source line #32 -> byte code offset #5
		// Java source line #33 -> byte code offset #7
		// Java source line #35 -> byte code offset #40
		// Java source line #36 -> byte code offset #56
		// Java source line #37 -> byte code offset #62
		// Java source line #38 -> byte code offset #66
		// Java source line #39 -> byte code offset #95
		// Java source line #40 -> byte code offset #99
		// Java source line #41 -> byte code offset #119
		// Java source line #42 -> byte code offset #152
		// Java source line #40 -> byte code offset #156
		// Java source line #44 -> byte code offset #166
		// Java source line #45 -> byte code offset #172
		// Java source line #46 -> byte code offset #176
		// Java source line #47 -> byte code offset #182
		// Java source line #48 -> byte code offset #186
		// Java source line #49 -> byte code offset #191
		// Java source line #52 -> byte code offset #196
		// Java source line #53 -> byte code offset #200
		// Java source line #55 -> byte code offset #204
		// Java source line #56 -> byte code offset #209
		// Java source line #50 -> byte code offset #217
		// Java source line #52 -> byte code offset #219
		// Java source line #53 -> byte code offset #223
		// Java source line #55 -> byte code offset #227
		// Java source line #56 -> byte code offset #232
		// Java source line #58 -> byte code offset #237
		// Java source line #52 -> byte code offset #240
		// Java source line #53 -> byte code offset #244
		// Java source line #55 -> byte code offset #248
		// Java source line #56 -> byte code offset #253
		// Java source line #60 -> byte code offset #258
		// Local variable table:
		// start length slot name signature
		// 0 259 0 this PropertyWriter
		// 0 259 1 data String[][]
		// 0 259 2 file File
		// 6 239 3 bw java.io.BufferedWriter
		// 117 23 4 keyV String[]
		// 189 3 4 e java.io.IOException
		// 107 57 5 i int
		// 104 60 6 j int
		// 101 12 7 arrayOfString String[][]
		// 217 21 8 localObject Object
		// 207 3 9 e java.io.IOException
		// 230 3 9 e java.io.IOException
		// 251 3 9 e java.io.IOException
		// Exception table:
		// from to target type
		// 40 186 189 java/io/IOException
		// 196 204 207 java/io/IOException
		// 40 196 217 finally
		// 219 227 230 java/io/IOException
		// 240 248 251 java/io/IOException
	}
}
