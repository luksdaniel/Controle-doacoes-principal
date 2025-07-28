package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.service.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.Role;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.service.instituicao.InstituicaoServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.PasswordDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.service.doador.DoadorServiceBase;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioService implements UsuarioServiceBase {

    UsuarioRepository usuarioRepository;
    InstituicaoServiceBase instituicaoServiceBase;
    DoadorServiceBase doadorServiceBase;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, InstituicaoServiceBase instituicaoServiceBase, DoadorServiceBase doadorServiceBase) {
        this.usuarioRepository = usuarioRepository;
        this.instituicaoServiceBase = instituicaoServiceBase;
        this.doadorServiceBase = doadorServiceBase;
    }

    @Override
    public List<Usuario> findAllUsuario() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        if (usuarioList.isEmpty()){
            throw new ResourceNotFoundException("Usuários não encontrados");
        }else{
            return usuarioList;
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(!usuario.isPresent()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Optional<Usuario> findByDoadorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findByDoadorId(id);
        if(!usuario.isPresent()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Optional<Usuario> findByUserName(String username) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if(!usuario.isPresent()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        setaAtributosIniciais(usuario);

        if(usuario.getInstituicao() != null && usuario.getDoador() == null)
            usuario.setInstituicao(instituicaoServiceBase.findById(usuario.getInstituicao().getId()).get());
        else if(usuario.getInstituicao() == null && usuario.getDoador() != null) {
            usuario.setDoador(doadorServiceBase.findById(usuario.getDoador().getId()).get());
            usuario.setRole(Collections.singleton(Role.ROLE_DOADOR));
        }

        validaUsuarioLoginDuplicado(usuario);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsername(String newUsername, long id) {
        Optional<Usuario> usuarioOptional = findById(id);

        usuarioOptional.get().setUsername(newUsername);
        validaUsuarioLoginDuplicado(usuarioOptional.get());

        return usuarioRepository.save(usuarioOptional.get());
    }

    @Override
    public Usuario updatePassword(PasswordDto passwordDto, long id) {
        Optional<Usuario> usuarioOptional = findById(id);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(passwordDto.getPassword());

        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword()))
            throw new BusinessException("As novas senhas não coincidem");

        if (usuarioOptional.get().getPassword().equals(encodedPassword))
            throw new BusinessException("A nova senha é igual à antiga!");

        usuarioOptional.get().setPassword(encodedPassword);

        return usuarioRepository.save(usuarioOptional.get());
    }

    @Override
    public void deleteUsuario(Long id) {
        verificaUsuarioJaCadastrado(id);
        usuarioRepository.deleteById(id);
    }

    private void verificaUsuarioJaCadastrado(Long id){
        if (!usuarioRepository.findById(id).isPresent())
            throw new ResourceNotFoundException("Usuário não encontrado");
    }

    private void setaAtributosIniciais(Usuario usuario){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usuario. getPassword());
        usuario.setPassword(encodedPassword);

        usuario.setCancelado(false);
        usuario.setDataCadastro(LocalDate.now());
    }

    private void validaUsuarioLoginDuplicado(Usuario usuario){
        Optional<Usuario> entity = usuarioRepository.findByUsername(usuario.getUsername());

        if(entity.isPresent()){
            throw new BusinessException("Já existe um usuário cadastrado com login: "+usuario.getUsername());
        }
    }
}
