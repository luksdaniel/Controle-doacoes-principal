package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.persistence.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class UsuarioManagerImp implements UsuarioManager{

    @Autowired
    UsuarioService usuarioService;

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
    public Usuario saveUsuario(Usuario usuario) {
        setaAtributosIniciais(usuario);

        Usuario usuarioInterno = usuarioService.saveUsuario(usuario);
        if(usuarioInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o usuário");
        }else {
            return usuarioInterno;
        }
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
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
        usuario.setEstaCancelado(false);
        usuario.setDataCadastro(LocalDate.now());
    }
}
