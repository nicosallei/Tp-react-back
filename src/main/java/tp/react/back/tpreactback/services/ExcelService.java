package tp.react.back.tpreactback.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.modelo.Pedido;
import tp.react.back.tpreactback.modelo.PedidoDetalle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Service
public class ExcelService {
    @Autowired
    private PedidoService pedidoService;

    public ByteArrayInputStream pedidosToExcel(LocalDate fechaDesde, LocalDate fechaHasta) throws IOException {
        String[] COLUMNs = {"Fecha Pedido", "Instrumento", "Marca", "Modelo", "Cantidad", "Precio", "Subtotal"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Pedidos");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            List<Pedido> pedidos = pedidoService.getAllPedidosBetween(fechaDesde, fechaHasta);
            for (Pedido pedido : pedidos) {
                for (PedidoDetalle detalle : pedido.getPedidoDetalle()) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(pedido.getFecha().toString());
                    row.createCell(1).setCellValue(detalle.getInstrumento().getInstrumento());
                    row.createCell(2).setCellValue(detalle.getInstrumento().getMarca());
                    row.createCell(3).setCellValue(detalle.getInstrumento().getModelo());
                    row.createCell(4).setCellValue(detalle.getCantidad());
                    row.createCell(5).setCellValue(detalle.getInstrumento().getPrecio());
                    row.createCell(6).setCellValue(detalle.getSubtotal());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
