package com.springproj.shop.ShoppingApp.services.impls;

import com.springproj.shop.ShoppingApp.services.DataService;

public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "This is Dev DB!";
    }
}
