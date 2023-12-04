package com.kkraljic.task_express_eval.repository;

import com.kkraljic.task_express_eval.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, UUID> {
}
