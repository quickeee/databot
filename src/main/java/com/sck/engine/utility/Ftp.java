package com.sck.engine.utility;

import com.jcraft.jsch.*;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//ConfigFileFTP(S) Libraries from apache commons
//import org.apache.commons.net.PrintCommandListener;

public class Ftp {
    private static String address = "";
    private static int port;
    private static String userName = "";
    private static String password = "";
    private static String type = "";
    private static String ftpsType = "EXPLICIT_SSL";
    private static String[] cmds;
    private static boolean activeMode = false;
    private static boolean binaryMode = false;
    private static String cDir = "/";
    private static FTPClient ftp = null;
    private static FTPSClient ftps = null;
    private static JSch ssh;
    private static Session session;
    private static Channel channel;
    private static ChannelSftp sftp;

    //CONSTRUCTORS -----------------------------------------------------------------------

    //Most basic constructor -  no port/commands/ftps info...
    public Ftp(String address, String userName, String password, String type) {
        Ftp.address = address;
        Ftp.userName = userName;
        Ftp.password = password;
        Ftp.type = type;
    }
    //Basic with port
    public Ftp(String address, int port, String userName, String password, String type) {
        Ftp.address = address;
        Ftp.port = port;
        Ftp.userName = userName;
        Ftp.password = password;
        Ftp.type = type;
    }
    //Basic with port and passive/active
    public Ftp(String address, int port, String userName, String password, String type, boolean activeMode) {
        Ftp.address = address;
        Ftp.port = port;
        Ftp.userName = userName;
        Ftp.password = password;
        Ftp.type = type;
        Ftp.activeMode = activeMode;
    }
    //Basic with port and passive/active
    public Ftp(String address, int port, String userName, String password, String type, boolean binaryMode, boolean activeMode) {
        Ftp.address = address;
        Ftp.port = port;
        Ftp.userName = userName;
        Ftp.password = password;
        Ftp.type = type;
        Ftp.binaryMode = binaryMode;
        Ftp.activeMode = activeMode;
    }
    //Every possible parameter, needed for tricky FTPS configs (ConfigFileFTP is one of these)
    public Ftp(String address, int port, String userName, String password, String type, String ftpsType, String[] cmds, boolean binaryMode, boolean activeMode) {
        Ftp.address = address;
        Ftp.port = port;
        Ftp.userName = userName;
        Ftp.password = password;
        Ftp.type = type;
        Ftp.ftpsType = ftpsType;
        Ftp.cmds = cmds;
        Ftp.binaryMode = binaryMode;
        Ftp.activeMode = activeMode;
    }

    //PUBLIC METHODS -------------------------------------------------------------------------

    public boolean connect() throws IOException, JSchException {
        //Connect success

        if(type.equals("FileFTP")){

            ftp = new FTPClient();
            int reply;

            if(port > 0) {
                ftp.connect(address, port);
            }else{
                ftp.connect(address);
            }

            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

            //Get response from connection
            reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                return false;
            }

            if(!ftp.login(userName, password)) {
                ftp.logout();
                return false;
            }

            if(binaryMode){
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            }else{
                ftp.setFileType(FTP.ASCII_FILE_TYPE);
            }
            if(activeMode) {
                ftp.enterLocalActiveMode();
            }else{
                ftp.enterLocalPassiveMode();
            }

            return true;

        }else if(type.equals("FTPS")){
            //Create new connection client based on params
            if(ftpsType.equals("IMPLICIT_TLS")) {
                ftps = new FTPSClient("TLS",true);
                //ftps.setAuthValue("TLS");
            }else if(ftpsType.equals("IMPLICIT_SSL")) {
                ftps = new FTPSClient("SSL",true);
            }else if(ftpsType.equals("EXPLICIT_TLS")) {
                ftps = new FTPSClient("TLS",false);
            }else if(ftpsType.equals("EXPLICIT_SSL")) {
                ftps = new FTPSClient("SSL",false);
            }else{
                ftps = new FTPSClient();
            }

            int reply;
            //Spit out responses from base.ftp server
            ftps.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
            //accept all certs
            ftps.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
            //connect
            if(port > 0) {
                ftps.connect(address, port);
            }else{
                ftps.connect(address);
            }
            //Get response from connection
            reply = ftps.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                return false;
            }

