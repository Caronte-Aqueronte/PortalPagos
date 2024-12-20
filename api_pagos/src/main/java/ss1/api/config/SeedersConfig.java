/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.config;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ss1.api.models.Rol;
import ss1.api.models.Saldo;
import ss1.api.models.Usuario;
import ss1.api.repositories.RolRepository;
import ss1.api.services.UsuarioService;

/**
 * @author Luis Monterroso
 */
@Component
public class SeedersConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioService usuarioService;

    public Rol insertarRol(Rol rol) throws Exception {
        Optional<Rol> opRol = this.rolRepository.findOneByNombre(rol.getNombre());
        if (opRol.isPresent()) {
            return opRol.get();
        }
        return this.rolRepository.save(rol);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {

            // siders roles
            Rol clienteRol = this.insertarRol(new Rol("CLIENTE"));
            Rol adminRol = this.insertarRol(new Rol("ADMIN"));

            // Seeder de usuarios del sistema
            Usuario admin = new Usuario("admin@admin", "admin", "admin", "123", adminRol);
            Usuario cliente1 = new Usuario("empleado77@empresa1.com", "Luis", "Monterroso", "123", clienteRol);
            Usuario cliente2 = new Usuario("empleada90@empresa2.com", "Maria", "Yox", "123", clienteRol);
            Usuario cliente3 = new Usuario("electricShop@gmail.com",
                    "Elvis", "Elvis", "123", clienteRol);
            Usuario cliente4 = new Usuario("mensstyle@gmail.com",
                    "Alex", "Alex", "123", clienteRol);

            try {
                this.usuarioService.crearAdmin(admin);
                this.usuarioService.crearUsuario(cliente1);
                this.usuarioService.crearUsuario(cliente2);
                this.usuarioService.crearUsuario(cliente3);
                this.usuarioService.crearUsuario(cliente4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(SeedersConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
