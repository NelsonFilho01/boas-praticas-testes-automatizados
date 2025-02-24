package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Mock
    private AbrigoRepository abrigoRepository;

    @InjectMocks
    private AbrigoService abrigoService;

    private List<Abrigo> abrigos;


    @BeforeEach
    void setUp() {
        abrigos = List.of(new Abrigo(new CadastroAbrigoDto("Abrigo 1", "(11)1111-1111", "email1@test.com")),
                new Abrigo(new CadastroAbrigoDto("Abrigo 2", "(11)1111-11111", "email2@test.com")));

    }

    @Test
    void deveRetornarListaDeAbrigos() {
        // Arrange
        given(abrigoRepository.findAll()).willReturn(abrigos);

        // Act
        List<AbrigoDto> resultado = abrigoService.listar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Abrigo 1", resultado.get(0).nome());
        assertEquals("Abrigo 2", resultado.get(1).nome());

        verify(abrigoRepository, times(1)).findAll();
    }

    @Test
    void deveCadastrarAbrigo() {
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo 1", "(11)1111-1111", "email1@test.com");

        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email()))
                .willReturn(true);

        assertThrows(ValidacaoException.class, () -> abrigoService.cadastrar(dto));

        verify(abrigoRepository, never()).save(any(Abrigo.class));

    }

    @Test
    void deveSalvarNovoAbrigoSeNaoExistir() {
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo B", "(22)2222-2222", "email2@email.com");

        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email()))
                .willReturn(false);

        abrigoService.cadastrar(dto);

        verify(abrigoRepository, times(1)).save(any(Abrigo.class));
    }

    @Test
    void deveCarregarObrigoCorretamente() {
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto("Abrigo X", "(11)9999-9999", "email@email.com"));
        given(abrigoRepository.findById(1L)).willReturn(Optional.of(abrigo));

        Abrigo resultado = abrigoService.carregarAbrigo("1");

        assertNotNull(resultado);
        assertEquals("Abrigo X", resultado.getNome());
        verify(abrigoRepository, times(1)).findById(1L);
        verify(abrigoRepository, never()).findByNome(anyString());
    }

    @Test
    void deveCarregarComErroNomeDoAbrigo() {
        given(abrigoRepository.findByNome(anyString())).willReturn(Optional.empty());

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> abrigoService.carregarAbrigo("Abrigo 1"));

        assertEquals("Abrigo não encontrado", exception.getMessage());
    }





}
