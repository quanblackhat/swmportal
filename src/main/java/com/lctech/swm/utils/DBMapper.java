package com.lctech.swm.utils;

import com.lctech.swm.domain.Price;
import com.lctech.swm.domain.PriceLevel;

import java.util.List;

/**
 * Convert data from Domain Object
 */
public class DBMapper {
    public static PriceLevel[] getPriceLevelArray(List<Price> prices){
        int i, length = prices.size();
        PriceLevel[] priceLevels = new PriceLevel[length];
        for(i = 0; i < length; i++){
            PriceLevel priceLevel = new PriceLevel();
            priceLevel.setStart(prices.get(i).getStartM2());
            priceLevel.setEnd(prices.get(i).getEndM2());
            priceLevel.setPrice(prices.get(i).getPrice());
            priceLevels[i] = priceLevel;
        }
        return priceLevels;
    }
}
