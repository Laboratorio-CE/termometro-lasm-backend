package br.com.laboratorioce.termometro.admin;

import java.security.SecureRandom;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriarAdminRunner implements ApplicationRunner {

    private static final String ALFABETO = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final AdminUserRepository repository;
    private final PasswordEncoder encoder;
    private final ConfigurableApplicationContext context;

    public CriarAdminRunner(AdminUserRepository repository, PasswordEncoder encoder,
            ConfigurableApplicationContext context) {
        this.repository = repository;
        this.encoder = encoder;
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!args.containsOption("criar-admin")) {
            return;
        }

        String email = args.getOptionValues("criar-admin").getFirst();
        String nome = args.containsOption("nome") ? args.getOptionValues("nome").getFirst() : email;

        if (repository.count() > 0) {
            System.out.println("\nJá existe um usuário administrador. Não é possível criar outro.\n");
            encerrar();
            return;
        }

        String senha = gerarSenha();
        repository.save(new AdminUser(email, encoder.encode(senha), nome, "ADMIN"));

        System.out.println("""

                ==========================================================
                 Administrador criado: %s
                 Senha: %s

                 Guarde agora em um gerenciador de senhas.
                 Ela NÃO pode ser recuperada depois.
                ==========================================================
                """.formatted(email, senha));

        encerrar();
    }

    private String gerarSenha() {
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 42; i++) {
            senha.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return senha.toString();
    }

    private void encerrar() {
        System.exit(SpringApplication.exit(context, () -> 0));
    }

}
