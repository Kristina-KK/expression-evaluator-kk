package com.kkraljic.task_express_eval.mapper;

import com.kkraljic.task_express_eval.dto.ExpressionDto;
import com.kkraljic.task_express_eval.entity.Expression;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ExpressionMapper {

    ExpressionDto entityToDto(Expression expression);

    Expression dtoToEntity(ExpressionDto expressionDto);

}
