package lucasbatista.br.edu.utfpr.Controledoacoesprincipal.commons.Enumerators;

public enum TipoItem {

    ALIMENTO_PERECIVEL("Alimento perecível"),
    ALIMENTO_NAO_PERECIVEL("Alimento não perecível"),
    CESTA_BASICA("Cesta básica"),
    ROUPA("Roupa comum"),
    AGASALHO("Agasalho"),
    COBERTOR("Cobertor"),
    DINHEIRO("Dinheiro"),
    OUTRO("Outro");
    private String descricao;

    TipoItem(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }

}
