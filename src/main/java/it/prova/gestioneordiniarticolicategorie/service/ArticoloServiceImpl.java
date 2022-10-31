package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class ArticoloServiceImpl implements ArticoloService {
	private ArticoloDAO articoloDAO;

	@Override
	public void setArticoloDAO(ArticoloDAO articoloDAO) {
		this.articoloDAO = articoloDAO;
	}

	@Override
	public List<Articolo> listAll() throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			articoloDAO.setEntityManager(entityManager);

			return articoloDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Articolo caricaSingoloElemento(Long id) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			articoloDAO.setEntityManager(entityManager);

			return articoloDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Articolo articoloInstance) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);

			articoloDAO.update(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Articolo articoloInstance) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);

			articoloDAO.insert(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Long idArticolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);

			articoloDAO.delete(articoloDAO.get(idArticolo));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	public Articolo caricaSingoloElementoEagerCategorie(Long idArticolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			articoloDAO.setEntityManager(entityManager);

			return articoloDAO.findByIdFetchingCategorie(idArticolo);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	public void rimozioneArticoloDaOrdine(Long idOrdine, Long idArticolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);
			articoloDAO.deleteArticoloFromOrder(idOrdine, idOrdine);

			entityManager.getTransaction().commit();

		} catch (Exception e) {

			entityManager.getTransaction().rollback();

			e.printStackTrace();
			throw e;

		} finally {

			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void aggiungiCategoriaAdArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();
			articoloDAO.setEntityManager(entityManager);

			articoloInstance = entityManager.merge(articoloInstance);
			categoriaInstance = entityManager.merge(categoriaInstance);

			articoloInstance.getCategorie().add(categoriaInstance);
			articoloDAO.update(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	public void rimuoviCategoriaDaArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {

			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);

			articoloInstance = entityManager.merge(articoloInstance);
			categoriaInstance = entityManager.merge(categoriaInstance);

			articoloDAO.deleteCategoryFromArticolo(categoriaInstance.getId(), articoloInstance.getId());

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
    public long sommaPrezziDiCategoria(Categoria categoriaInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			articoloDAO.setEntityManager(entityManager);

			return articoloDAO.sumPrezziByCategory(categoriaInput);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}
	
	@Override
	public long sommaPrezziStessoDestinatario(String destinatario) throws Exception{
	EntityManager entityManager = EntityManagerUtil.getEntityManager();

	try {

		articoloDAO.setEntityManager(entityManager);

		return articoloDAO.sumPrezziStessoDestinatario(destinatario);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		EntityManagerUtil.closeEntityManager(entityManager);
	}
    }
	
	@Override
	public List<Articolo> cercaArticoliConErrori() throws Exception{
		EntityManager entityManager =EntityManagerUtil.getEntityManager();
		
		try {
			articoloDAO.setEntityManager(entityManager);
			
			return articoloDAO.findArticoliInErrore();
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	
	}
}
