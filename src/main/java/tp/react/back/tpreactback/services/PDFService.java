package tp.react.back.tpreactback.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.modelo.Instrumento;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PDFService {

    public ByteArrayInputStream generatePdf(Instrumento instrumento) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            //region Fuentes

            // Crea una fuente de color verde para el texto "Envío Gratis"
            Font greenFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            greenFont.setColor(Color.GREEN);
            // Crea una fuente de color naranja para el costo de envío
            Font orangeFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            orangeFont.setColor(Color.ORANGE);
            // Crea un Chunk para un salto de línea
            Chunk newLine = new Chunk(Chunk.NEWLINE);
            // Crea una fuente de mayor tamaño y en negrita
            Font font = new Font(Font.HELVETICA, 18, Font.BOLD);
            // Crea una fuente de mayor tamaño y en negrita para el título
            Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD);
            // Crea una fuente de color gris
            Font font2 = new Font(Font.HELVETICA, 12, Font.NORMAL);
            font2.setColor(Color.GRAY);

           //endregion

            //region Envio

            Image iconoEnvioGratis = Image.getInstance("camion.png");
            // Ajusta el tamaño de la imagen
            iconoEnvioGratis.scaleAbsolute(20f, 20f);
            // Ajusta estos valores según sea necesario

            Phrase phrase = new Phrase();
            Paragraph costoEnvio;
            if (instrumento.getCostoEnvio().contains("G")) {
            // Agrega la imagen y el texto a la Phrase
            phrase.add(new Chunk(iconoEnvioGratis, 0, 0, true));
            phrase.add(new Chunk(" Envío Gratis", greenFont));
            costoEnvio = new Paragraph();
            costoEnvio.add(phrase);
            } else {
                costoEnvio = new Paragraph("Costo Envío: $ " + instrumento.getCostoEnvio(), orangeFont);
            }
            costoEnvio.setAlignment(Element.ALIGN_LEFT);

            //endregion

            //region Carga de imagen

            // Carga la imagen del instrumento
            Resource resource = new ClassPathResource("images/" + instrumento.getImagen());
            Image image = Image.getInstance(resource.getURL());
            // Posiciona la imagen
            image.setAbsolutePosition(50f, 570f);
            document.add(image);
            //endregion

            //region Texto
            // Crea el texto para el nombre del instrumento con la fuente especificada
            Paragraph nombre = new Paragraph(instrumento.getInstrumento(), titleFont);
            nombre.setAlignment(Element.ALIGN_LEFT);
            // Crea el texto para la cantidad vendida con la fuente especificada
            Paragraph cantidadVendida = new Paragraph(instrumento.getCantidadVendida() + " vendidas", font2);
            cantidadVendida.setAlignment(Element.ALIGN_LEFT);
            // Crea el texto para el precio con la fuente especificada
            Paragraph precio = new Paragraph("$ " + instrumento.getPrecio(), font);
            precio.setAlignment(Element.ALIGN_LEFT);
            // Crea el texto para la marca
            Paragraph marca = new Paragraph("Marca: " + instrumento.getMarca());
            marca.setAlignment(Element.ALIGN_LEFT);
            // Crea el texto para el modelo
            Paragraph modelo = new Paragraph("Modelo: " + instrumento.getModelo());
            modelo.setAlignment(Element.ALIGN_LEFT);
            // Crea el texto para la descripción
            Paragraph descripcion = new Paragraph("Descripción: " + instrumento.getDescripcion());
            descripcion.setAlignment(Element.ALIGN_LEFT); // Cambia la alineación a la izquierda
            //endregion

            //region Posicion de los textos

            // Agrega el texto al documento en posiciones específicas
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(280f, 800f, 570f, 500f); // Ajusta las coordenadas para mover el texto más arriba
            ct.addElement(cantidadVendida);
            ct.addElement(nombre);
            ct.addElement(precio);
            ct.addElement(newLine);
            ct.addElement(marca);
            ct.addElement(modelo);
            ct.addElement(newLine); // Agrega un salto de línea después del modelo
            ct.addElement(costoEnvio);
            ct.go();

            ColumnText ct2 = new ColumnText(writer.getDirectContent());
            ct2.setSimpleColumn(50f, 500f, 570f, 36f); // Ajusta las coordenadas para mover el texto debajo de la imagen
            ct2.addElement(descripcion);
            ct2.go();
            //endregion

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}