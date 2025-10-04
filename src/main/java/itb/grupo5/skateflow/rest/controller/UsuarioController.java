package itb.grupo5.skateflow.rest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itb.grupo5.skateflow.model.entity.Usuario;
import itb.grupo5.skateflow.rest.exception.ResourceNotFoundException;
import itb.grupo5.skateflow.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        super();
        this.usuarioService = usuarioService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Olá, Usuario!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id) {

        Usuario usuario = usuarioService.findById(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado!");
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Usuario usuario) {

        Usuario _usuario = usuarioService.save(usuario);
        if (_usuario != null) {
            return ResponseEntity.ok("Usuário cadastrado com sucesso");
        }

        throw new ResourceNotFoundException("Usuário já cadastrado");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        Usuario _usuario = usuarioService.login(usuario.getEmail(), usuario.getSenha());
        if (_usuario != null) {
            return ResponseEntity.ok(_usuario);
        }

        throw new ResourceNotFoundException("Dados Incorretos!");
    }

    @PutMapping("/alterarSenha/{id}")
    public ResponseEntity<?> alterarSenha(@PathVariable long id, @RequestBody Usuario usuario) {

        Usuario _usuario = usuarioService.alterarSenha(id, usuario);
        if (_usuario != null) {
            return ResponseEntity.ok().body("Senha alterado com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao alterar a senha!");
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<?> inativar(@PathVariable long id) {

        Usuario _usuario = usuarioService.inativar(id);
        if (_usuario != null) {
            return ResponseEntity.ok().body("Conta de usuário inativa com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao inativar a conta de usuário!");
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<?> reativar(@PathVariable long id) {

        Usuario _usuario = usuarioService.reativar(id);
        if (_usuario != null) {
            return ResponseEntity.ok().body("Conta de usuário ativada com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao ativar a conta de usuário!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        boolean deleted = usuarioService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        }
        throw new ResourceNotFoundException("Erro ao deletar usuário!");
    }

    // Novo endpoint para atualizar o usuário
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {

        Usuario _usuario = usuarioService.atualizarUsuario(id, usuario);
        if (_usuario != null) {
            return ResponseEntity.ok().body("Usuário atualizado com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao atualizar o usuário!");
    }
    
    // Endpoint para atualizar perfil do usuário
    @PutMapping("/perfil/{id}")
    public ResponseEntity<?> atualizarPerfil(@PathVariable long id, @RequestBody Usuario usuario) {
        try {
            Usuario _usuario = usuarioService.atualizarPerfil(id, usuario);
            if (_usuario != null) {
                return ResponseEntity.ok(_usuario);
            }
            throw new ResourceNotFoundException("Usuário não encontrado!");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao atualizar o perfil: " + e.getMessage());
        }
    }
    
    @PutMapping("/alterarSenhaComVerificacao/{id}")
    public ResponseEntity<?> alterarSenhaComVerificacao(@PathVariable long id, @RequestBody Map<String, String> senhas) {
        String senhaAtual = senhas.get("senhaAtual");
        String novaSenha = senhas.get("novaSenha");
        
        boolean alterada = usuarioService.alterarSenhaComVerificacao(id, senhaAtual, novaSenha);
        if (alterada) {
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.badRequest().build();
    }
    

}
