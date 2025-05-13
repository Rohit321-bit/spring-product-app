package com.springproj.shop.ShoppingApp.services.impls;

import com.springproj.shop.ShoppingApp.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceImpProd implements DataService {
    @Override
    public String getData() {
        return "This is Prod DB!";
    }
}
