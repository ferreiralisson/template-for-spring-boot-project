package com.example.demo.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Constants {

    public static final String API_URL_PREFIX = "smart-time/v1";
    public static final String[] PUBLIC_MATCHERS = { API_URL_PREFIX + "/clients/**" };
    public static final String[] PUBLIC_MATCHERS_POST = { API_URL_PREFIX + "/auth/forgot/**" };
    public static final String[] PUBLIC_MATCHERS_GET = { "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
            "/**/*.css", "/**/*.js" , "/actuator/**"};

    public static final String $ROLE_ADMIN = "ROLE_ADMIN";
    public static final String $ROLE_USER = "ROLE_USER";
    public static final String SECURITY_ROLE_ADMIN = "hasAnyRole('" + $ROLE_ADMIN + "')";
    public static final String SECURITY_ROLE_USER = "hasAnyRole('ROLE_ADMIN','" + $ROLE_USER + "')";

    public static final String MESSAGE_RECOVERY_PASSWORD_BODY = "\n\nComo mudar minha senha:\n "
            + "1. Com sua nova senha em mãos, entre no APP ---- e faça seu login\n "
            + "2. Vá no menu 'Configurações' com seu perfil\n "
            + "3. Clique em alterar senha\n "
            + "4. Digite a senha atual (a que está nesse e-mail) e sua nova senha 2 (duas) vezes\n "
            + "5. O aplicativo irá deslogar e você poderá entrar com  sua nova senha que acabou de cadastrar.\n\n"
            + "E-mail gerado automaticamente, favor não responder\n"
            + "Caso não tenha solicitado esse serviço, para sua segurança entre em contato a administração da Smart Time pelo Grupo Arruda Paes.\n\n"
            + "Grato.\n"
            + "A Administração";

    public static final String SUBJECT_EMAIL = "Nova Senha - Smart Time";

    public static final String MESSAGE_RECOVERY_PASSWORD_TITLE = "Prezado cliente, como solicitado, segue abaixo uma nova senha de acesso para o APP ----.\n\n Nova Senha: ";

    public  static final Long ACTIVE = Long.valueOf("1");
    public  static final Long INACTIVE = Long.valueOf("0");
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String TIME_FORMAT_DEFAULT = "HH:mm:ss";
    public  static final String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
}
