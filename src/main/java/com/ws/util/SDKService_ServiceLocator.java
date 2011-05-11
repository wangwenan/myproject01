/**
 * SDKService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.util;

public class SDKService_ServiceLocator extends org.apache.axis.client.Service implements com.ws.util.SDKService_Service {

    public SDKService_ServiceLocator() {
    }


    public SDKService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SDKService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SDKService
    private java.lang.String SDKService_address = "http://116.58.219.223:8090/sdk/SDKService";

    public java.lang.String getSDKServiceAddress() {
        return SDKService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SDKServiceWSDDServiceName = "SDKService";

    public java.lang.String getSDKServiceWSDDServiceName() {
        return SDKServiceWSDDServiceName;
    }

    public void setSDKServiceWSDDServiceName(java.lang.String name) {
        SDKServiceWSDDServiceName = name;
    }

    public com.ws.util.SDKClient getSDKService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SDKService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSDKService(endpoint);
    }

    public com.ws.util.SDKClient getSDKService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ws.util.SDKServiceBindingStub _stub = new com.ws.util.SDKServiceBindingStub(portAddress, this);
            _stub.setPortName(getSDKServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSDKServiceEndpointAddress(java.lang.String address) {
        SDKService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ws.util.SDKClient.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ws.util.SDKServiceBindingStub _stub = new com.ws.util.SDKServiceBindingStub(new java.net.URL(SDKService_address), this);
                _stub.setPortName(getSDKServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SDKService".equals(inputPortName)) {
            return getSDKService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sdkhttp.eucp.b2m.cn/", "SDKService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sdkhttp.eucp.b2m.cn/", "SDKService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SDKService".equals(portName)) {
            setSDKServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
