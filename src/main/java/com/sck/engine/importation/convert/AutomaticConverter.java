package com.sck.engine.importation.convert;

import com.sck.engine.config.ConversionConfig;
import com.sck.engine.model.data.DataRow;
import com.sck.engine.utility.DirectoryFile;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;

import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by KINCERS on 12/8/2015.
 */
public class AutomaticConverter {

    private ConversionConfig conversionConfig;

    public AutomaticConverter(ConversionConfig conversionConfig) {
        this.conversionConfig = conversionConfig;
    }

    public List<DataRow> convert(DirectoryFile file) {

        List<DataRow> dataRowList;

        Parser parser = new AutoDetectParser();
        CustomContentHandler contentHandler =  new CustomContentHandler(); //new BodyContentHandler(new CustomContentHandler());
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        context.set(Locale.class, Locale.US);

        try {
            parser.parse(new FileInputStream(file.getFilePath().toFile()), contentHandler, metadata, context);
        }catch(IOException | SAXException | TikaException e) {
            System.out.println(e.getMessage());
            for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                System.out.println(stackTraceElement);
            }
            System.exit(1);
        }

        System.out.println("Parsed By: " + contentHandler.getParsedBy());
        System.out.println("Content Encoding: " + contentHandler.getContentEncoding());
        System.out.println("Content Type: " + contentHandler.getContentType());

        dataRowList = contentHandler.getDataRows();
        System.out.println("Contents: " + contentHandler.getTextContents());



        int minColumns = -1;

        return dataRowList;
    }
}
