package com.kkraljic.task_express_eval.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonRootName("customer")
public class CustomerDto {

    private String firstName;
    private String lastName;
    private AddressDto address;
    private Integer salary;
    private String type;

}
