package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.initializeResource;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.Role;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators.TipoPessoa;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.Endereco;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.instituicao.InstituicaoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class InictializeResource {

    @Autowired
    InstituicaoManager instituicaoManager;

    @Autowired
    UsuarioManager usuarioManager;

    public void createDefaultIntituicao(){
        Optional<Instituicao> instituicaoOptional;

        try {
            instituicaoOptional = instituicaoManager.find();
        }catch (ResourceNotFoundException ex){

            Usuario usuario = new Usuario();
            Instituicao instituicao = new Instituicao();
            Endereco endereco = new Endereco();
            Set<Usuario> usuarioSet= new HashSet<>();

            endereco.setCep("85933000");
            endereco.setBairro("Centro");
            endereco.setComplemento("Centro");
            endereco.setMunicipio("Ouro Verde do Oeste");
            endereco.setUf("PR");
            endereco.setLogradouro("R. Curitiba");
            endereco.setNumero(657);

            instituicao.setEndereco(endereco);
            instituicao.setDataImplantacao(LocalDate.now());
            instituicao.setDiasEntreDoacao(30);
            //instituicao.setUsuarios(usuarioSet);
            instituicao.setHorarioFuncionamento("De segunda à sexta - das 08:00 às 17:00");
            instituicao.setCpfCnpj("00.000.000/0000-00");
            instituicao.setEmail("teste@teste.com");
            instituicao.setNomeFantasia("Instituição padrão");
            instituicao.setRazaoSocial("Instituição padrão");
            instituicao.setTelefone("(00)0000-0000");
            instituicao.setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);

            instituicaoManager.saveInstituicao(instituicao);

            usuario.setUsername("admin");
            usuario.setPassword("admin");
            usuario.setRole(Collections.singleton(Role.ROLE_INSTITUICAO));
            usuario.setDoGoogle(false);
            usuario.setInstituicao(instituicao);
            usuarioSet.add(usuario);

            usuarioManager.saveUsuario(usuario);

        }

    }

}
