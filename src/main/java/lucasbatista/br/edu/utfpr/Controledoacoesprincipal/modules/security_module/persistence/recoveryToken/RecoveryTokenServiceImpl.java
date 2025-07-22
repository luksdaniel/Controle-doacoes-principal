package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.recoveryToken;

import lombok.RequiredArgsConstructor;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.emailSender.EmailService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.BusinessException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.exceptions.ResourceNotFoundException;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.entity.Instituicao;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.base_module.repository.InstituicaoRepository;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.entity.doador.Doador;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.doacoes_module.persistence.doador.DoadorService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.dto.RecoveryCredentialsDto;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.RecoveryToken;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.entity.usuario.Usuario;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.persistence.usuario.UsuarioService;
import lucasbatista.br.edu.utfpr.Controledoacoesprincipal.modules.security_module.repository.RecoveryTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.utils.Constants.RECOVERY_PASSWORD_URL;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecoveryTokenServiceImpl implements RecoveryTokenService {

    private final RecoveryTokenRepository recoveryTokenRepository;
    private final UsuarioService usuarioService;
    private final DoadorService doadorService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final InstituicaoRepository instituicaoRepository;

    @Override
    public void createToken(String email) {
        Optional<Doador> doador = doadorService.findByEmail(email);
        Optional<Instituicao> instituicao = instituicaoRepository.findByEmail(email);
        Usuario usuario = new Usuario();
        Optional<Usuario> userOpt;

        if(!instituicao.isPresent() && !doador.isPresent())
            throw new ResourceNotFoundException("Não foi encontrado usuário com o e-mail: "+email);

        if(doador.isPresent()) {
            userOpt = usuarioService.findByDoadorId(doador.get().getId());
            if (userOpt.isPresent())
                usuario = userOpt.get();
        }

        if(instituicao.isPresent()){
            userOpt = usuarioService.findByInstituicaoId(instituicao.get().getId());
            if (userOpt.isPresent())
                usuario = userOpt.get();

            if (!userOpt.isPresent())
                throw new ResourceNotFoundException("Não foi encontrado usuário com o e-mail: "+email);

        }

        String token = UUID.randomUUID().toString();
        createRecoveryToken(usuario, token);
        sendRecoveryMail(email, token);
    }

    @Override
    public RecoveryToken findToken(String recoveryToken) {
        return recoveryTokenRepository.findByToken(recoveryToken).orElseThrow(() -> new BusinessException("Token not found"));
    }

    @Override
    public void createRecoveryToken(Usuario user, String token) {
        RecoveryToken tk = new RecoveryToken();
        tk.setToken(token);
        tk.setUsuario(user);
        tk.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        recoveryTokenRepository.save(tk);
    }

    @Override
    public void recoverPassword(RecoveryCredentialsDto recoveryCredentials) {
        RecoveryToken tk = findToken(recoveryCredentials.getToken());
        Usuario user = tk.getUsuario();
        user.setPassword(passwordEncoder.encode(recoveryCredentials.getPassword()));
        usuarioService.updateUsuario(user);
    }

    @Override
    public RecoveryToken validateRecoveryToken(RecoveryCredentialsDto recoveryCredentialsDto) {
        Optional<RecoveryToken> token = recoveryTokenRepository.findByToken(recoveryCredentialsDto.getToken());

        if (!token.isPresent())
            throw new BusinessException("Token Inválido para recuperação de senha!");

        if(token.get().getExpiryDate().isBefore(LocalDateTime.now()))
            throw new BusinessException("Token Expirado para recuperação de senha!");

        return token.get();
    }

    private void sendRecoveryMail(String email, String token) {
        String subject = "Recuperação de senha";
        String message = "Para recuperar sua senha, clique no link abaixo:";
        emailService.enviar(email, subject, message + "\r\n" + RECOVERY_PASSWORD_URL + token);
    }

}