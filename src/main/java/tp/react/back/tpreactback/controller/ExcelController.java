package tp.react.back.tpreactback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tp.react.back.tpreactback.services.ExcelService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/Excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @GetMapping("/download/excel")
public ResponseEntity<byte[]> downloadExcel(@RequestParam("fechaDesde") String fechaDesdeStr,
                                            @RequestParam("fechaHasta") String fechaHastaStr) throws IOException {
    LocalDate fechaDesde = LocalDate.parse(fechaDesdeStr);
    LocalDate fechaHasta = LocalDate.parse(fechaHastaStr);

    ByteArrayInputStream in = excelService.pedidosToExcel(fechaDesde, fechaHasta);
    byte[] bytes = in.readAllBytes();

    HttpHeaders headers = new HttpHeaders();
    String filename = String.format("pedidos(%s-%s).xlsx", fechaDesdeStr, fechaHastaStr);
    headers.add("Content-Disposition", "attachment; filename=" + filename);

    return ResponseEntity
            .ok()
            .headers(headers)
            .body(bytes);
}
}
