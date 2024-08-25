package com.mx.omidaga.servicio_crypto.controller;

import com.mx.omidaga.servicio_crypto.service.BitcoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CryptoController {

    private final BitcoinService bitcoinService;

    public CryptoController(BitcoinService bitcoinService){
        this.bitcoinService = bitcoinService;
    }

    @GetMapping(value="/bitcoin-price/{currency}")
    public ResponseEntity<String> getBitcoinPrice(@PathVariable("currency") String currency){
        return new ResponseEntity<>(bitcoinService.getBitcoinPriceSync(currency), HttpStatus.OK);
    }
}
