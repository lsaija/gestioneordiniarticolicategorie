package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class ArticoloDAOImpl implements ArticoloDAO {
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
		TypedQuery<Articolo> query = entityManager.createQuery(
				"from Articolo a left join fetch a.categorie left join fetch a.ordine where a.id = ?1", Articolo.class);
		query.setParameter(1, idArticolo);
		return query.getResultStream().findFirst().orElseGet(null);
	}

	@Override
	public void deleteArticoloFromOrder(Long idOrdine, Long idArticolo) throws Exception {
		if (idOrdine < 0) {
			throw new Exception("Problema valore in input");
		}
		entityManager.createNativeQuery("delete from articolo a where ordine_id=?1 and a.id=?2")
				.setParameter(1, idOrdine).setParameter(2, idArticolo).executeUpdate();

	}

	@Override
	public void deleteCategoryFromArticolo(Long idCategoria, Long idArticolo) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where categoria_id=?1 and articolo_id=?2")
				.setParameter(1, idCategoria).setParameter(2, idArticolo).executeUpdate();
		this.delete(this.get(idCategoria));
	}

	@Override
	public long sumPrezziByCategory(Categoria categoriaInput) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) from Articolo a join a.categorie c where c =:categoria", Long.class);
		query.setParameter("categoria", categoriaInput);
		return query.getSingleResult().longValue();
	}

	@Override
	public long sumPrezziStessoDestinatario(String destinatario) throws Exception {
		if (destinatario.isBlank())
			throw new Exception("Problema  valore in input");
		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) from Articolo a join  a.ordine o on o.nomeDestinatario=?1", Long.class);
		query.setParameter(1, destinatario);
		return query.getSingleResult().longValue();
	}

	@Override
	public List<Articolo> findArticoliInErrore() {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"from Articolo a join fetch a.ordine o  where  o.dataSpedizione>o.dataScadenza  ", Articolo.class);
		return query.getResultList();

	}

}
