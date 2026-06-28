package com.example.ms_seguridad.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ms_seguridad.dto.UsuarioDto;

@FeignClient(name = "MS-USUARIOS")
public interface UsuarioFeignClient {

    @PostMapping("/api/usuarios")
    UsuarioDto crearUsuario(@RequestBody UsuarioDto usuario);

}
