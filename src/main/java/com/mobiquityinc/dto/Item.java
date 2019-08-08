package com.mobiquityinc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the DTO class which represents the consignment item
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private Integer index;
    private Integer weight;
    private String currency;
    private Integer cost;
}
