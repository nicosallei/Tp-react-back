package tp.react.back.tpreactback.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;
import tp.react.back.tpreactback.modelo.Pedido;
import tp.react.back.tpreactback.modelo.PreferenceMP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    public PreferenceMP getPreferenciaIdMercadoPago(Pedido pedido) {

// Agrega credenciales
        try {
            MercadoPagoConfig.setAccessToken("TEST-4175185833030904-051819-13d87ce6087754b874fd8e357b5f526f-337084866");
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("1234")
                    .title(pedido.getTitulo())
                    .description("Pedido realizado desde el carrito de compras")
                    .pictureUrl("http://picture.com/PS5")
                    .categoryId("Instrumentos")
                    .quantity(1)
                    .currencyId("ARG")
                    .unitPrice(new BigDecimal(pedido.getTotalPedido()))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/success")
                    .failure("http://localhost:8080/failure")
                    .pending("http://localhost:8080/pending")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            PreferenceMP mpPreference = new PreferenceMP();
            mpPreference.setStatusCode(preference.getResponse().getStatusCode());
            mpPreference.setId(preference.getId());


            return mpPreference;


        } catch (
                Exception e) {
            e.printStackTrace();
            return null;

        }

    }
}
