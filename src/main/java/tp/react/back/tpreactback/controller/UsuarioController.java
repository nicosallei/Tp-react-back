package tp.react.back.tpreactback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.react.back.tpreactback.modelo.UserResponse;
import tp.react.back.tpreactback.modelo.Usuario;
import tp.react.back.tpreactback.repository.IUsuarioRepository;
import tp.react.back.tpreactback.services.UsuarioService;
import tp.react.back.tpreactback.util.InvalidCredentialsException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private  IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public void registrarNuevoUsuario(@RequestBody Usuario usuario) {
        usuarioService.saveUser(usuario);
    }

    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable String username) {
        return usuarioService.findUserByUsername(username);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            UserResponse usuarioEncontrado = usuarioService.login(usuario.getUsername(), usuario.getPassword());
            return ResponseEntity.ok(usuarioEncontrado);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).build();
        }
    }
    @GetMapping("/roles")
    public ResponseEntity<?> obtenerRoles() {
        return ResponseEntity.ok(usuarioService.findAllRoles());
    }
}

