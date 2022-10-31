package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Categoria input) throws Exception {
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
	public Categoria findByIdFetchingArticoli(Long idCategoria) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", idCategoria);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteArticoloFromCategoria(Long idArticolo, Long idCategoria) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where articolo_id=?1 and categoria_id=?2")
				.setParameter(1, idArticolo).setParameter(2, idCategoria).executeUpdate();
		this.delete(this.get(idCategoria));
	}

	@Override
	public List<String> findAllCodiciFromOrdiniDuranteMeseAnno(java.util.Date dataInput) throws Exception{
		if(dataInput==null)
			throw new Exception("Input non valido");
		
		/*SimpleDateFormat getYearFormat=new SimpleDateFormat("yyyy");
		SimpleDateFormat getMonthFormat=new SimpleDateFormat("MM");
		String year=getYearFormat.format(dataInput);
		String month=getMonthFormat.format(dataInput);
		"select distinct c.codice from Categoria c join  c.articoli a join  a.ordine o where month(o.dataSpedizione) = ?1 and year(o.dataSpedizione) = ?2"
		*/
		
		
		/*LocalDate dataConvertita = dataInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = dataConvertita.getMonthValue();
		int year = dataConvertita.getYear();*/
		
		TypedQuery<String> query = entityManager.createQuery(
				"select distinct(c.codice) from Categoria c join c.articoli a join  a.ordine o where year(o.dataSpedizione) =YEAR(:dataInput) and month(o.dataSpedizione) = MONTH(:dataInput)",
				String.class);
		query.setParameter("dataInput",new java.sql.Date(dataInput.getTime()));	
		return query.getResultList();
	}

}
