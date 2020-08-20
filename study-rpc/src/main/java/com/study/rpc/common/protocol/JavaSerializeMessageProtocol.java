package com.study.rpc.common.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class JavaSerializeMessageProtocol implements MessageProtocol {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public byte[] marshallingRequest(Request request) throws Exception {
        return this.serilize(request);
    }

    @Override
    public Request unmarshallingRequest(byte[] data) throws Exception {
        return this.deserialize(data,Request.class);
    }

    @Override
    public byte[] marshallingResponse(Response response) throws Exception {
        return this.serilize(response);
    }

    @Override
    public Response unmarshallingResponse(byte[] data) throws Exception {
        return this.deserialize(data,Response.class);
    }

    /** 序列化 */
    private byte[] serilize(Object obj) throws Exception{
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream outputStream = null;
        byte[] data =null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(obj);
            data = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return data;
    }

    /** 反序列化 */
    private <T>T deserialize(byte[] data,Class<T> cls) throws Exception{
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream inputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(data);
            inputStream = new ObjectInputStream(byteArrayInputStream);
            Object o = inputStream.readObject();
            return (T)o;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();;
                }
            } catch (IOException e) {
                log.error("error",e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("error",e);
            }
        }
    }
}
