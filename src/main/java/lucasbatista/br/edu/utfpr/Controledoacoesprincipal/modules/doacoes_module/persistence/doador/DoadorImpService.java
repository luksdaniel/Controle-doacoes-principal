package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador;

import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceIntegrityException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.InstituicaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoadorImpService implements DoadorService{

    @Autowired
    DoadorRepository doadorRepository;

    @Autowired
    InstituicaoRepository instituicaoRepository;

    @Override
    public List<Doador> findAllDoador() {
        return doadorRepository.findAll();
    }

    @Override
    public List<Doador> findDoadorsByRecebeEmailsTrue() {
        return doadorRepository.findDoadorsByRecebeEmailsTrue();
    }

    @Override
    public Optional<Doador> findById(Long id) {
        return doadorRepository.findById(id);
    }

    @Override
    public Optional<Doador> findByEmail(String email) {
        return doadorRepository.findByEmail(email);
    }

    @Override
    public Doador saveDoador(Doador doador) {
        validaEmailDuplicado(doador);
        return doadorRepository.save(doador);
    }

    @Override
    public Doador updateDoador(Doador doador) {
        validaEmailDuplicado(doador);
        return doadorRepository.save(doador);
    }

    @Override
    public void deleteDoador(Long id) {
        doadorRepository.deleteById(id);
    }

    private void validaEmailDuplicado(Doador doador){
        Optional<Doador> doador1 = doadorRepository.findByEmail(doador.getEmail());
        Optional<Instituicao> instituicao = instituicaoRepository.findByEmail(doador.getEmail());
        if ((doador1.isPresent() && doador.getId() != doador1.get().getId()) ||
                instituicao.isPresent())
            throw new ResourceIntegrityException("Já existe um registro gravado com o e-mail fornecido!");

    }
}
