package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineServiceImpl implements OrdineService {

	private OrdineDAO ordineDAO;

	@Override
	public void setOrdineDAO(OrdineDAO ordineDAO) {
		this.ordineDAO = ordineDAO;
	}

	@Override
	public List<Ordine> listAll() throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			ordineDAO.setEntityManager(entityManager);

			return ordineDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Ordine caricaSingoloElemento(Long id) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			ordineDAO.setEntityManager(entityManager);

			return ordineDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Ordine ordineInstance) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			ordineDAO.setEntityManager(entityManager);

			ordineDAO.update(ordineInstance);

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
	public void inserisciNuovo(Ordine ordineInstance) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			ordineDAO.setEntityManager(entityManager);

			ordineDAO.insert(ordineInstance);

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
	public void rimuovi(Long idOrdine) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			ordineDAO.setEntityManager(entityManager);

			ordineDAO.delete(ordineDAO.get(idOrdine));

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			ordineDAO.setEntityManager(entityManager);

			return ordineDAO.findByIdFetchingArticoli(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<Ordine> cercaOrdiniPerCategoria(Categoria categoriaInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			ordineDAO.setEntityManager(entityManager);

			return ordineDAO.findAllOrdiniByCategoria(categoriaInput);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<Categoria> cercaCategorieDistintePerOrdine(Ordine ordineInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		try {
			
			ordineDAO.setEntityManager(entityManager);
			
			return ordineDAO.findAllCategorieDistinteByOrdine(ordineInput);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Ordine cercaOrdinePerSpedizionePiuRecenteCategoria(Categoria categoriaInput) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		try {
			
			ordineDAO.setEntityManager(entityManager);
			
			return ordineDAO.findOrdineBySpedizionePiuRecenteCategoria(categoriaInput);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}
	
	@Override
	public List<String> cercaIndirizziPerNumeroSerialeCon(String input) throws Exception{
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		try {
			
			ordineDAO.setEntityManager(entityManager);
			
			return ordineDAO.findIndirizziByNumeroSerialeCon(input);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
