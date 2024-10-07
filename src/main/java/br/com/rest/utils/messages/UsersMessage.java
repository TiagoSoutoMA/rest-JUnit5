package br.com.rest.utils.messages;

public class UsersMessage {

    public static final String SUCESS = "Cadastro realizado com sucesso";
    public static final String EMPTY_NAME = "nome não pode ficar em branco";
    public static final String EMPTY_EMAIL = "email não pode ficar em branco";
    public static final String EMPTY_PASSWORD = "password não pode ficar em branco";
    public static final String INCORRECT_ADMINISTRATOR = "administrador deve ser 'true' ou 'false'";
    public static final String DUPLICATED_EMAIL = "Este email já está sendo usado";
    public static final String INVALID_EMAIL = "email deve ser um email válido";
    public static final String USER_NOT_FOUND = "Usuário não encontrado";
    public static final String RECORD_CHANGED = "Registro alterado com sucesso";
    public static final String DELETE_SUCESS = "Registro excluído com sucesso";
    public static final String DELETE_FAIL = "Nenhum registro excluído";
    public static final String DELETE_FAIL_CART = "Não é permitido excluir usuário com carrinho cadastrado";
}
