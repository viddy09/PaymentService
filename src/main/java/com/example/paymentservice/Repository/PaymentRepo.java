package com.example.paymentservice.Repository;

import com.example.paymentservice.Models.Payment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
    @Override
    <S extends Payment> S save(S entity);
    @NotNull
    @Override
    Optional<Payment> findById(Long aLong);
}
