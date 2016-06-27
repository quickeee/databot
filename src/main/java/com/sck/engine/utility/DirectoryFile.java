package com.sck.engine.utility;

import com.sck.engine.config.constant.DataTypes;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class DirectoryFile {

    final private Charset ENCODING = StandardCharsets.UTF_8;
    private Path filePath;
    private String fileType;
    Tika tika = new Tika();

    public DirectoryFile(String path) {
        filePath = Paths.get(path);
        try {
            fileType = tika.detect(filePath.toFile());
        }catch (IOException e) {
            fileType = "Unknown";
        }
    }

    public DirectoryFile(Path path) {
        this.filePath = path;
        try {
            fileType = tika.detect(filePath.toFile());
        }catch (IOException e) {
            fileType = "Unknown";
        }
    }

    private boolean fileAccessible() {
        // Check to see if we can get at the file.
        return Files.isRegularFile(filePath) && Files.isReadable(filePath);
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExtension() {

        String extension = "";
        int i = filePath.getFileName().toString().lastIndexOf('.');
        if (i > 0) {
            extension = filePath.getFileName().toString().substring(i+1);
        }
        return extension;
    }

    public String getFileName() {
        return filePath.getFileName().toString();
    }

    public Path getFilePath() { return filePath; }

    public String getAndBase64Contents() {
        String strEncoded;
        try {
            strEncoded = Base64.getEncoder().encodeToString( Files.readAllBytes(filePath) );
        }catch (UnsupportedEncodingException e) {
            strEncoded = null;
        }catch (IOException io) {
            strEncoded = null;
        }

        return strEncoded;
    }

    public String getFileContents() {
        String content;
        try {
            content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        }catch (IOException e) {
            content = null;
        }
        return content;
    }

    public byte[] getFileBytes() throws IOException {

        return Files.readAllBytes(filePath);
    }

    public String base64Encode(String str) {
        try {
            String strEncoded = Base64.getEncoder().encodeToString( str.getBytes( "utf-8" ) );
        }catch (UnsupportedEncodingException e) {
            str = null;
        }
        return str;
    }

    public String base64Decode(String str) {
        byte[] decodedStr = Base64.getDecoder().decode( str );
        try {
            str = new String( decodedStr, "utf-8" );
        }catch (UnsupportedEncodingException e) {
            str = null;
        }
        return str;
    }

    public List<DirectoryFile> listFiles(String sortType, String globIn, String globEx, int limit) {

        System.out.println("Listing files");
        System.out.println(sortType);
        System.out.println(globIn);
        System.out.println(globEx);
        System.out.println(limit);

        List<DirectoryFile> files = new ArrayList<>();
        List<Path> filePaths = new ArrayList<>();

        if(globIn != null) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath,globIn)) {
                for (Path path : directoryStream) {
                    if(path.toFile().isFile()) {
                        filePaths.add(path);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }else {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath)) {
                for (Path path : directoryStream) {
                    if(path.toFile().isFile()) {
                        filePaths.add(path);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        if(globEx != null) {
            if(globEx != null) {
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath,globEx)) {
                    for (Path path : directoryStream) {
                        if(path.toFile().isFile()) {
                            filePaths.remove(path);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else {
            if(globEx != null) {
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath)) {
                    for (Path path : directoryStream) {
                        if(path.toFile().isFile()) {
                            filePaths.remove(path);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        if(sortType != null) {
            if(sortType.equalsIgnoreCase("ASC")) {
                Collections.sort(filePaths, new Comparator<Path>() {
                    public int compare(Path o1, Path o2) {
                        int c = 0;
                        try {
                            c = Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        return c;
                    }
                });
            }else if(sortType.equalsIgnoreCase("DESC")) {
                Collections.sort(filePaths, new Comparator<Path>() {
                    public int compare(Path o1, Path o2) {
                        int c = 0;
                        try {
                            c = Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
                        }catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        return c;
                    }
                });
                Collections.reverse(filePaths);
            }

        }



        for(int i=0;i<filePaths.size();i++) {
            if(limit > 0 && i > limit) {
                break;
            }
            files.add(new DirectoryFile(filePaths.get(i)));
        }


        System.out.println("Done listing files");
        return files;
    }

    public List<Path> listFilePaths(String sortType, String glob) {

        List<Path> filePaths = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath,glob)) {
            for (Path path : directoryStream) {
                if(path.toFile().isFile()) {
                    filePaths.add(path);
                }
            }
        } catch (IOException e) {}


        if(sortType != null) {
            if(sortType.equalsIgnoreCase("ASC")) {
                Collections.sort(filePaths, new Comparator<Path>() {
                    public int compare(Path o1, Path o2) {
                        int c = 0;
                        try {
                            c = Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
                        }catch (IOException e) {}
                        return c;
                    }
                });
            }else if(sortType.equalsIgnoreCase("DESC")) {
                Collections.sort(filePaths, new Comparator<Path>() {
                    public int compare(Path o1, Path o2) {
                        int c = 0;
                        try {
                            c = Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
                        }catch (IOException e) {}
                        return c;
                    }
                });
                Collections.reverse(filePaths);
            }

        }

        return filePaths;
    }

    // What is the most likely delimiter based on character occurrences?
    public String determineDelimiter() throws IOException {

        if(!DataTypes.FLATFILE.contains(this.fileType)) {
            return "unknown";
        }

        int maxLines = 30;

        Map<String, Integer> delimMap = new HashMap<>();

        char pipe = '|';
        int pipeCount = 0;
        char comma = ',';
        int commaCount = 0;
        char semicolon = ';';
        int semicolonCount = 0;
        char tab = '\t';
        int tabCount = 0;

        Scanner scanner =  new Scanner(filePath, ENCODING.name());
        int lineCount = 0;
        while (scanner.hasNextLine() && lineCount < maxLines){
            lineCount++;
            String currLine = scanner.nextLine();

            for( int i=0; i<currLine.length(); i++ ) {
                if( currLine.charAt(i) == pipe ) {
                    pipeCount++;
                }else if( currLine.charAt(i) == semicolon ) {
                    semicolonCount++;
                }else if( currLine.charAt(i) == comma ) {
                    commaCount++;
                }else if( currLine.charAt(i) == tab ) {
                    tabCount++;
                }
            }

        }

        delimMap.put("pipe",pipeCount);
        delimMap.put("comma",commaCount);
        delimMap.put("semicolon",semicolonCount);
        delimMap.put("tab",tabCount);

        Map.Entry<String, Integer> maxEntry = null;

        for(Map.Entry<String, Integer> entry : delimMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        if(maxEntry != null) {
            return maxEntry.getKey();
        }else {
            return "unknown";
        }
    }


    public boolean move(String destination) throws IOException {

        if(!fileAccessible()) {
            return false;
        }

        Path dest = Paths.get(destination + "/" + filePath.getFileName());

        Files.move(filePath, dest, StandardCopyOption.REPLACE_EXISTING);

        return true;
    }

    public boolean move(String destination, String name) throws IOException {

        if(!fileAccessible()) {
            return false;
        }

        Path dest = Paths.get(destination +"/"+ name);

        Files.move(filePath,dest, StandardCopyOption.REPLACE_EXISTING);

        return true;
    }

    public boolean moveAll(String destination) throws IOException {

        List<Path> paths;
        Path dest;

        paths = this.listFilePaths(null, "*");

        for(Path p : paths) {
            dest = Paths.get(destination +"/"+ p.getFileName() );

            Files.move(p,dest, StandardCopyOption.REPLACE_EXISTING);

        }
        return true;
    }

    public boolean copy(String destination) throws IOException {

        if(!fileAccessible()) {
            return false;
        }

        Path dest = Paths.get(destination);

        Files.copy(filePath,dest, StandardCopyOption.REPLACE_EXISTING);

        return true;
    }

    public boolean delete() throws IOException {

        Files.deleteIfExists(filePath);

        return true;
    }

    public boolean deleteAll() throws IOException {

        List<Path> paths;
        paths = this.listFilePaths(null,"*");

        for(Path p : paths) {

            Files.deleteIfExists(p);

        }
        return true;
    }


}
