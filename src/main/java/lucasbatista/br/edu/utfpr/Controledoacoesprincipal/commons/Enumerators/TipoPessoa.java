package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators;

public enum TipoPessoa {

    PESSOA_FISICA("Pessoa Fisíca"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    private final String descricao;

    TipoPessoa(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }

}
