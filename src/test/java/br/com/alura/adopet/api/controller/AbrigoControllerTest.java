package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AbrigoController.class)
class AbrigoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    Gson gson = new Gson();

    @MockBean
    private AbrigoRepository abrigoRepository;

    @Test
    void deveRetornarListaDeAbrigosComSucesso200() throws Exception {
        List<AbrigoDto> abrigoDtos = List.of(
                new AbrigoDto(1L, "Abrigo 1"),
                new AbrigoDto(2L, "Abrigo 2"),
                new AbrigoDto(3L, "Abrigo 3"),
                new AbrigoDto(4L, "Abrigo 4")
        );
        given(abrigoService.listar()).willReturn(abrigoDtos);


        mockMvc.perform(get("/abrigos"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            [
                                {"id":1, "nome":"Abrigo 1"},
                                {"id":2, "nome":"Abrigo 2"},
                                {"id":3, "nome":"Abrigo 3"},
                                {"id":4, "nome":"Abrigo 4"}
                            ]
                        """));
    }

    @Test
    void deveCadastrarComSucesso200() throws Exception {
        CadastroAbrigoDto cadastroAbrigoDto =
                new CadastroAbrigoDto("Nome de Teste", "(12)4568-5847", "meuemail@email.com");
        doNothing().when(abrigoService).cadastrar(cadastroAbrigoDto);


        mockMvc.perform(post("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cadastroAbrigoDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaCadastrarComErro() throws Exception {
        CadastroAbrigoDto cadastrarAbrigoDtoErrado =
                new CadastroAbrigoDto("Nome de Tete", "12345", "meuemailemailcom");
        doNothing().when(abrigoService).cadastrar(cadastrarAbrigoDtoErrado);

        mockMvc.perform(post("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cadastrarAbrigoDtoErrado)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void deveRetornarListaDePetsDoAbrigoComSucesso() throws Exception {
        List<PetDto> pets = List.of(
                new PetDto(1L, TipoPet.CACHORRO, "Rex", "Pit", 12),
                new PetDto(2L, TipoPet.CACHORRO, "LuLo", "Labrador", 12)
        );
        given(abrigoService.listarPetsDoAbrigo("Abrigo Esperança")).willReturn(pets);

        mockMvc.perform(get("/abrigos/Abrigo Esperança/pets"))
                .andExpect(content().json(gson.toJson(pets)))
                .andExpect(status().isOk());

        verify(abrigoService).listarPetsDoAbrigo("Abrigo Esperança");
        System.out.println("Pets mockados: " + gson.toJson(pets));

    }


}