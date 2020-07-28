package patlego.vm.github.io.ocr;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import patlego.vm.github.io.ocr.enums.ContentTypes;
import patlego.vm.github.io.ocr.exceptions.FailedOCRException;
import patlego.vm.github.io.ocr.utils.impl.SimpleConversionResult;

public class TestSimpleConversionOutput {

    InputStream pdfDoc = null;

    @BeforeEach
    public void setup() {
        this.pdfDoc = TestSimpleConversionOutput.class.getResourceAsStream("/ocr/sample.pdf");
    }

    @Test
    public void testConversionOutput() throws IOException, FailedOCRException {
        SimpleConversionResult result = new SimpleConversionResult(this.pdfDoc, ContentTypes.TXT);

        assertNotNull(result.getInputStream());
        assertNotNull(result.getMetadataParameters());
    }

    @Test
    public void testConversionOutputFail() throws IOException, FailedOCRException {

        assertThrows(FailedOCRException.class, () -> {
            new SimpleConversionResult(null, ContentTypes.TXT);
        });

        assertThrows(FailedOCRException.class, () -> {
            new SimpleConversionResult(IOUtils.toInputStream(new String("Testing"), "UTF-8"), null);
        });
    }

    @AfterEach
    public void destroy() throws IOException {
        if (this.pdfDoc != null) {
            this.pdfDoc.close();
        }
    }
    
}