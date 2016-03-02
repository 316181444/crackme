 package com.crack.me.active;
 
 import java.util.Random;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class SystemId
 {
   public static final int NODE_LOCKED = 1;
   public static final int NODE_UNLOCKED = 2;
   public static final String NO_HOST = "00";
   public static final String NO_SYSINFO = "0000000";
   public static final String NO_MAC = "0000";
   public static final String NO_HD = "0000";
   public static final int FORMAT_TYPE_FIELD_LENGTH = 1;
   public static final int FORMAT_FIELD_LENGTH = 1;
   public static final int HOST_FIELD_LENGTH = "00".length();
   public static final int SYSINFO_FIELD_LENGTH = "0000000".length();
   public static final int MAC_FIELD_LENGTH = "0000".length();
   public static final int HD_FIELD_LENGTH = "0000".length();
   
   public static final int SYSTEM_ID_LENGTH = 2 + HOST_FIELD_LENGTH + SYSINFO_FIELD_LENGTH + MAC_FIELD_LENGTH + HD_FIELD_LENGTH;
   
   private int field = 0;
   private String hostInfo = "00";
   private String systemInfo = "0000000";
   private String macAddress = "0000";
   private String HDSerial = "0000";
   
 
 
 
 
 
   public static SystemId fromCode(String systemId)
     throws Exception
   {
     if ((isNullOrEmpty(systemId)) || (systemId.length() < SYSTEM_ID_LENGTH)) {
       throw new Exception("InvalidLicenseDataException!{systemId is null or length not right!}");
     }
     
     int typeField = Integer.parseInt(systemId.substring(0, 1), 16);
     if ((typeField != 1) && (typeField != 2)) {
       throw new Exception("InvalidLicenseDataException!{systemId first letter must be 1 or 2 !}");
     }
     
     int field = Integer.parseInt(systemId.substring(1, 2), 16);
     String host = "00";String systemInfo = "0000000";String macAddress = "0000";String hdSerial = "0000";
     
     if ((field & 0x1) == 1) {
       host = systemId.substring(2, 2 + HOST_FIELD_LENGTH);
     }
     if ((field & 0x2) == 2) {
       systemInfo = systemId.substring(4, 4 + SYSINFO_FIELD_LENGTH);
     }
     if ((field & 0x4) == 4) {
       macAddress = systemId.substring(11, 11 + MAC_FIELD_LENGTH);
     }
     if ((field & 0x8) == 8) {
       hdSerial = systemId.substring(15, 15 + HD_FIELD_LENGTH);
     }
     
     return new SystemId(field, host, systemInfo, macAddress, hdSerial);
   }
   
   public SystemId(int field, String hostInfo, String systemInfo, String macAddress, String HDSerial) {
     this.field = field;
     this.hostInfo = ((isNullOrEmpty(hostInfo)) || (!isIdLegal(hostInfo)) ? "00" : hostInfo);
     this.systemInfo = ((isNullOrEmpty(systemInfo)) || (!isIdLegal(systemInfo)) ? "0000000" : systemInfo);
     this.macAddress = ((isNullOrEmpty(macAddress)) || (!isIdLegal(macAddress)) ? "0000" : macAddress);
     this.HDSerial = ((isNullOrEmpty(HDSerial)) || (!isIdLegal(HDSerial)) ? "0000" : HDSerial);
   }
   
 
 
 
 
   public boolean isNodeLocked()
   {
     return true;
   }
   
   public int getField() {
     return this.field;
   }
   
   public String getHostInfo() {
     return this.hostInfo;
   }
   
   public boolean hasHostInfo() {
     return !"00".equals(this.hostInfo);
   }
   
   public String getSystemInfo() {
     return this.systemInfo;
   }
   
   public boolean hasSystemInfo() {
     return !"0000000".equals(this.systemInfo);
   }
   
   public String getMacAddress() {
     return this.macAddress;
   }
   
   public boolean hasMacAddress() {
     return !"0000".equals(this.macAddress);
   }
   
   public String getHDSerial() {
     return this.HDSerial;
   }
   
   public boolean hasHDSerial() {
     return !"0000".equals(this.HDSerial);
   }
   
 
 
 
 
 
 
 
 
 
 
 
   public boolean matches(SystemId other)
   {
     if (other == null) {
       return false;
     }
     if ((hasHDSerial()) && (other.hasHDSerial()) && (getHDSerial().equals(other.getHDSerial()))) {
       return true;
     }
     if ((hasMacAddress()) && (other.hasMacAddress()) && (getMacAddress().equals(other.getMacAddress()))) {
       return true;
     }
     if ((hasHostInfo()) && (other.hasHostInfo()) && (getHostInfo().equals(other.getHostInfo()))) {
       return true;
     }
     return (hasSystemInfo()) && (other.hasSystemInfo()) && (getSystemInfo().equals(other.getSystemInfo()));
   }
   
 
 
 
 
   public String getCode()
   {
     int typeField = isNodeLocked() ? 1 : 2;
     int field = 0;
     field += (hasHostInfo() ? 1 : 0);
     field += (hasSystemInfo() ? 2 : 0);
     field += (hasMacAddress() ? 4 : 0);
     field += (hasHDSerial() ? 8 : 0);
     
     return Integer.toString(typeField, 16) + 
       Integer.toString(field, 16) + (
       hasHostInfo() ? getHostInfo() : randomId(HOST_FIELD_LENGTH)) + (
       hasSystemInfo() ? getSystemInfo() : randomId(SYSINFO_FIELD_LENGTH)) + (
       hasMacAddress() ? getMacAddress() : randomId(MAC_FIELD_LENGTH)) + (
       hasHDSerial() ? getHDSerial() : randomId(HD_FIELD_LENGTH));
   }
   
 
 
 
 
 
   private String randomId(int idLength)
   {
     StringBuffer localStringBuffer = new StringBuffer(idLength);
     Random localRandom = new Random();
     for (int i = 0; i < idLength; i++)
       localStringBuffer.append(Integer.toHexString(localRandom.nextInt(16)));
     return localStringBuffer.toString();
   }
   
 
 
 
 
 
 
   private static boolean isIdLegal(String id)
   {
     if (isNullOrEmpty(id))
       return false;
     for (int i = 0; i < id.length(); i++) {
       String str = id.substring(i, i + 1);
       try {
         Byte.parseByte(str, 16);
       } catch (NumberFormatException e) {
         e.printStackTrace();
         return false;
       }
     }
     return true;
   }
   
   public static boolean isNullOrEmpty(String value) {
     return (value == null) || ("".equals(value));
   }
   
   public String toString()
   {
     return 
     
 
 
 
       "TypeField:\t" + (isNodeLocked() ? 1 : 2) + "\nField:    \t" + this.field + "\nHostInfo:\t" + this.hostInfo + "\nSystemInfo:\t" + this.systemInfo + "\nMacAddress:\t" + this.macAddress + "\nHDSerial:\t" + this.HDSerial;
   }
 }


