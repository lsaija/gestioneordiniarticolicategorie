package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {
	public Ordine findByIdFetchingArticoli(Long idOrdine) throws Exception;

	public List<Ordine> findAllOrdiniByCategoria(Categoria categoriaInput) throws Exception;

	public List<Categoria> findAllCategorieDistinteByOrdine(Ordine ordineInput) throws Exception;

	public Ordine findOrdineBySpedizionePiuRecenteCategoria(Categoria categoriaInput) throws Exception;

	public List<String> findIndirizziByNumeroSerialeCon(String input) throws Exception;

}
