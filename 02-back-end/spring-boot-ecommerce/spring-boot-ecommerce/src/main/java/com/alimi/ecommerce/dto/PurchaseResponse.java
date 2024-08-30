package com.alimi.ecommerce.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PurchaseResponse {

    @NonNull //either final or add NonNull
    private String orderTrackingNumber;
}
