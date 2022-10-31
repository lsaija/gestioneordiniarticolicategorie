package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	public Categoria findByIdFetchingArticoli(Long idCategoria) throws Exception;

	public void deleteArticoloFromCategoria(Long idArticolo, Long idCategoria) throws Exception;

	public List<String> findAllCodiciFromOrdiniDuranteMeseAnno(Date dataInput) throws Exception;
}
