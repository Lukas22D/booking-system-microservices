package com.sistemareseva.service_user.config.component;

import com.sistemareseva.service_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
    }
}