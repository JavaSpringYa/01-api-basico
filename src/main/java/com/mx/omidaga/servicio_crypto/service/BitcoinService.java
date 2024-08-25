package com.mx.omidaga.servicio_crypto.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class BitcoinService {

    @SuppressWarnings("unchecked")
    public String getBitcoinPriceSync(String currency){
        WebClient webClient = WebClient.create("https://api.coindesk.com/v1");
        Map<String, Object> responseMap = webClient.get()
                .uri("/bpi/currentprice.json")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        Map<String, Object> usd = responseMap != null
                ? (Map<String, Object>) searchNestedMap(responseMap, currency) : null;
        if(usd != null){
            return (String) usd.get("rate");
        } else {
            return "USD data not found";
        }
    }
    @SuppressWarnings("unchecked")
    public static Object searchNestedMap(Map<String, Object> map, String key){
        if(map.containsKey(key)){
            return map.get(key);
        }
        for(Map.Entry<String, Object> entry : map.entrySet()){
            Object value = entry.getValue();
            if(value instanceof Map){
                Object result = searchNestedMap((Map<String, Object>) value, key);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }
}
