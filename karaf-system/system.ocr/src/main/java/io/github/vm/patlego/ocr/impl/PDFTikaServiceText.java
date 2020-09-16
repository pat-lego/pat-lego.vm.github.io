package io.github.vm.patlego.ocr.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import io.github.vm.patlego.ocr.OCRService;
import io.github.vm.patlego.ocr.enums.ContentTypes;
import io.github.vm.patlego.ocr.exceptions.FailedOCRException;
import io.github.vm.patlego.ocr.utils.OCRConversionInput;
import io.github.vm.patlego.ocr.utils.OCRConversionResult;
import io.github.vm.patlego.ocr.utils.impl.SimpleConversionResult;

@Component(immediate = true, service = OCRService.class, property = {"OCR_INPUT_TYPE=PDF", "OCR_OUTPUT_TYPE=TXT", "RENDERER=TIKA" })
public class PDFTikaServiceText implements OCRService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // This is the key used to store the document metadata within the map that represents the metadata from the OCR conversion
    public final static String TIKA_METADATA = "TIKA_PDF_METADATA"; 

    @Override
    public OCRConversionResult performOCR(OCRConversionInput input)
            throws FailedOCRException, InterruptedException, ExecutionException {
        BodyContentHandler handler = new BodyContentHandler();

        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();

        // parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();

        try {
            pdfparser.parse(input.getInputStream(), handler, metadata, pcontext);
            SimpleConversionResult result = new SimpleConversionResult(
                            new ByteArrayInputStream(
                                handler.toString().getBytes()),
                            ContentTypes.TXT);
            
            result.addMetadaParam(PDFTikaServiceText.TIKA_METADATA, metadata);
            return result;
        } catch (IOException | SAXException | TikaException e) {
            logger.error(String.format("Caught a PDF Parsing exception when using Tika to parse incoming stream in %s",
                    this.getClass().getName()), e);
            throw new FailedOCRException(
                    String.format("Caught a PDF Parsing exception when using Tika to parse incoming stream in %s",
                            this.getClass().getName()),
                    e);
        }
    }

    /**
     * Function used to decide if the Tika execution can happen or not
     * 
     * Note if the incoming ContentType is not PDF then the document cannot be OCR
     * 
     * @throws FailedOCRException
     */
    private void failedTika(OCRConversionInput input) throws FailedOCRException {
        if (input == null) {
            throw new FailedOCRException("Conversion Input object cannot be null");
        }

        if (!input.getContentType().equals(ContentTypes.PDF)) {
            throw new FailedOCRException(String.format("Conversion Input can only be of type PDF %s", PDFTikaServiceText.class.getName()));
        }
    }
    
    @Override
    public Boolean validateParameters(OCRConversionInput document) {
        try {
            this.failedTika(document);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}