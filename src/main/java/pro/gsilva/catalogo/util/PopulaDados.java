package pro.gsilva.catalogo.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.repository.CatalogoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.gsilva.catalogo.repository.CategoriaRepository;

@Component
@Slf4j
public class PopulaDados {

    @Autowired
    private CatalogoRepository catalogoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

//    @PostConstruct
    public void cadastrarMusica() {

        List<Musica> listaMusica = new ArrayList<>();

        Musica musica1 = new Musica();
        Musica musica2 = new Musica();
        Musica musica3 = new Musica();

        musica1.setAutor("Metallica");
        musica1.setData(LocalDate.now());
        musica1.setTitulo("One");
        musica1.setLetra("I can't remember anything, Can't tell if this is true or dream, Deep down inside I feel the scream, This terrible silence stops me");

        musica2.setAutor("AC/DC");
        musica2.setData(LocalDate.now());
        musica2.setTitulo("Back In Black");
        musica2.setLetra("Nobody's gonna get me on another trap, So look at me now, I'm just a makin' my pay, Don't try to push your luck, just get outta my way, 'Cause I'm back! Yes, I'm back!");

        listaMusica.add(musica1);
        listaMusica.add(musica2);

        for (Musica musica : listaMusica) {
            Musica salvarMusica = catalogoRepository.save(musica);
            log.info("Musica de id " + salvarMusica.getId() + " criada");
        }

        List<Categoria> categorias = new ArrayList<>();

        Categoria c1 = new Categoria();
        Categoria c2 = new Categoria();
        Categoria c3 = new Categoria();
        Categoria c4 = new Categoria();
        Categoria c5 = new Categoria();
        Categoria c6 = new Categoria();
        Categoria c7 = new Categoria();
        c1.setNome("categoria 1");
        c2.setNome("categoria 2");
        c3.setNome("categoria 3");
        c4.setNome("categoria 4");
        c5.setNome("categoria 5");
        c6.setNome("categoria 6");
        c7.setNome("categoria 7");
        categorias.add(c1);
        categorias.add(c2);
        categorias.add(c3);
        categorias.add(c4);
        categorias.add(c5);
        categorias.add(c6);
        categorias.add(c7);
        for (Categoria c : categorias) {
            Categoria categoriaSalva = categoriaRepository.save(c);
            log.info("Categoria de id " + categoriaSalva.getId() + " criada");

        }

    }

}