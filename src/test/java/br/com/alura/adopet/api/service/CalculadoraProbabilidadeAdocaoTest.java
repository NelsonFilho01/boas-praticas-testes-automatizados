package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CalculadoraProbabilidadeAdocaoTest {

    CalculadoraProbabilidadeAdocao calc = new CalculadoraProbabilidadeAdocao();


    Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
            "Abrigo da alegria",
            "959999999",
            "abrigodaalegria@rothmans.com.br"
    ));

    @Test
    void probabilidadeAltaDeAdocao() {

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.CACHORRO,
                "AuUA",
                "Labrador",
                4,
                "Cinza",
                4.0f
        ), abrigo);

        ProbabilidadeAdocao probabilidadeAdocao = calc.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidadeAdocao);
    }

    @Test
    void probabilidadeMediaAdocao() {

            Pet pet = new Pet(new CadastroPetDto(
                    TipoPet.CACHORRO,
                    "AuUA",
                    "Labrador",
                    15,
                    "Cinza",
                    4.0f
            ), abrigo);

            ProbabilidadeAdocao probabilidadeAdocao = calc.calcular(pet);

            Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidadeAdocao);
        }

        @Test
        void probabilidadeBaixaAdocao() {

            Pet pet = new Pet(new CadastroPetDto(
                    TipoPet.CACHORRO,
                    "AuUA",
                    "Labrador",
                    15,
                    "Cinza",
                    17.0f
            ), abrigo);

            ProbabilidadeAdocao probabilidadeAdocao = calc.calcular(pet);
            Assertions.assertEquals(ProbabilidadeAdocao.BAIXA, probabilidadeAdocao);
        }

    }
