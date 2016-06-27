package com.sck.engine.config.file.imp;

import com.sck.engine.config.constant.FTP;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class ConfigFileFTP {

    private String ftpType = FTP.TYPE_FTP;
    private String ftpsType = null;
    private String address = "127.0.0.1";
    private Integer port;
    private String username;
    private String password;
    private String commands;
    private Boolean activeMode = false;

    public String getFtpType() {
        return ftpType;
    }

    public void setFtpType(String ftpType) {
        this.ftpType = ftpType;
    }

    public String getFtpsType() {
        return ftpsType;
    }

    public void setFtpsType(String ftpsType) {
        this.ftpsType = ftpsType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public Boolean isActiveMode() {
        return activeMode;
    }

    public void setActiveMode(Boolean activeMode) {
        this.activeMode = activeMode;
    }
}
