package tp.react.back.tpreactback.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import tp.react.back.tpreactback.modelo.Categoria;
import tp.react.back.tpreactback.modelo.Instrumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.repository.ICategoriaRepository;
import tp.react.back.tpreactback.repository.IInstrumentoRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class InstrumentoService {

    @Autowired
    private IInstrumentoRepository instruRepos;
    @Autowired
    private ICategoriaRepository categoriaRepository;


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

   public Instrumento guardarInstrumento(Instrumento instrumento){
    if (instrumento.getCategoria() != null && instrumento.getCategoria().getId() != 0){
        Categoria categoriaPersistente = categoriaRepository.findById(instrumento.getCategoria().getId()).orElse(null);
        if (categoriaPersistente != null) {
            instrumento.setCategoria(categoriaPersistente);
        }
    }
    return instruRepos.save(instrumento);
}

  public Instrumento modificarInstrumento(Long id, Instrumento instrumento){
    Instrumento instrumentoExistente = instruRepos.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No se encontró el instrumento con el ID: " + id));

    if(instrumento.getInstrumento() != null && !instrumento.getInstrumento().isEmpty()){
        instrumentoExistente.setInstrumento(instrumento.getInstrumento());
    }
    if (instrumento.getMarca() != null && !instrumento.getMarca().isEmpty()){
        instrumentoExistente.setMarca(instrumento.getMarca());
    }

    if(instrumento.getModelo() != null && !instrumento.getModelo().isEmpty()){
        instrumentoExistente.setModelo(instrumento.getModelo());
    }

    if(instrumento.getPrecio() != 0 ){
        instrumentoExistente.setPrecio(instrumento.getPrecio());
    }

    if(instrumento.getCostoEnvio() != null && !instrumento.getCostoEnvio().isEmpty()){
        instrumentoExistente.setCostoEnvio(instrumento.getCostoEnvio());
    }

    if(instrumento.getCantidadVendida() != 0 ){
        instrumentoExistente.setCantidadVendida(instrumento.getCantidadVendida());
    }

    if (instrumento.getDescripcion() != null && !instrumento.getDescripcion().isEmpty()){
        instrumentoExistente.setDescripcion(instrumento.getDescripcion());
    }

    if (instrumento.getImagen() != null && !instrumento.getImagen().isEmpty()){
        instrumentoExistente.setImagen(instrumento.getImagen());
    }

    if (instrumento.getCategoria() != null) {
        instrumentoExistente.setCategoria(instrumento.getCategoria());
    }

    return instruRepos.save(instrumentoExistente);
}

    public void eliminarInstrumento(long id){
        instruRepos.deleteById(id);
    }


}
