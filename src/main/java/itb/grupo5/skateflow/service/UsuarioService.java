package itb.grupo5.skateflow.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import itb.grupo5.skateflow.model.entity.Usuario;
import itb.grupo5.skateflow.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findById(long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return usuario.get();
        }
        return null;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        Usuario _usuario = usuarioRepository.findByEmail(usuario.getEmail());

        if (_usuario == null) {
            String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());

            usuario.setSenha(senha);
            usuario.setDataCadastro(LocalDateTime.now());
            usuario.setStatusUsuario("ATIVO");

            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public boolean delete(long id) {
        Usuario usuario = findById(id);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
            return true;
        }
        return false;
    }

    @Transactional
    public Usuario login(String email, String senha) {
        Usuario _usuario = usuarioRepository.findByEmail(email);

        if (_usuario != null) {
            if (_usuario.getStatusUsuario().equals("ATIVO")) {
                byte[] decodedPass = Base64.getDecoder().decode(_usuario.getSenha());

                if (new String(decodedPass).equals(senha)) {
                    return _usuario;
                }
            }
        }
        return null;
    }

    @Transactional
    public Usuario alterarSenha(long id, Usuario usuario) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();
            String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());

            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);
            usuarioAtualizado.setStatusUsuario("ATIVO");

            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    @Transactional
    public Usuario inativar(long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        String senhaPadrao = "12345678";

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();
            String senha = Base64.getEncoder().encodeToString(senhaPadrao.getBytes());

            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);
            usuarioAtualizado.setStatusUsuario("INATIVO");

            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    @Transactional
    public Usuario reativar(long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        String senhaPadrao = "12345678";

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();
            String senha = Base64.getEncoder().encodeToString(senhaPadrao.getBytes());

            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);
            usuarioAtualizado.setStatusUsuario("ATIVO");

            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    // Método para atualizar usuário (sem alterar senha)
    @Transactional
    public Usuario atualizarUsuario(long id, Usuario usuario) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();

            if (usuario.getNome() != null) {
                usuarioAtualizado.setNome(usuario.getNome());
            }
            if (usuario.getEmail() != null) {
                usuarioAtualizado.setEmail(usuario.getEmail());
            }
            usuarioAtualizado.setFoto(usuario.getFoto());
            if (usuario.getNivelAcesso() != null) {
                usuarioAtualizado.setNivelAcesso(usuario.getNivelAcesso());
            }
            if (usuario.getStatusUsuario() != null) {
                usuarioAtualizado.setStatusUsuario(usuario.getStatusUsuario());
            }
            
            // Preserva a data de cadastro original
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);

            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }
    
    // Método para atualizar perfil do usuário (apenas nome e foto)
    @Transactional
    public Usuario atualizarPerfil(long id, Usuario usuario) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();

            if (usuario.getNome() != null && !usuario.getNome().trim().isEmpty()) {
                usuarioAtualizado.setNome(usuario.getNome());
            }
            
            // Atualiza a foto sempre (pode ser null para remover)
            usuarioAtualizado.setFoto(usuario.getFoto());
            
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }
    
    @Transactional
    public boolean alterarSenhaComVerificacao(long id, String senhaAtual, String novaSenha) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            
            // Verifica se a senha atual está correta
            byte[] decodedPass = Base64.getDecoder().decode(usuarioAtualizado.getSenha());
            if (!new String(decodedPass).equals(senhaAtual)) {
                return false;
            }
            
            LocalDateTime dataCadastroOriginal = usuarioAtualizado.getDataCadastro();
            String senha = Base64.getEncoder().encodeToString(novaSenha.getBytes());

            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(dataCadastroOriginal);

            usuarioRepository.save(usuarioAtualizado);
            return true;
        }
        return false;
    }
    

}
