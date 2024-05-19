package tp.react.back.tpreactback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tp.react.back.tpreactback.modelo.Pedido;
import tp.react.back.tpreactback.modelo.PreferenceMP;
import tp.react.back.tpreactback.services.MercadoPagoService;


@RestController
@RequestMapping("/MercadoPago")
public class MercadoPagoController {
@Autowired
private MercadoPagoService mercadoPagoService;

 @PostMapping("/crear_preference_mp")
    public PreferenceMP crearPreferenceMP(@RequestBody Pedido pedido) {
     try {

         return mercadoPagoService.getPreferenciaIdMercadoPago(pedido);
     } catch (Exception e) {
         e.printStackTrace();
         return null;
     }
 }
}