            if(!ftps.login(userName, password)) {
                ftps.logout();
                return false;
            }

            //set to ASCII and PASSIVE unless told to do otherwise
            if(binaryMode){
                ftps.setFileType(FTP.BINARY_FILE_TYPE);
            }else{
                ftps.setFileType(FTP.ASCII_FILE_TYPE);
            }
            if(activeMode) {
                ftps.enterLocalActiveMode();
            }else{
                ftps.enterLocalPassiveMode();
            }

            //If we have commands specified
            for(int i=0;i<cmds.length;i++) {
                if(cmds[i].equals("PROT P")) {
                    ftps.execPROT("P");
                }
                if(cmds[i].equals("PBSZ 0")) {
                    ftps.execPBSZ(0);
                }
            }

            return true;

        }else if(type.equals("SFTP")){
            System.out.println("Connecting sftp");
            //TODO test and implement various sftp functions in this class
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            ssh = new JSch();
            session = ssh.getSession(userName, address, port);
            System.out.println("New session created");
            session.setConfig(config);
            session.setPassword(password);
            System.out.println("Connecting...");
            session.connect();
            System.out.println("Connection established");
            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("done");

            //TODO read responses from base.ftp using this library to determine results
            return true;
        }

        return false;

    }

    public boolean chDir(String dir) throws IOException, SftpException {
        //Set or change remote directory
        if(type.equals("FileFTP")){
            if(ftp.changeWorkingDirectory(dir)) {
                cDir = dir;
                return true;
            }else{
                return false;
            }
        }else if(type.equals("FTPS")){
            if(ftps.changeWorkingDirectory(dir)){
                cDir = dir;
                return true;
            }else{
                return false;
            }
        }else if(type.equals("SFTP")){
            //TODO find the response value for this action
            sftp.cd(dir);
            return true;
        }
        return false;
    }

    public void disconnect() throws IOException {
        //end session
        if(type.equals("FileFTP")){
            ftp.disconnect();
        }else if(type.equals("FTPS")){
            ftps.disconnect();
        }
    }

    public boolean getFile(String fileName, String savePath) throws IOException {
        //copy a base.base from the base.ftp to a local path
        boolean s = false;

        InputStream is = null;
        OutputStream os = null;
        os = new FileOutputStream(savePath+fileName);

        if(type.equals("FileFTP")){

            is = ftp.retrieveFileStream(fileName);
            copyStream(is,os);
            s = ftp.completePendingCommand();

        }else if(type.equals("FTPS")){

            is = ftps.retrieveFileStream(fileName);
            copyStream(is,os);
            s = ftps.completePendingCommand();

        }
        //TODO add SFTP base.base dataimport
        //output.close();
        return s;
    }

    public void delFile(String fileName) throws IOException {
        if(type.equals("FileFTP")){
            if(ftp.deleteFile(fileName)) {
            }
        }else if(type.equals("FTPS")){
            if(ftps.deleteFile(fileName)) {
            }
        }
    }
    /*
    public boolean mvFile(String source, String dest) throws IOException {
        if(type.equals("ConfigFileFTP")){
            base.ftp.sendCommand("");
            return true;
        }else if(type.equals("FTPS")){
            return ftps.rename(source, dest);
        }
        return false;
    }
    */
	/*
	public void cpFile(String source, String dest) {

		base.ftp.setFileType(ConfigFileFTP.BINARY_FILE_TYPE);
		InputStream inputStream = base.ftp.retrieveFileStream("");

	     if (null != inputStream) {
	    	 base.ftp.changeWorkingDirectory("/s/t/");
	    	 OutputStream outputStream  = base.ftp.storeFileStream("");

	    	 Util.copyStream(inputStream, outputStream);

	    	 outputStream.flush();

		if(type.equals("ConfigFileFTP")){
			if(base.ftp.) {
			}
		}else if(type.equals("FTPS")){
			if(ftps.) {
			}
		}
	}
	*/
    @SuppressWarnings("unchecked")
    public List<String> fileList() throws IOException, SftpException {

        List<String> fList =  new ArrayList<String>();
        FTPFile[] f = null;
        FTPFile cFile;

        if(type.equals("FileFTP")){
            f = fileListFTP();
        }else if(type.equals("FTPS")){
            f = fileListFTPS();
        }else if(type.equals("SFTP")){
            //f = fileListSFTP();
        }

        if(type.equals("FileFTP") || type.equals("FTPS")) {
            int fCount = f.length;
            for(int i=0;i<fCount;i++) {
                cFile = f[i];
                if(cFile.isFile())
                    fList.add(cFile.getName());
            }
        }else if(type.equals("SFTP")) {
            fList = sftp.ls("*");
        }
        return fList;
    }

    public List<String> fileListExt(String[] extensions) throws IOException, SftpException {

        List<String> fList =  new ArrayList<String>();
        FTPFile[] f = null;
        FTPFile cFile;

        if(type.equals("FileFTP")){
            f = fileListFTP();
        }else if(type.equals("FTPS")){
            f = fileListFTPS();
        }else if(type.equals("SFTP")){
            //f = fileListSFTP();
        }

        if(type.equals("FileFTP") || type.equals("FTPS")) {
            String[] ext;
            String cExt;
            boolean match = false;
            int fCount = f.length;
            for(int i=0;i<fCount;i++) {
                cFile = f[i];
                if(cFile.isFile()) {
                    for(int j=0;j<extensions.length;j++) {
                        ext = cFile.getName().split(".");
                        cExt = ext[ext.length];
                        match = false;
                        if(cExt.contains((CharSequence) extensions[j])){
                            match = true;
                            break;
                        }
                    }
                    if(match) {
                        fList.add(cFile.getName());
                    }
                }
            }
        }else if(type.equals("SFTP")) {
            //fList = sftp.ls("*");
        }

        return fList;
    }

    public List<String> fileListPat(String[] patterns) throws IOException, SftpException {

        List<String> fList =  new ArrayList<String>();
        FTPFile[] f = null;
        FTPFile cFile;

        if(type.equals("FileFTP")){
            f = fileListFTP();
        }else if(type.equals("FTPS")){
            f = fileListFTPS();
        }else if(type.equals("SFTP")){
            //f = fileListSFTP();
        }

        if(type.equals("FileFTP") || type.equals("FTPS")) {

            boolean match = false;
            int fCount = f.length;
            for(int i=0;i<fCount;i++) {
                cFile = f[i];
                if(cFile.isFile()) {
                    for(int j=0;j<patterns.length;j++) {
                        match = false;
                        if(cFile.getName().contains((CharSequence) patterns[j])){
                            match = true;
                            break;
                        }
                    }
                    if(match) {
                        fList.add(cFile.getName());
                    }
                }
            }
        }else if(type.equals("SFTP")) {
            //fList = sftp.ls("*");
        }

        return fList;
    }

    //PRIVATE METHODS ------------------------------------------------------

    private void copyStream (InputStream is, OutputStream os) {
        try {

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //List files
    private FTPFile[] fileListFTPS() throws IOException {
        FTPFile[] files = ftps.listFiles();
        return files;
    }
    private FTPFile[] fileListFTP() throws IOException {
        FTPFile[] files = ftp.listFiles();
        return files;
    }
	/*private FTPFile[] fileListSFTP() throws IOException {
		FTPFile[] files = sftp.listFiles();
		return files;
	}*/

}

