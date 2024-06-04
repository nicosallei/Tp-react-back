package tp.react.back.tpreactback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp.react.back.tpreactback.modelo.Instrumento;
import tp.react.back.tpreactback.services.InstrumentoService;
import tp.react.back.tpreactback.services.PDFService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/PDF")
public class PDFController {
    @Autowired
    private PDFService pdfService;
    @Autowired
    private InstrumentoService instrumentoService;


@GetMapping(value = "/descarga/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<?> getPdf(@PathVariable long id) {
    try {
        Instrumento instrumento = instrumentoService.obtenerInstrumento(id);
        ByteArrayInputStream pdf = pdfService.generatePdf(instrumento);

        byte[] bytes = pdf.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String filename = instrumento.getInstrumento().replaceAll("\\s+","") + "_" + currentDate + ".pdf";
        headers.add("Content-Disposition", "attachment; filename=" + filename);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}