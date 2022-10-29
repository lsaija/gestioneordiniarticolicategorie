package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService {
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Long idCategoria) throws Exception;

	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public Categoria caricaSingoloElementoEagerArticoliCategoria(Long idCategoria) throws Exception;

	public void aggiungiArticoloACategoria(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

	public void rimuoviArticoloDaCategoria(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

}
