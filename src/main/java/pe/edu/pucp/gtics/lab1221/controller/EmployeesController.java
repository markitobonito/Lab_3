
package pe.edu.pucp.gtics.lab1221.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/")
public class EmployeesController {
        final UsuariosRepository usuariosRepository;
        final RolRepository rolRepository;
        final EstadoUsuRepository estadoUsuRepository;// Declaración correcta
        // Constructor con inyección de dependencia
        public HomeController(UsuariosRepository usuariosRepository, RolRepository rolRepository, EstadoUsuRepository estadoUsuRepository) {
                this.usuariosRepository = usuariosRepository;
                this.rolRepository      = rolRepository;
                this.estadoUsuRepository   = estadoUsuRepository;
        }

        @GetMapping("/") // Este método maneja la petición GET a la ruta raíz (/)
        public String mostrarPaginaLogin() {
                return "registro/login"; // Devuelve el nombre de la plantilla de login (login.html en templates/registro/)
        }

        //prueba de conexion a base de datos
        @GetMapping("/prueba")
        public String mostrarTodosUsuarios(Model model) {
                List<Usuarios> usuarios = usuariosRepository.findAll(); // Obtiene todos los usuarios
                model.addAttribute("usuarios", usuarios); // Pasa la lista a la vista
                return "hola"; // Usa la misma vista para mostrar todos los DNIs
        }





        @PostMapping("/")
        public String logueo(@RequestParam String username,
                             @RequestParam String password,
                             Model model,
                             HttpSession session
        ) {
                // Intentar por correo
                Optional<Usuarios> opt = usuariosRepository.findByCorreoAndContrasena(username, password);
                // Si no, intentar por DNI (si es numérico)
                if (opt.isEmpty()) {
                        try {
                                int dni = Integer.parseInt(username);
                                opt = usuariosRepository.findByDniAndContrasena(dni, password);
                        } catch (NumberFormatException e) { }
                }

                if (opt.isPresent()) {
                        Usuarios user = opt.get();
                        session.setAttribute("loggedUser", user);

                        // Redirigir según el rol
                        String rolName = user.getRol().getRol();
                        switch (rolName) {
                                case "admininstrador":   return "redirect:/administrador/dashboard";
                                case "vecino": return "redirect:/vecino/home";
                                case "superadmin": return "redirect:/superadmin/home";
                                case "coordinador":return "redirect:/superadmin/coordinador";
                                default:
                                        return "redirect:/";
                        }
                }

                // Falló autenticación
                model.addAttribute("error", "Credenciales inválidas");
                return "login";

        }




        @GetMapping("/registro")
        public String mostrarFormRegistro(Model model) {
                model.addAttribute("usuario", new Usuarios());
                return "registro/registro";        // tu registro.html
        }




        @PostMapping("/registro")
        public String procesarRegistro(
                @RequestParam int dni,
                @RequestParam(required = false) String correo,
                @RequestParam String contrasena,
                @RequestParam String confirmContrasena) {

                if (!contrasena.equals(confirmContrasena)) {
                        // aquí podrías reenviar un error al modelo; para simplificar:
                        return "redirect:/registro?error=diff";
                }

                Usuarios u = new Usuarios();
                u.setDni(dni);
                u.setCorreo(correo);
                u.setContrasena(contrasena);

                // Asignar rol “vecino” y estado “activo”
                u.setRol( rolRepository.findByRol("vecino") );
                u.setEstado( estadoUsuRepository.findByEstado("activo") );

                usuariosRepository.save(u);

                // redirige a tu login.html
                return "redirect:/login";
        }
}
