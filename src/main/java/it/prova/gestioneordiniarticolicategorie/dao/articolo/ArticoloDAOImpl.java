package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class ArticoloDAOImpl implements ArticoloDAO{
	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Articolo findByIdFetchingCategorie(Long idArticolo) {
		TypedQuery<Articolo> query=entityManager.createQuery("from Articolo a left join fetch a.categorie left join fetch a.ordine where a.id = ?1", Articolo.class);
		query.setParameter(1, idArticolo);
		return query.getResultStream().findFirst().orElseGet(null);
	}
	
	
	@Override
	public void deleteArticoliFromOrder(Long idOrdine) throws Exception {
		if (idOrdine < 0) {
			throw new Exception("Problema valore in input");
		}
		entityManager.createNativeQuery("delete from articolo where ordine_id=?1").setParameter(1, idOrdine).executeUpdate();
		
	}
	
	@Override
	public void deleteCategoryFromArticolo(Long idCategoria,Long idArticolo) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where categoria_id=?1 and articolo_id=?2").setParameter(1, idCategoria).setParameter(2, idArticolo)
				.executeUpdate();
		this.delete(this.get(idCategoria));
	}


}
