package com.mobiquityinc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * This is the DTO class which represents the consignment with capacity and list of consignment items
 */
@Getter
@AllArgsConstructor
@ToString
public class Consignment {
    protected Integer weight;

    protected List<Item> items;
}
