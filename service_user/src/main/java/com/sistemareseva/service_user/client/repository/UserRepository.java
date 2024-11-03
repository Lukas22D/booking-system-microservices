package com.sistemareseva.service_user.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.sistemareseva.service_user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
     @Query("SELECT u FROM tb_users u WHERE u.cpf = :cpf OR u.phone = :phone OR u.email = :email")
    Optional<User> findByCpfOrPhoneOrEmail(@Param("cpf") String cpf, 
                                           @Param("phone") String phone, 
                                           @Param("email") String email);
}
