package com.crack.me.active;

import java.io.IOException;

public final class Hex {
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static Appendable append(Appendable a, short in) {
		return append(a, in, 4);
	}

	public static Appendable append(Appendable a, short in, int length) {
		return append(a, in, length);
	}

	public static Appendable append(Appendable a, int in) {
		return append(a, in, 8);
	}

	public static Appendable append(Appendable a, int in, int length) {
		return append(a, in, length);
	}

	public static Appendable append(Appendable a, long in) {
		return append(a, in, 16);
	}

	public static Appendable append(Appendable a, long in, int length) {
		try {
			int lim = (length << 2) - 4;
			while (lim >= 0) {
				a.append(DIGITS[((byte) (int) (in >> lim) & 0xF)]);
				lim -= 4;
			}
		} catch (IOException localIOException) {
		}

		return a;
	}

	public static Appendable append(Appendable a, byte[] bytes) {
		try {
			byte[] arrayOfByte;

			int j = (arrayOfByte = bytes).length;
			for (int i = 0; i < j; i++) {
				byte b = arrayOfByte[i];
				a.append(DIGITS[((byte) ((b & 0xF0) >> 4))]);
				a.append(DIGITS[((byte) (b & 0xF))]);
			}
		} catch (IOException localIOException) {
		}

		return a;
	}

	public static long parseLong(CharSequence s) {
		long out = 0L;
		byte shifts = 0;

		for (int i = 0; (i < s.length()) && (shifts < 16); i++) {
			char c = s.charAt(i);
			if ((c > '/') && (c < ':')) {
				shifts = (byte) (shifts + 1);
				out <<= 4;
				out |= c - '0';
			} else if ((c > '@') && (c < 'G')) {
				shifts = (byte) (shifts + 1);
				out <<= 4;
				out |= c - '7';
			} else if ((c > '`') && (c < 'g')) {
				shifts = (byte) (shifts + 1);
				out <<= 4;
				out |= c - 'W';
			}
		}

		return out;
	}

	public static short parseShort(String s) {
		short out = 0;
		byte shifts = 0;

		for (int i = 0; (i < s.length()) && (shifts < 4); i++) {
			char c = s.charAt(i);
			if ((c > '/') && (c < ':')) {
				shifts = (byte) (shifts + 1);
				out = (short) (out << 4);
				out = (short) (out | c - '0');
			} else if ((c > '@') && (c < 'G')) {
				shifts = (byte) (shifts + 1);
				out = (short) (out << 4);
				out = (short) (out | c - '7');
			} else if ((c > '`') && (c < 'g')) {
				shifts = (byte) (shifts + 1);
				out = (short) (out << 4);
				out = (short) (out | c - 'W');
			}
		}

		return out;
	}

	public static byte[] decodeHex(char[] data) throws Exception {
		int len = data.length;

		if ((len & 0x1) != 0) {
			throw new Exception("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		int i = 0;
		for (int j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = ((byte) (f & 0xFF));
		}

		return out;
	}

	protected static int toDigit(char ch, int index) throws Exception {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new Exception("Illegal hexadecimal charcter " + ch
					+ " at index " + index);
		}
		return digit;
	}

	public static char[] encodeHex(byte[] data) {
		int l = data.length;

		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS[(0xF & data[i])];
		}

		return out;
	}

	public byte[] decode(byte[] array) throws Exception {
		return decodeHex(new String(array).toCharArray());
	}

	public Object decode(Object object) throws Exception {
		try {
			char[] charArray = (object instanceof String) ? ((String) object)
					.toCharArray() : (char[]) object;
			return decodeHex(charArray);
		} catch (ClassCastException e) {
			throw new Exception(e.getMessage());
		}
	}

	public byte[] encode(byte[] array) {
		return new String(encodeHex(array)).getBytes();
	}

	public Object encode(Object object) throws Exception {
		try {
			byte[] byteArray = (object instanceof String) ? ((String) object)
					.getBytes() : (byte[]) object;
			return encodeHex(byteArray);
		} catch (ClassCastException e) {
			throw new Exception(e.getMessage());
		}
	}
}
