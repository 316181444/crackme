package com.crack.me.active;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetActivationKeys {
	private static String USER_ID = "hxms";

	private static String SYSTEM_ID = "";

	private static String licenceTypeCode = "YE3BS";

	private static String LICENCEVERSION = "300";

	private static int LICENCENUMBER = 0;

	public static void main(String[] args) {
		getActivationKeys();
	}

	private static void getActivationKeys() {
		if ((SYSTEM_ID == null) || (SYSTEM_ID.trim().equals(""))) {
			try {
				SYSTEM_ID = new SystemIdFactory().generateSystemId();
			} catch (Exception localException1) {
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(1, 2099);
		calendar.set(6, 365);

		String uid = USER_ID.substring(0, 1);
		String licenceNumber = new DecimalFormat("000").format(Integer
				.valueOf(LICENCENUMBER));
		String expirationDate = new SimpleDateFormat("yyMMdd").format(calendar
				.getTime()) + "0";

		String base = uid + licenceTypeCode + "-" + LICENCEVERSION
				+ licenceNumber + "-" + expirationDate;

		LicenceCode lc = new LicenceCode();

		String needDecode = base
				+ "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself."
				+ USER_ID;
		int suf = lc.decode(needDecode);

		String code = base + String.valueOf(suf);
		String licenceCode = lc.transform(code);

		String dateString = lc.getExpirationDate(licenceCode);

		ActivationCode ac = null;
		try {
			ac = ActivationCode.fromCode(SYSTEM_ID, licenceCode, dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String perpareCode = ac.generateActivationCode();

		String activeCode = new RSAKey().encryption(perpareCode);

		System.err.println("LICENSEE=" + USER_ID);
		System.err.println("LICENSE_KEY=" + licenceCode);
		System.err.println("ACTIVATION_KEY=" + activeCode);
	}
}
