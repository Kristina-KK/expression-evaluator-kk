package com.kkraljic.task_express_eval.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {

    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;

}
