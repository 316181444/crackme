package com.crack.me.active;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LicenceCode {
	static final String LICENCE_KEY = "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself.";
	private Map<Integer, String> licenceTypes = null;

	public static final int STANDARD_TYPE = 0;
	public static final int PROFESSIONAL_TYPE = 1;
	public static final int BLUE_TYPE = 2;
	public static final int SPRING_TYPE = 3;
	public static final int BLING_TYPE = 4;
	public static final int DEFAULT_LICENCE_TYPE = 1;
	public static final int UNLIMITED_LICENCE_NUMBER = 0;

	public LicenceCode() {
		initLicenceType();
	}

	private void initLicenceType() {
		this.licenceTypes = new HashMap<Integer, String>();
		this.licenceTypes.put(Integer.valueOf(0), "YE2MY");
		this.licenceTypes.put(Integer.valueOf(1), "YE3MP");
		this.licenceTypes.put(Integer.valueOf(2), "YE3MB");
		this.licenceTypes.put(Integer.valueOf(3), "YE3MS");
		this.licenceTypes.put(Integer.valueOf(4), "YE3BS");
	}

	private String getLicenceTypeCode(int type) {
		String licenceType = (String) this.licenceTypes.get(Integer
				.valueOf(type));
		if (licenceType == null) {
			licenceType = (String) this.licenceTypes.get(Integer.valueOf(1));
		}
		return licenceType;
	}

	public String generateLicenceCode(String userId, int licenceNum,
			int licenceType) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(1, 3);
		calendar.add(6, -1);

		String uid = userId.substring(0, 1);
		String licenceTypeCode = getLicenceTypeCode(licenceType);
		String licenceVersion = "300";
		String licenceNumber = new DecimalFormat("000").format(Integer
				.valueOf(licenceNum));
		String expirationDate = new SimpleDateFormat("yyMMdd").format(calendar
				.getTime()) + "0";

		String base = uid + licenceTypeCode + "-" + licenceVersion
				+ licenceNumber + "-" + expirationDate;

		String needDecode = base
				+ "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself."
				+ userId;
		int suf = decode(needDecode);

		String code = base + String.valueOf(suf);
		return transform(code);
	}

	int decode(String s) {
		char[] ac = s.toCharArray();
		int sum = 0;
		for (int i = 0; i < ac.length; i++) {
			sum = 31 * sum + ac[i];
		}
		return Math.abs(sum);
	}

	String transform(String s) {
		byte[] bytes = s.getBytes();
		char[] changed = new char[s.length()];
		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i];
			if ((value >= 48) && (value <= 57)) {
				value = (value - 48 + 5) % 10 + 48;
			} else if ((value >= 65) && (value <= 90)) {
				value = (value - 65 + 13) % 26 + 65;
			} else if ((value >= 97) && (value <= 122)) {
				value = (value - 97 + 13) % 26 + 97;
			}
			changed[i] = ((char) value);
		}

		return String.valueOf(changed);
	}

	public boolean isLicenceCorrect(String uid, String licenceCode) {
		if ((licenceCode == null) || (licenceCode.length() < 22)) {
			return false;
		}
		String lc = transform(licenceCode);
		String base = lc.substring(0, 21);
		int srcCode = Integer.parseInt(lc.substring(21, lc.length()));
		int desCode = decode(base
				+ "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself."
				+ uid);
		return srcCode == desCode;
	}

	public boolean isLicenceDateExpired(String licenceCode) {
		if ((licenceCode == null) || (licenceCode.length() < 22)) {
			return true;
		}
		try {
			String lc = transform(licenceCode);
			String date = lc.substring(14, 20);
			Date licenceDate = new SimpleDateFormat("yyyyMMdd").parse("20"
					+ date);
			Calendar licenceCalendar = Calendar.getInstance();
			licenceCalendar.setTime(licenceDate);

			int compare = licenceCalendar.compareTo(Calendar.getInstance());

			return compare <= 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getExpirationDate(String licenceCode) {
		String lc = transform(licenceCode);

		String date = lc.substring(14, 20);

		return date;
	}

	public void showLicence(String licenceCode) {
		licenceCode = transform(licenceCode);
	}
}
