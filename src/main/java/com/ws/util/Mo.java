/**
 * Mo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.util;

public class Mo  implements java.io.Serializable {
    private java.lang.String addSerial;

    private java.lang.String addSerialRev;

    private java.lang.String channelnumber;

    private java.lang.String mobileNumber;

    private java.lang.String sentTime;

    private java.lang.String smsContent;

    public Mo() {
    }

    public Mo(
           java.lang.String addSerial,
           java.lang.String addSerialRev,
           java.lang.String channelnumber,
           java.lang.String mobileNumber,
           java.lang.String sentTime,
           java.lang.String smsContent) {
           this.addSerial = addSerial;
           this.addSerialRev = addSerialRev;
           this.channelnumber = channelnumber;
           this.mobileNumber = mobileNumber;
           this.sentTime = sentTime;
           this.smsContent = smsContent;
    }


    /**
     * Gets the addSerial value for this Mo.
     * 
     * @return addSerial
     */
    public java.lang.String getAddSerial() {
        return addSerial;
    }


    /**
     * Sets the addSerial value for this Mo.
     * 
     * @param addSerial
     */
    public void setAddSerial(java.lang.String addSerial) {
        this.addSerial = addSerial;
    }


    /**
     * Gets the addSerialRev value for this Mo.
     * 
     * @return addSerialRev
     */
    public java.lang.String getAddSerialRev() {
        return addSerialRev;
    }


    /**
     * Sets the addSerialRev value for this Mo.
     * 
     * @param addSerialRev
     */
    public void setAddSerialRev(java.lang.String addSerialRev) {
        this.addSerialRev = addSerialRev;
    }


    /**
     * Gets the channelnumber value for this Mo.
     * 
     * @return channelnumber
     */
    public java.lang.String getChannelnumber() {
        return channelnumber;
    }


    /**
     * Sets the channelnumber value for this Mo.
     * 
     * @param channelnumber
     */
    public void setChannelnumber(java.lang.String channelnumber) {
        this.channelnumber = channelnumber;
    }


    /**
     * Gets the mobileNumber value for this Mo.
     * 
     * @return mobileNumber
     */
    public java.lang.String getMobileNumber() {
        return mobileNumber;
    }


    /**
     * Sets the mobileNumber value for this Mo.
     * 
     * @param mobileNumber
     */
    public void setMobileNumber(java.lang.String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    /**
     * Gets the sentTime value for this Mo.
     * 
     * @return sentTime
     */
    public java.lang.String getSentTime() {
        return sentTime;
    }


    /**
     * Sets the sentTime value for this Mo.
     * 
     * @param sentTime
     */
    public void setSentTime(java.lang.String sentTime) {
        this.sentTime = sentTime;
    }


    /**
     * Gets the smsContent value for this Mo.
     * 
     * @return smsContent
     */
    public java.lang.String getSmsContent() {
        return smsContent;
    }


    /**
     * Sets the smsContent value for this Mo.
     * 
     * @param smsContent
     */
    public void setSmsContent(java.lang.String smsContent) {
        this.smsContent = smsContent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Mo)) return false;
        Mo other = (Mo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addSerial==null && other.getAddSerial()==null) || 
             (this.addSerial!=null &&
              this.addSerial.equals(other.getAddSerial()))) &&
            ((this.addSerialRev==null && other.getAddSerialRev()==null) || 
             (this.addSerialRev!=null &&
              this.addSerialRev.equals(other.getAddSerialRev()))) &&
            ((this.channelnumber==null && other.getChannelnumber()==null) || 
             (this.channelnumber!=null &&
              this.channelnumber.equals(other.getChannelnumber()))) &&
            ((this.mobileNumber==null && other.getMobileNumber()==null) || 
             (this.mobileNumber!=null &&
              this.mobileNumber.equals(other.getMobileNumber()))) &&
            ((this.sentTime==null && other.getSentTime()==null) || 
             (this.sentTime!=null &&
              this.sentTime.equals(other.getSentTime()))) &&
            ((this.smsContent==null && other.getSmsContent()==null) || 
             (this.smsContent!=null &&
              this.smsContent.equals(other.getSmsContent())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAddSerial() != null) {
            _hashCode += getAddSerial().hashCode();
        }
        if (getAddSerialRev() != null) {
            _hashCode += getAddSerialRev().hashCode();
        }
        if (getChannelnumber() != null) {
            _hashCode += getChannelnumber().hashCode();
        }
        if (getMobileNumber() != null) {
            _hashCode += getMobileNumber().hashCode();
        }
        if (getSentTime() != null) {
            _hashCode += getSentTime().hashCode();
        }
        if (getSmsContent() != null) {
            _hashCode += getSmsContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Mo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sdkhttp.eucp.b2m.cn/", "mo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addSerial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addSerial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addSerialRev");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addSerialRev"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelnumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channelnumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mobileNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sentTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sentTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smsContent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "smsContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
