package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository repository;


    @Test
    void cadastrarQuandoForPossivelCadastrar() {
        given(repository.existsByTelefoneOrEmail("(99)9999-9999", "email@email.com")).willReturn(false);

        tutorService.cadastrar(new CadastroTutorDto("Tutor2", "(99)9999-9999", "email@email.com"));

        verify(repository).save(any(Tutor.class));
    }

    @Test
    void cadastrarQuandoTuturoEstaRepetido() {
        given(repository.existsByTelefoneOrEmail("(99)9999-9999", "email@email.com")).willReturn(true);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> tutorService.cadastrar(new CadastroTutorDto("Tutor", "(99)9999-9999", "email@email.com"))
        );

        assertEquals("Dados já cadastrados para outro tutor!", exception.getMessage());
    }
}