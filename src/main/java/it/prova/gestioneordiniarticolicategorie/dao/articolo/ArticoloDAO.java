package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo findByIdFetchingCategorie(Long idArticolo) throws Exception;

	public void deleteArticoloFromOrder(Long idOrdine, Long idArticolo) throws Exception;

	public void deleteCategoryFromArticolo(Long idCategoria, Long idArticolo) throws Exception;

	public long sumPrezziByCategory(Categoria categoriaInput) throws Exception;

	public long sumPrezziStessoDestinatario(String destinatario) throws Exception;

	 public List<Articolo> findArticoliInErrore();

}
