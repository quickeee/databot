package com.sck.engine.importation.convert;

import com.sck.engine.model.data.DataField;
import com.sck.engine.model.data.DataRow;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 12/8/2015.
 */
public class CustomContentHandler implements ContentHandler {


    private String parsedBy;
    private String contentType;
    private String contentEncoding;

    // File parsing variables
    private boolean inBodyTag = false;

    private boolean legacyExcelField = false;

    private boolean gotSheet = false;
    private boolean gotSheetName = false;
    private boolean gotSheetBody = false;
    private boolean gotSheetRow = false;
    private boolean gotSheetField = false;
    private String currentSheetName = "";

    private DataRow currentDataRow;

    private boolean gotTextBody = false;

    private String textContents = "";

    private List<DataRow> dataRows = new ArrayList<>();


    public String getParsedBy() {
        return parsedBy;
    }
    public String getContentType() {
        return contentType;
    }
    public String getContentEncoding() {
        return contentEncoding;
    }


    public String getTextContents() {
        return textContents;
    }
    public List<DataRow> getDataRows() {
        return dataRows;
    }

    // ----------------------------------------------------------------------------------------------------------------
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

        //System.out.print("<"+localName);

        for (int i = 0; i < atts.getLength(); i++) {
            //System.out.print(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
        }

        //System.out.print(">");
        //System.out.println();

        // Get Meta Data
        if(localName.equalsIgnoreCase("meta")) {
            for (int i = 0; i < atts.getLength(); i++) {

                // Parsed by
                if(atts.getQName(i).equalsIgnoreCase("name") && atts.getValue(i).equalsIgnoreCase("X-Parsed-By")) {
                    String[] classSplit = atts.getValue("content").split("\\.");
                    this.parsedBy = classSplit[classSplit.length-1];
                }
                // Content encoding
                if(atts.getQName(i).equalsIgnoreCase("name") && atts.getValue(i).equalsIgnoreCase("Content-Encoding")) {
                    this.contentEncoding = atts.getValue("content");
                }
                // Content type
                if(atts.getQName(i).equalsIgnoreCase("name") && atts.getValue(i).equalsIgnoreCase("Content-Type")) {
                    this.contentType = atts.getValue("content");
                }

            }
        }

        if(localName.equalsIgnoreCase("body")) {
            this.inBodyTag = true;
        }

        // TODO - Reliable way to get file type
        if(this.inBodyTag && (this.parsedBy.equalsIgnoreCase("OfficeParser") || this.parsedBy.equalsIgnoreCase("OOXMLParser"))) {
            // We've got a table to parse out...

            if(localName.equalsIgnoreCase("p")) {
                // p one per field, no line breaks provided
                this.legacyExcelField = true;
            }

            if(!legacyExcelField) {
                // div (one per sheet)
                if(localName.equalsIgnoreCase("div")) {
                    this.gotSheet = true;
                }
                // h1 -> sheet name
                if(localName.equalsIgnoreCase("h1") && this.gotSheet) {
                    this.gotSheetName = true;
                }
                // table
                // tbody
                if(localName.equalsIgnoreCase("tbody") && this.gotSheet) {
                    this.gotSheetBody = true;
                }
                // tr (one per row) -> DataRow
                if(localName.equalsIgnoreCase("tr") && this.gotSheetBody) {
                    this.gotSheetRow = true;
                    currentDataRow = new DataRow(); // close it and add it to the list on end element
                    currentDataRow.setParentName(currentSheetName);
                }
                // td (content) -> DataField
                if(localName.equalsIgnoreCase("td") && this.gotSheetRow) {
                    this.gotSheetField = true;
                }
            }



        }else if(this.inBodyTag && this.parsedBy.equalsIgnoreCase("TXTParser")) {
            // We should have a paragraph tag with all the contents in it...
            if(localName.equalsIgnoreCase("p")) {
                this.gotTextBody = true;
            }

        }

    }

    public void endElement(String namespaceURI, String localName, String qName) {

        if(localName.equalsIgnoreCase("body")) {
            this.inBodyTag = false;
        }

        if(this.inBodyTag && (this.parsedBy.equalsIgnoreCase("OfficeParser") || this.parsedBy.equalsIgnoreCase("OOXMLParser"))) {
            // We've got a table to parse out...

            if(localName.equalsIgnoreCase("p") && this.legacyExcelField) {
                this.legacyExcelField = false;
            }

            if(!legacyExcelField) {
                // div (one per sheet)
                if(localName.equalsIgnoreCase("div")) {
                    this.gotSheet = false;
                }
                // h1 -> sheet name
                if(localName.equalsIgnoreCase("h1") && this.gotSheet) {
                    this.gotSheetName = false;
                }
                // table
                // tbody
                if(localName.equalsIgnoreCase("tbody") && this.gotSheet) {
                    this.gotSheetBody = false;
                }
                // tr (one per row) -> DataRow
                if(localName.equalsIgnoreCase("tr") && this.gotSheetBody) {
                    this.gotSheetRow = false;
                    dataRows.add(currentDataRow);
                }
                // td (content) -> DataField
                if(localName.equalsIgnoreCase("td") && this.gotSheetRow) {
                    this.gotSheetField = false;
                }
            }

        }else if(this.inBodyTag && this.parsedBy.equalsIgnoreCase("TXTParser")) {
            // We should have a paragraph tag with all the contents in it...
            if(localName.equalsIgnoreCase("p")) {
                this.gotTextBody = false;
            }

        }

        if(this.inBodyTag) {
            //System.out.println();
        }
        //System.out.print("</"+localName+">");
        //System.out.println();
    }

    public void characters(char[] ch, int start, int length) {
        //System.out.print(new String(ch));
        showCharacters(ch);
    }

    public void showCharacters(char[] ch) {

        if(this.gotSheetName) {
            this.currentSheetName = new String(ch);
        }

        if(this.gotSheetField) {
            DataField dataField = new DataField();
            dataField.setContent(new String(ch));
            currentDataRow.getFields().add(dataField);
        }

        if(this.gotTextBody) {
            // Send to delimited parser
            this.textContents = new String(ch);
        }

        if(this.legacyExcelField) {
            // Send to legacy excel parser?
            String currentContents = new String(ch);
            if(currentContents.startsWith("Sheet:")) {
                this.currentSheetName = currentContents.substring(7);
                System.out.println("New Sheet: "+ this.currentSheetName);
            }else {
                this.textContents += currentContents+"|";
            }
        }


    }


    // Unused ---------------------------------------------------------------------------------------------------------
    public void setDocumentLocator(Locator locator) {
        System.out.println("Tried to set document locator?");
    }
    public void startDocument() {
    }
    public void endDocument() {
    }
    public void startPrefixMapping(String prefix, String uri) {
        //System.out.println("START PREFIX MAPPING");
    }
    public void endPrefixMapping(String prefix) {
        //System.out.println("STOP PREFIX MAPPING");
    }
    public void ignorableWhitespace(char[] ch, int start, int length) {
        //System.out.println("IGNORABLE WHITESPACE");
    }
    public void processingInstruction(String target, String data) {
        //System.out.println("PROCESSING INSTRUCTION");
    }
    public void skippedEntity(String name) {
        //System.out.println("SKIPPED ENTITY: "+name);
    }


}
