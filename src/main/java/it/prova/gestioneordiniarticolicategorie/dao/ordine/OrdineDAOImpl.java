package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.exception.CancellazioneOrdineConArticoliException;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {
	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Ordine input) throws Exception {

		if (!input.getArticoli().isEmpty()) {
			throw new CancellazioneOrdineConArticoliException("Non si può eliminare perchè ha articoli");
		}

		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Ordine findByIdFetchingArticoli(Long idOrdine) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine", Ordine.class);
		query.setParameter("idOrdine", idOrdine);
		return query.getResultStream().findFirst().orElseGet(null);
	}

	@Override
	public List<Ordine> findAllOrdiniByCategoria(Categoria categoriaInput) throws Exception {
		if (categoriaInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o join fetch o.articoli a join fetch a.categorie c where c.id = ?1",
				Ordine.class);
		query.setParameter(1, categoriaInput.getId());
		return query.getResultList();
	}

	@Override
	public List<Categoria> findAllCategorieDistinteByOrdine(Ordine ordineInput) throws Exception {
		if (ordineInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c join fetch c.articoli a where a.ordine.id = ?1", Categoria.class);
		query.setParameter(1, ordineInput.getId());
		return query.getResultList();
	}

	@Override
	public Ordine findOrdineBySpedizionePiuRecenteCategoria(Categoria categoriaInput) throws Exception {
		if (categoriaInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o join o.articoli a join a.categorie c where c.id = ?1 order by o.dataSpedizione desc",
				Ordine.class);
		query.setParameter(1, categoriaInput.getId());
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<String> findIndirizziByNumeroSerialeCon(String input) throws Exception {
		if (input.isBlank() || input.equals(null))
			throw new Exception("Problema valore in input");
		TypedQuery<String> query = entityManager.createQuery(
				"select o.indirizzoDiSpedizione from Ordine o join  o.articoli a where a.numeroSeriale like ?1",
				String.class);
		query.setParameter(1, "%" + input + "%");
		return query.getResultList();
	}

}
