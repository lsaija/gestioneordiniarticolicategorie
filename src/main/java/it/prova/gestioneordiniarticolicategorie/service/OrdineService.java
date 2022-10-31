package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {
	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Long idOrdine) throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception;
	
	public List<Ordine> cercaOrdiniPerCategoria(Categoria categoriaInput) throws Exception;

	public List<Categoria> cercaCategorieDistintePerOrdine(Ordine ordineInput) throws Exception;

	public Ordine cercaOrdinePerSpedizionePiuRecenteCategoria(Categoria categoriaInput) throws Exception;

	public List<String> cercaIndirizziPerNumeroSerialeCon(String input) throws Exception; 
}
