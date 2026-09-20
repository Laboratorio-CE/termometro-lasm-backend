package br.com.laboratorioce.termometro.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "admin_user")
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String papel;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "ultimo_login")
    private OffsetDateTime ultimoLogin;

    protected AdminUser() {
    }

    public AdminUser(String email, String senhaHash, String nome, String papel) {
        this.email = email;
        this.senhaHash = senhaHash;
        this.nome = nome;
        this.papel = "ADMIN";
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public String getNome() {
        return nome;
    }

    public String getPapel() {
        return papel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public OffsetDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void registrarLogin() {
        this.ultimoLogin = OffsetDateTime.now();
    }

}
