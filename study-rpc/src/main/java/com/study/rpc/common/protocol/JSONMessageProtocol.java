package com.study.rpc.common.protocol;

public class JSONMessageProtocol implements MessageProtocol {
    @Override
    public byte[] marshallingRequest(Request request) throws Exception {
        //TODO json convert to byte
        return new byte[0];
    }

    @Override
    public Request unmarshallingRequest(byte[] data) throws Exception {
        return null;
    }

    @Override
    public byte[] marshallingResponse(Response response) throws Exception {
        return new byte[0];
    }

    @Override
    public Response unmarshallingResponse(byte[] data) throws Exception {
        return null;
    }
}
