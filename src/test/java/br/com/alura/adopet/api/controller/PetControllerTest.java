package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService service;

    @Test
    void listarTodosDisponiveis_DeveRetornarListaDePets() throws Exception {
        List<PetDto> pets = new LinkedList<PetDto>();
        pets.add(new PetDto(1L, TipoPet.CACHORRO, "Rex", "Labrador", 3));
        pets.add(new PetDto(2L, TipoPet.CACHORRO, "Dog", "Labrador", 3));
        when(service.buscarPetsDisponiveis()).thenReturn(pets);


        mvc.perform(get("/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                [
                                                  {\"id\":1,\"tipo\":\"CACHORRO\",\"nome\":\"Rex\",\"raca\":\"Labrador\",\"idade\":3},
                                                  {\"id\":2,\"tipo\":\"CACHORRO\",\"nome\":\"Dog\",\"raca\":\"Labrador\",\"idade\":3}
                    
                                                ]
                        
                        """));
    }
}

