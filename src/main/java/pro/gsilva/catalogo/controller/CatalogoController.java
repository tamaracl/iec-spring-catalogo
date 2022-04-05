package pro.gsilva.catalogo.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.service.CatalogoService;
import pro.gsilva.catalogo.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/musicas", method = RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/musicas/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/musicas/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaFormEdit(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaForm");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        List<Categoria> listCategoria = categoriaService.findAll();
        mv.addObject("categorias", listCategoria);
        return mv;
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.GET)
    public ModelAndView getMusicaForm(Musica musica) {
        ModelAndView mv = new ModelAndView("musicaForm");
        List<Categoria> listCategoria = categoriaService.findAll();
        mv.addObject("categorias", listCategoria);
        return mv;
    }

    @RequestMapping(value = "/addMusica", method = RequestMethod.POST)
    public ModelAndView salvarMusica(@Valid @ModelAttribute("musica") Musica musica,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            ModelAndView musicaForm = new ModelAndView("musicaForm");
            musicaForm.addObject("mensagem", "Verifique os errors do formulário");

            List<Categoria> listCategoria = categoriaService.findAll();
            musicaForm.addObject("categorias", listCategoria);

            return musicaForm;
            
        } else if (musica.getCategoria().getId() == 0) {
            ModelAndView musicaForm = new ModelAndView("musicaForm");
            musicaForm.addObject("mensagem", "Categoria deve ser informada");

            List<Categoria> listCategoria = categoriaService.findAll();
            musicaForm.addObject("categorias", listCategoria);

            return musicaForm;
        }

        musica.setData(LocalDate.now());
        Categoria categoria = categoriaService.findById(musica.getCategoria().getId());
        musica.setCategoria(categoria);
        catalogoService.save(musica);
        return new ModelAndView("redirect:/musicas");
    }

    @GetMapping("/musicas/pesquisar")
    public ModelAndView pesquisar(@RequestParam("titulo") String titulo) {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findByTitulo(titulo);
        mv.addObject("musicas", musicas);
        return mv;
    }

    @GetMapping("/musicas/pesquisarCategoria")
    public ModelAndView pesquisarCategoria(@RequestParam("categoria") String categoria) {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findByCategoria(categoria);
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value = "/delMusica/{id}", method = RequestMethod.GET)
    public String delMusica(@PathVariable("id") long id) {
        catalogoService.excluir(id);
        return "redirect:/musicas";
    }

}
