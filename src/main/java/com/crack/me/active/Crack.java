 package com.crack.me.active;
 
 public class Crack implements Crackable
 {
   public String getSystemId() throws Exception {
     return new SystemIdFactory().generateSystemId();
   }
   
   public String[][] crackme(String uid) throws Exception {
     return crackme(uid, 1, 0, null);
   }
   
   public String[][] crackme(String uid, int licenceType) throws Exception {
     return crackme(uid, licenceType, 0, null);
   }
   
   public String[][] crackme(String uid, String systemId) throws Exception {
     return crackme(uid, 1, 0, systemId);
   }
   
   public String[][] crackme(String uid, int licenceType, String systemId) throws Exception {
     return crackme(uid, licenceType, 0, systemId);
   }
   
   public String[][] crackme(String uid, int licenceType, int licenceNumber, String systemId) throws Exception
   {
     if ((systemId == null) || ("".equals(systemId))) {
       systemId = getSystemId();
     }
     
     LicenceCode lc = new LicenceCode();
     String licenceCode = lc.generateLicenceCode(uid, licenceNumber, licenceType);
     String dateString = lc.getExpirationDate(licenceCode);
     
     ActivationCode ac = ActivationCode.fromCode(systemId, licenceCode, dateString);
     
     String perpareCode = ac.generateActivationCode();
     String activeCode = new RSAKey().encryption(perpareCode);
     
     return new String[][] {
       { "LICENSEE", uid }, 
       { "LICENSE_KEY", licenceCode }, 
       { "ACTIVATION_CODE", perpareCode }, 
       { "ACTIVATION_KEY", activeCode } };
   }
 }


