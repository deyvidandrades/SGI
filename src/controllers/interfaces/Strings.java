package controllers.interfaces;

public interface Strings {
    //STRINGS DO PROGRAMA//
    String SGI = "SGI";
    String VERSAO = "0.1.7";
    String DESCRICAO = "SGI - Sistema de Gestão de Imobiliárias";

    //STRINGS DE GUI//
    String OLA = "Olá";
    String BEM_VINDO = "Seja bem vindo ao SGI!";
    String LOGIN = "Login";
    String CADASTRAR_CLIENTE = "Cadastrar Cliente";
    String ALTERAR_CLIENTE = "Alterar Cliente";
    String CADASTRAR_IMOVEL = "Cadastrar Imóvel";
    String ALTERAR_IMOVEL = "Alterar Imóvel";
    String CADASTRAR_CONTRATO = "Cadastrar Contrato";
    String ALTERAR_CONTRATO = "Alterar Contrato";
    String CADASTRAR_FUNCIONARIO = "Cadastrar Funcionario";
    String ALTERAR_FUNCIONARIO = "Alterar Funcionario";
    String CONFIGURACOES = "Configurações";
    String EXTENSAO_CONTRATO = "Extensão (Anos)";
    String EXTENSAO_VIGENCIA_CONTRATO = "Vigência";
    String MENU_TOPO = "Topo";
    String MENU_ESQUERDA = "Esquerda";
    String IMOVEIS = "Imóveis";
    String CLIENTES = "Clientes";
    String CONTRATOS = "Contratos";
    String GERENTE = "Gerente";
    String FUNCIONARIO = "Funcionário";
    String FOOTER = "por Deyvid Andrade e Klebrer Halley";


    //CABECALHOS DE TABELAS//
    String[] COLUNAS_CONTRATOS = {"ID", "Proprietário", "Endereço", "Valor", "Contratante", "CPF Contratante", "Data Criação", "Data Fim", "Tipo"};
    String[] COLUNAS_CLIENTES = {"ID", "Nome", "CPF", "Email", "Renda"};
    String[] COLUNAS_IMOVEIS = {"ID", "Endereço", "Proprietário", "Valor Locação", "Valor Venda", "Quartos", "Banheiros", "Tipo", "Disponível"};
    String[] COLUNAS_FUNCIONARIOS = {"ID", "Nome", "CPF", "Email", "Cargo"};


    //CAMINHOS DE DADOS//
    String DADOS_RAIZ = "dados";
    String DADOS_CONTRATOS = "contratos";
    String DADOS_IMOVEIS = "imoveis";
    String DADOS_CLIENTES = "clientes";
    String DADOS_FUNCIONARIOS = "funcionarios";
    String DADOS_CONFIGURACOES = "configuracoes";

    //MENSAGENS//
    String MENSAGEM_LOGIN_INVALIDO = "Nome ou senha inválido.";
    String MENSAGEM_ERRO = "Ocorreu um erro ao realizar a operação. A operação foi cancelada.";
    String MENSAGEM_ERRO_REMOCAO = "Não foi possível remover o registro selecionado.";
    String MENSAGEM_ERRO_REMOCAO_FUNCIONARIO_BASE = "Esse funcionário não pode ser removido!";
    String MENSAGEM_DADOS_INVALIDOS = "Dados inválidos. Verifique as informações e tente novamente.";
    String MENSAGEM_CLIENTE_EXISTENTE = "Não foi possível adicionar o cliente. (Cliente já existe)";
    String MENSAGEM_IMOVEL_EXISTENTE = "Não foi possível adicionar o cliente. (Imóvel já existe)";
    String MENSAGEM_FUNCIONARIO_EXISTENTE = "Não foi possível adicionar o cliente. (Funcionário já existe)";
    String MENSAGEM_FUNCIONARIO_NAO_ATUALIZDO = "Não foi possível atualizar o funciário";
    String MENSAGEM_CONTRATO_EXISTENTE = "Não foi possível adicionar o contrato. (Contrato já existe)";
    String MENSAGEM_CONTRATO_EM_VIGENCIA_CLIENTE = "Não é possível realizar a operação (Cliente possui contrato em vigencia)";
    String MENSAGEM_CONTRATO_EM_VIGENCIA_IMOVEL = "Não é possível realizar a operação (Imóvel possui contrato em vigencia)";

    //ARQUIVOS//
    String DADOS = "src/model/dados/dados.json";
    String ICONE_32 = "src/views/imagens/icon-32.png";
    String ICONE_64 = "src/views/imagens/icon-96.png";
    String ICONE_ADD = "src/views/imagens/add-16.png";
    String ICONE_BUSCAR = "src/views/imagens/search-16.png";
    String ICONE_SAIR = "src/views/imagens/exit-16.png";
    String ICONE_CONFIGURACOES = "src/views/imagens/settings-16.png";
    String ICONE_ATUALIZAR = "src/views/imagens/refresh-16.png";

}
