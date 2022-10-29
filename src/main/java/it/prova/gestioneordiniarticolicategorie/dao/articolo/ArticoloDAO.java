package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo>{

	 public Articolo findByIdFetchingCategorie(Long idArticolo) throws Exception;

	 public void deleteArticoliFromOrder(Long idOrdine) throws Exception;

	 public void deleteCategoryFromArticolo(Long idCategoria,Long idArticolo) throws Exception;
	
}
