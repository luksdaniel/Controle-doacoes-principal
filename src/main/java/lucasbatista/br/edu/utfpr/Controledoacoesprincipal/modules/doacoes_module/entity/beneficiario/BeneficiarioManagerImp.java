package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.beneficiario;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.*;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.endereco.EnderecoManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.item.Item;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.pessoa.PessoaManager;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.beneficiario.BeneficiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class BeneficiarioManagerImp implements BeneficiarioManager{

    @Autowired
    PessoaManager pessoaManager;
    @Autowired
    BeneficiarioService beneficiarioService;
    @Autowired
    EnderecoManager enderecoManager;


    @Override
    public List<Beneficiario> findAllBeneficiario() {
        List<Beneficiario> beneficiarioList = beneficiarioService.findAllBeneficiario();

        if (beneficiarioList.isEmpty()){
            throw new ResourceNotFoundException("Beneficiários não encontrados");
        }else {
            return beneficiarioList;
        }
    }

    @Override
    public Optional<Beneficiario> findById(Long id) {
        Optional<Beneficiario> beneficiario = beneficiarioService.findById(id);
        if (beneficiario.isEmpty()){
            throw new ResourceNotFoundException("Beneficiário não encontrado");
        }else {
            return beneficiario;
        }
    }

    @Override
    public Beneficiario saveBeneficiario(Beneficiario beneficiario) {
        pessoaManager.setaAtributosIniciais(beneficiario);
        pessoaManager.criaEnderecoPessoa(beneficiario);

        validaEnderecoExistente(beneficiario);
        validaCaracteristicas(beneficiario);

        pessoaManager.validaCPFeCNPJ(beneficiario);

        Beneficiario beneficiarioInterno = beneficiarioService.saveBeneficiario(beneficiario);
        if(beneficiarioInterno == null){
            throw new ResourceCreateErrorException("Não foi possível criar o beneficiário");
        }else {
            return beneficiarioInterno;
        }
    }

    @Override
    public Beneficiario updateBeneficiario(Beneficiario beneficiario) {
        atualizaEnderecoComId(beneficiario);
        validaEnderecoExistente(beneficiario);
        validaCaracteristicas(beneficiario);

        pessoaManager.validaCPFeCNPJ(beneficiario);
        //pessoaManager.setaAtributosIniciais(beneficiario);
        beneficiario.setDataCadastro(LocalDate.now());

        verificaBeneficiarioJaCadastrado(beneficiario.getId());
        return (beneficiarioService.updateBeneficiario(beneficiario));
    }

    @Override
    public Beneficiario cancelBeneficiario(Long id) {

        Optional<Beneficiario> beneficiario = findById(id);

        if (beneficiario.get().isEstaCancelado())
            throw new BusinessException("Beneficiário já cancelado");

        beneficiario.get().setEstaCancelado(true);

        return (beneficiarioService.updateBeneficiario(beneficiario.get()));
    }

    @Override
    public void deleteBeneficiario(Long id) {
        verificaBeneficiarioJaCadastrado(id);
        beneficiarioService.deleteBeneficiario(id);
    }

    private void validaCaracteristicas(Beneficiario beneficiario){
        if (beneficiario.isPossuiCriancas() && beneficiario.getQuantidadeCriancas() <= 0)
            throw new ResourceIntegrityException("É obrigatório informar a quantidade de crianças, quando marcado que as possui na residencia");

        if(beneficiario.isPossuiIdosos() && beneficiario.getQuantidadeIdosos() <= 0)
            throw new ResourceIntegrityException("É obrigatório informar a quantidade de idosos, quando marcado que os possui na residencia");

    }

    public void atualizaEnderecoComId(Beneficiario beneficiario){
        Optional<Beneficiario> optionalBeneficiario = findById(beneficiario.getId());
        Beneficiario beneficiario1 = optionalBeneficiario.get();
        beneficiario.setEndereco(beneficiario1.getEndereco());
    }

    public void validaEnderecoExistente(Beneficiario beneficiario){
        if (enderecoManager.findById(beneficiario.getEndereco().getId()).isEmpty())
            throw new DependencyNotFoundException("Não foi localizado o endereço para criação do Beneficiário");
    }

    private void verificaBeneficiarioJaCadastrado(Long id){
        if(beneficiarioService.findById(id).isEmpty())
            throw new ResourceNotFoundException("Beneficiário não encontrado");
    }
}
