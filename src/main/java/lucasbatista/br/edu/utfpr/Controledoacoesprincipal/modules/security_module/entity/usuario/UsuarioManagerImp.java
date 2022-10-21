package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.InstituicaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.usuario.UsuarioService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioManagerImp implements UsuarioManager{

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    InstituicaoManager instituicaoManager;

    @Autowired
    DoadorManager doadorManager;

    @Override
    public List<Usuario> findAllUsuario() {
        List<Usuario> usuarioList = usuarioService.findAllUsuario();
        if (usuarioList.isEmpty()){
            throw new ResourceNotFoundException("Usuários não encontrados");
        }else{
            return usuarioList;
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if(usuario.isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Optional<Usuario> findByDoadorId(Long id) {
        Optional<Usuario> usuario = usuarioService.findByDoadorId(id);
        if(usuario.isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Optional<Usuario> findByUserName(String username) {
        Optional<Usuario> usuario = usuarioService.findByUserName(username);
        if(usuario.isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }else {
            return usuario;
        }
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        setaAtributosIniciais(usuario);

        if(usuario.getInstituicao() != null && usuario.getDoador() == null)
            usuario.setInstituicao(instituicaoManager.findById(usuario.getInstituicao().getId()).get());
        else if(usuario.getInstituicao() == null && usuario.getDoador() != null)
            usuario.setDoador(doadorManager.findById(usuario.getDoador().getId()).get());
            //usuario.setDoador(doadorManager.saveDoador(usuario.getDoador()));

        validaUsuarioLoginDuplicado(usuario);

        Usuario usuarioInterno = usuarioService.saveUsuario(usuario);
        if(usuarioInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o usuário");
        }else {
            return usuarioInterno;
        }
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
/*
        if(usuario.getInstituicao() == null && usuario.getDoador() != null)
            usuario.setDoador(doadorManager.saveDoador(usuario.getDoador()));
*/
        validaUsuarioLoginDuplicado(usuario);

        setaAtributosIniciais(usuario);
        verificaUsuarioJaCadastrado(usuario.getId());
        return usuarioService.updateUsuario(usuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        verificaUsuarioJaCadastrado(id);
        usuarioService.deleteUsuario(id);
    }

    private void verificaUsuarioJaCadastrado(Long id){
        if (usuarioService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Usuário não encontrado");
    }

    private void setaAtributosIniciais(Usuario usuario){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);

        usuario.setCancelado(false);
        usuario.setDataCadastro(LocalDate.now());
    }

    private void validaUsuarioLoginDuplicado(Usuario usuario){
        Optional<Usuario> entity = usuarioService.findByUserName(usuario.getUsername());

        if(!entity.isEmpty()){
            throw new BusinessException("Já existe um usuário cadastrado com login: "+usuario.getUsername());
        }
    }
}
