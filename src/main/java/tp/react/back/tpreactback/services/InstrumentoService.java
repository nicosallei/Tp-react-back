package tp.react.back.tpreactback.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import tp.react.back.tpreactback.modelo.Instrumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.repository.IInstrumentoRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class InstrumentoService {

    @Autowired
    private IInstrumentoRepository instruRepos;

    public  List<Instrumento> getInstrumento(){
        List<Instrumento> listaInstrumento = instruRepos.findAll();
        return listaInstrumento;
    }

    public Instrumento obtenerInstrumento(long id){
        return instruRepos.findById(id);
    }

    public void cargarInstrumentosDesdeJson(String rutaArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lee el archivo JSON y convierte sus datos en una lista de objetos Instrumento
            Instrumento[] instrumentos = objectMapper.readValue(new File(rutaArchivo), Instrumento[].class);
            // Guarda los instrumentos en la base de datos
            instruRepos.saveAll(Arrays.asList(instrumentos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
