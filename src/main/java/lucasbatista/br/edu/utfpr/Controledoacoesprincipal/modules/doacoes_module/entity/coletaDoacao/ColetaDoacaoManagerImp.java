package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.coletaDoacao;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceCreateErrorException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.ItemManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.usuario.UsuarioManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaDoacao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.DoadorManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.itemColetaDoacao.ItemColetaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.coletaDoacao.ColetaDoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ColetaDoacaoManagerImp implements ColetaDoacaoManager{

    @Autowired
    UsuarioManager usuarioManager;

    @Autowired
    ColetaDoacaoService coletaDoacaoService;

    @Autowired
    DoadorManager doadorManager;

    @Autowired
    ItemManager itemManager;

    @Autowired
    ItemColetaManager itemColetaManager;

    @Override
    public List<ColetaDoacao> findAllColetaDoacao() {
        return null;
    }

    @Override
    public List<ColetaDoacao> findByIdDoador(Long id) {
        return null;
    }

    @Override
    public List<ColetaDoacao> findByIdUsuarioLancamento(Long id) {
        return null;
    }

    @Override
    public Optional<ColetaDoacao> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ColetaDoacao saveColetaDoacao(ColetaDoacao coletaDoacao) {
        setaAtributosIniciais(coletaDoacao);

        ColetaDoacao coletaDoacaoInterna = coletaDoacaoService.saveColetaDoacao(coletaDoacao);
        if(coletaDoacaoInterna == null){
            throw new ResourceCreateErrorException("Não foi possível gravar a coleta de doação!");
        }else {
            gravaItensColeta(coletaDoacao);
            return coletaDoacaoInterna;
        }
    }

    @Override
    public ColetaDoacao updateColetaDoacao(ColetaDoacao coletaDoacao) {
        return null;
    }

    @Override
    public ColetaDoacao cancelaColetaDoacao(ColetaDoacao coletaDoacao) {
        return null;
    }

    @Override
    public ColetaDoacao EfetivaColetaDoacao(ColetaDoacao coletaDoacao) {
        return null;
    }

    private void setaAtributosIniciais(ColetaDoacao coletaDoacao){

        coletaDoacao.setEstaCancelada(false);
        coletaDoacao.setUsuarioRegistro(usuarioManager.findById(coletaDoacao.getDoador().getId()).get());
        coletaDoacao.setDoador(doadorManager.findById(coletaDoacao.getDoador().getId()).get());

        System.out.println(coletaDoacao.getItensColeta());

        List<ItemColetaDoacao> itensDoacao = coletaDoacao.getItensColeta();
        List<ItemColetaDoacao> itensCompletos = new ArrayList<>();

        for (ItemColetaDoacao itemColetaAtual: itensDoacao) {
            itemColetaAtual.setItem(itemManager.findById(itemColetaAtual.getItem().getId()).get());
            itensCompletos.add(itemColetaAtual);
        }

        coletaDoacao.setItensColeta(itensCompletos);
    }

    private void gravaItensColeta(ColetaDoacao coletaDoacao){
        itemColetaManager.saveAllItensColeta(coletaDoacao.getItensColeta());
    }
}
