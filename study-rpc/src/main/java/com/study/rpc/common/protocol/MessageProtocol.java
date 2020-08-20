package com.study.rpc.common.protocol;

public interface MessageProtocol {

    byte[] marshallingRequest(Request request) throws Exception;

    Request unmarshallingRequest(byte[] data) throws Exception;

    byte[] marshallingResponse(Response response) throws Exception;

    Response unmarshallingResponse(byte[] data) throws Exception;
}
