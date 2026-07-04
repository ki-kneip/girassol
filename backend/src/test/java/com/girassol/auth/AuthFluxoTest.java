package com.girassol.auth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.girassol.TestcontainersConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class AuthFluxoTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void rotaProtegidaSemSessaoRetorna401ComJson() throws Exception {
        mockMvc.perform(get("/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.erro").value("nao_autenticado"));
    }

    @Test
    void loginComCredenciaisErradasRetorna401() throws Exception {
        mockMvc.perform(post("/auth/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "ninguem@girassol.dev", "senha": "qualquer-coisa"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.erro").value("credenciais_invalidas"));
    }

    @Test
    void cadastroInvalidoRetorna400() throws Exception {
        mockMvc.perform(post("/auth/cadastro").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome": "Gil", "email": "gil@girassol.dev", "senha": "curta"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("dados_invalidos"));
    }

    @Test
    void fluxoCompletoCadastroLoginMeLogout() throws Exception {
        mockMvc.perform(post("/auth/cadastro").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome": "Fábio", "email": "fabio@girassol.dev", "senha": "senha-forte"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Fábio"));

        var loginResult = mockMvc.perform(post("/auth/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email": "fabio@girassol.dev", "senha": "senha-forte"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("fabio@girassol.dev"))
                .andReturn();

        // Com Spring Session, a sessão viaja no cookie SESSION — como num browser de verdade
        var cookieSessao = loginResult.getResponse().getCookie("SESSION");

        mockMvc.perform(get("/me").cookie(cookieSessao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fábio"));

        mockMvc.perform(post("/auth/logout").with(csrf()).cookie(cookieSessao))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/me").cookie(cookieSessao))
                .andExpect(status().isUnauthorized());
    }
}
