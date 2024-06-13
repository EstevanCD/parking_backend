package co.edu.unicauca.iot.parking_backend.capaControladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import co.edu.unicauca.iot.parking_backend.capaAccesoADatos.repositories.UsuarioRepository;
import co.edu.unicauca.iot.parking_backend.capaAccesoADatos.models.Usuario;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping("api/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registroSubmit(Usuario usuario) {
        usuario.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(usuario.getPassword()));
        userRepository.save(usuario);
        return "redirect:/login";
    }
}