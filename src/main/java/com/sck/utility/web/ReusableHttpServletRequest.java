package com.sck.utility.web;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by KINCERS on 12/16/2015.
 */
public class ReusableHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] rawData;
    private HttpServletRequest request;
    private ReusableServletInputStream servletStream;

    public ReusableHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    public void init() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(this.request.getReader());
        }
        servletStream = new ReusableServletInputStream(new ByteArrayInputStream(rawData));
    }

    public void reset() {
        servletStream.setStream(new ByteArrayInputStream(rawData));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return servletStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(servletStream));
    }

}
