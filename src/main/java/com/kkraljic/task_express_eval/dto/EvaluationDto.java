package com.kkraljic.task_express_eval.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
public class EvaluationDto {

    @NotNull
    private UUID expressionId;

    @NotNull
    private CustomerDto customer;

}
