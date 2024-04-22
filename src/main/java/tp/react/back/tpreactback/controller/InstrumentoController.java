package tp.react.back.tpreactback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.react.back.tpreactback.modelo.Instrumento;
import org.springframework.beans.factory.annotation.Autowired;
import tp.react.back.tpreactback.services.InstrumentoService;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
public class InstrumentoController {

    @Autowired
    private InstrumentoService instruServ;


    @GetMapping("/Instrumento/traer-lista")
    public List<Instrumento> getInstrumento(){
        return  instruServ.getInstrumento();
    }
    @GetMapping("/Instrumento/traer/{id}")
    public Instrumento getInstrumento(@PathVariable long id){
        return instruServ.obtenerInstrumento(id);
    }

    @PostMapping("/cargar-instrumentos")
    public ResponseEntity<String> cargarInstrumentosDesdeJson(@RequestParam String rutaArchivo) {
        System.out.println("Recibida la solicitud para cargar instrumentos desde el archivo: " + rutaArchivo);
        instruServ.cargarInstrumentosDesdeJson(rutaArchivo);
        return ResponseEntity.ok("Datos cargados exitosamente en la base de datos.");
    }

}

