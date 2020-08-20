package com.study.rpc.server;

public abstract class BaseRpcServer implements RpcServer{
    protected int port;

    protected String protocol;

    protected RequestHandler requestHandler;

    public BaseRpcServer(int port, String protocol, RequestHandler requestHandler) {
        this.port = port;
        this.protocol = protocol;
        this.requestHandler = requestHandler;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }
}
