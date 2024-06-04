package tp.react.back.tpreactback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tp.react.back.tpreactback.modelo.Instrumento;
import org.springframework.beans.factory.annotation.Autowired;
import tp.react.back.tpreactback.services.ImagenService;
import tp.react.back.tpreactback.services.InstrumentoService;

import java.io.IOException;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/Instrumento")
public class InstrumentoController {

    @Autowired
    private InstrumentoService instruServ;
    @Autowired
    private ImagenService imagenService;


    @GetMapping("/traer-lista")
    public List<Instrumento> getInstrumento(){
        return  instruServ.getInstrumento();
    }
    @GetMapping("/traer/{id}")
    public Instrumento getInstrumento(@PathVariable long id){
        return instruServ.obtenerInstrumento(id);
    }

    @PostMapping("/cargar-instrumentos")
    public ResponseEntity<String> cargarInstrumentosDesdeJson(@RequestParam String rutaArchivo) {
        System.out.println("Recibida la solicitud para cargar instrumentos desde el archivo: " + rutaArchivo);
        instruServ.cargarInstrumentosDesdeJson(rutaArchivo);
        return ResponseEntity.ok("Datos cargados exitosamente en la base de datos.");
    }

@PostMapping("/guardar")
public Instrumento guardarInstrumento(@RequestPart("instrumento") String instrumentoStr, @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Instrumento instrumento = objectMapper.readValue(instrumentoStr, Instrumento.class);
    if (imagen != null && !imagen.isEmpty()) {
        String imagePath = imagenService.saveImage(imagen);
        instrumento.setImagen(imagePath);
    }
    return instruServ.guardarInstrumento(instrumento);
}

@PutMapping("/actualizar/{id}")
public Instrumento modificarInstrumento(@PathVariable Long id, @RequestPart("instrumento") String instrumentoStr, @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Instrumento instrumento = objectMapper.readValue(instrumentoStr, Instrumento.class);
    if (imagen != null && !imagen.isEmpty()) {
        String imagePath = imagenService.saveImage(imagen);
        instrumento.setImagen(imagePath);
    }
    return instruServ.modificarInstrumento(id, instrumento);
}

    @DeleteMapping("/borrar/{id}")
    public void eliminarInstrumento(@PathVariable long id){
        instruServ.eliminarInstrumento(id);
    }



}

