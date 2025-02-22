package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TutorController.class)
class TutorControllerTest {

    @MockBean
    private TutorService service;


    @Autowired
    private MockMvc mockMvc;

    private CadastroTutorDto cadastroTutorDto;
    private CadastroTutorDto cadastroTutorDtoErrado;

    private AtualizacaoTutorDto atualizacaoTutorDto;
    private AtualizacaoTutorDto atualizacaoTutorDtoErrado;

    Gson gson = new Gson();


    @BeforeEach
    void setUp() {
        cadastroTutorDto = new CadastroTutorDto("Nome Tutor", "(12)3456-7789", "email@email.com");
        cadastroTutorDtoErrado = new CadastroTutorDto("Nome Tutor", "123456789", "email@email.com");

        atualizacaoTutorDto = new AtualizacaoTutorDto(1L, "Nome Atualizado", "(85)98765-4321", "novo@email.com");
    }

    @Test
    void deveCdastrarComSucesso200() throws Exception {
        mockMvc.perform(post("/tutores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(cadastroTutorDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveCdastrarComErro400() throws Exception {
        mockMvc.perform(post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cadastroTutorDtoErrado)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarTurorComSucesso200() throws Exception {

        mockMvc.perform(put("/tutores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(atualizacaoTutorDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarTurorComErro400() throws Exception {

        mockMvc.perform(put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson("{\"id\":1,\"nome\":\"Nome Atualizado\",\"telefone\":\"8298765321\",\"email\":\"novo@email.com\"}")))
                .andExpect(status().isBadRequest());
    }

}