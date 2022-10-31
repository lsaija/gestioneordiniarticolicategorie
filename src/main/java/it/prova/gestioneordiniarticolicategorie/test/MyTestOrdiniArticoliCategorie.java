package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.CancellazioneOrdineConArticoliException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;

public class MyTestOrdiniArticoliCategorie {

	public static void main(String[] args) {
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		try {

			System.out.println("In tabella Ordine ci sono " + ordineServiceInstance.listAll().size() + " elementi.");
			System.out
					.println("In tabella Articolo ci sono " + articoloServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"In tabella Categoria ci sono " + categoriaServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			testInserimentoNuovoOrdine(ordineServiceInstance);
			testAggiornamentoOrdine(ordineServiceInstance);
			testInserimentoArticolo(articoloServiceInstance, ordineServiceInstance);
			testAggiornamentoArticolo(articoloServiceInstance, ordineServiceInstance);
			testRimozioneArticoliDaOrdine(articoloServiceInstance, ordineServiceInstance);
			testInserimentoCategoria(categoriaServiceInstance);
			testAggiornamentoCategoria(categoriaServiceInstance);
			testAggiungereCategoriaAdArticolo(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			testAggiungereArticoloACategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			testRimuovereCategoriaDaArticolo(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			testRimuovereArticoloDaCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);
			testRimuovereOrdineConArticoli(articoloServiceInstance, ordineServiceInstance);
			testCercaOrdiniPerCategoria(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testCercaCategorieDistintePerOrdine(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testCercaOrdinePerDataSpedizionePiuRecenteCategoria(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testSommaPrezziPerUnaCategoria(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testcercaCodiciDegliOrdiniNelMeseAnno(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testSommaPrezziStessoDestinatario(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testCercaIndirizziPerNumeroSerialeCon(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			testCercaArticoliConErrore(articoloServiceInstance,categoriaServiceInstance,ordineServiceInstance);
			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			System.out.println("In tabella Ordine ci sono " + ordineServiceInstance.listAll().size() + " elementi.");
			System.out
					.println("In tabella Articolo ci sono " + articoloServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"In tabella Categoria ci sono " + categoriaServiceInstance.listAll().size() + " elementi.");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoOrdine inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoOrdine fallito ");

		System.out.println(".......testInserimentoNuovoOrdine fine: PASSED.............");
	}

	private static void testAggiornamentoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoOrdine inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoOrdine fallito ");

		LocalDateTime createDateTimeIniziale = ordineInstance.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = ordineInstance.getUpdateDateTime();

		ordineInstance.setDataSpedizione(new Date());
		ordineServiceInstance.aggiorna(ordineInstance);

		if (ordineInstance.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoOrdine fallito: le date di aggiornamento sono disallineate ");

		if (!ordineInstance.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoOrdine fallito: la data di creazione è cambiata ");

		System.out.println(".......testAggiornamentoOrdine fine: PASSED.............");
	}

	private static void testInserimentoArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoArticolo inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoArticolo fallito ");
		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoOrdine fallito ");

	}

	private static void testAggiornamentoArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoArticolo inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoArticolo fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoArticolo fallito ");

		LocalDateTime createDateTimeIniziale = ordineInstance.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = ordineInstance.getUpdateDateTime();

		articoloInstance.setPrezzoSingolo(30);
		articoloServiceInstance.aggiorna(articoloInstance);

		if (ordineInstance.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException(
					"testAggiornamentoArticolo fallito: le date di aggiornamento sono disallineate ");
		if (!ordineInstance.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoArticolo fallito: la data di creazione è cambiata ");

		System.out.println(".......testAggiornamentoArticolo fine: PASSED.............");
	}

	private static void testRimozioneArticoliDaOrdine(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneArticoliDaOrdine inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testRimozioneArticoliDaOrdine fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testRimozioneArticoliDaOrdine fallito ");
		ordineInstance.getArticoli().add(articoloInstance);

		Ordine ordineReloaded1 = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if (ordineReloaded1.getArticoli().isEmpty())
			throw new RuntimeException("testRimozioneArticoliDaOrdine fallito: articoli non aggiunti ");

		articoloServiceInstance.rimozioneArticoloDaOrdine(ordineInstance.getId(), articoloInstance.getId());
		articoloServiceInstance.rimuovi(articoloInstance.getId());

		Ordine ordineReloaded2 = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if (!ordineReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testRimozioneArticoliDaOrdine fallito: articoli non rimossi ");

		System.out.println(".......testRimozioneArticoliDaOrdine fine: PASSED.............");
	}

	private static void testInserimentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testInserimentoCategoria inizio.............");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testInserimentoCategoria fallito ");

	}

	private static void testAggiornamentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoCategoria inizio.............");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoCategoria fallito ");

		LocalDateTime createDateTimeIniziale = categoriaInstance.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = categoriaInstance.getUpdateDateTime();

		categoriaInstance.setDescrizione("descrizione aggiornata");
		categoriaServiceInstance.aggiorna(categoriaInstance);

		if (categoriaInstance.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException(
					"testAggiornamentoCategoria fallito: le date di aggiornamento sono disallineate ");

		if (!categoriaInstance.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testAggiornamentoCategoria fallito: la data di creazione è cambiata ");

		System.out.println(".......testAggiornamentoCategoria fine: PASSED.............");
	}

	private static void testAggiungereCategoriaAdArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiungereCategoriaAdArticolo inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiungereCategoriaAdArticolo fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiungereCategoriaAdArticolo fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testAggiungereCategoriaAdArticolo fallito ");

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());
		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testAggiungereCategoriaAdArticolo fallito: categoria non aggiunta ");

		System.out.println(".......testAggiungereCategoriaAdArticolo fine: PASSED.............");

	}

	private static void testAggiungereArticoloACategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiungereArticoloACategoria inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (!categoriaReloaded1.getArticoli().isEmpty())
			throw new RuntimeException("testAggiungereArticoloACategoria fallito: articoli  aggiunti ");
		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		System.out.println(categoriaReloaded2.getArticoli().size());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testAggiungereCategoriaAdArticolo fallito: articolo non aggiunto ");

		System.out.println(".......testAggiungereCategoriaAdArticolo fine: PASSED.............");

	}

	private static void testRimuovereCategoriaDaArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimuovereCategoriaDaArticolo inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testRimuovereCategoriaDaArticolo fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testRimuovereCategoriaDaArticolo fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testRimuovereCategoriaDaArticolo fallito ");
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Articolo articoloReloaded2 = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());
		if (articoloReloaded2.getCategorie().isEmpty())
			throw new RuntimeException("testRimuovereCategoriaDaArticolo fallito: categoria non aggiunta ");

		articoloServiceInstance.rimuoviCategoriaDaArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());

		System.out.println(".......testRimuovereCategoriaDaArticolo fine: PASSED.............");

	}

	private static void testRimuovereArticoloDaCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimuovereArticoloDaCategoria inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testAggiungereArticoloACategoria fallito ");

		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testRimuovereCategoriaDaArticolo fallito: categoria non aggiunta ");

		categoriaServiceInstance.rimuoviArticoloDaCategoria(categoriaInstance, articoloInstance);
		articoloServiceInstance.rimuovi(articoloInstance.getId());

		System.out.println(".......testRimuovereArticoloDaCategoria fine: PASSED.............");

	}

	private static void testRimuovereOrdineConArticoli(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimuovereOrdineConArticoli inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testRimuovereOrdineConArticoli fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testRimuovereOrdineConArticoli fallito ");

		Articolo articoloReloaded1 = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());
		Long idOrdineDaRimuovere = ordineInstance.getId();

		ordineInstance.getArticoli().add(articoloInstance);

		Ordine ordineReloaded1 = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if (ordineReloaded1.getArticoli().isEmpty())
			throw new RuntimeException("testRimuovereOrdineConArticoli fallito: articoli non aggiunti ");

		try {

			ordineServiceInstance.rimuovi(ordineInstance.getId());

		} catch (CancellazioneOrdineConArticoliException e) {

		}

		System.out.println(".......testRimuovereOrdineConArticoli fine: PASSED.............");

	}
	
	private static void testCercaOrdiniPerCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaOrdiniPerCategoria inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testCercaOrdiniPerCategoria fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaOrdiniPerCategoria fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testCercaOrdiniPerCategoria fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testCercaOrdiniPerCategoria fallito: categoria non aggiunta ");

		Ordine ordineReloaded =ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if(ordineServiceInstance.cercaOrdiniPerCategoria(categoriaReloaded2).size()<1)
			throw new RuntimeException("testCercaOrdiniPerCategoria fallito ");
		
		System.out.println(".......testCercaOrdiniPerCategoria fine: PASSED.............");
	}

	private static void testCercaCategorieDistintePerOrdine(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaCategorieDistintePerOrdine inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testCercaCategorieDistintePerOrdine fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaCategorieDistintePerOrdine fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testCercaCategorieDistintePerOrdine fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testCercaCategorieDistintePerOrdine: categoria non aggiunta ");

		Ordine ordineReloaded =ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if(ordineServiceInstance.cercaCategorieDistintePerOrdine(ordineReloaded).size()<1)
			throw new RuntimeException("testCercaCategorieDistintePerOrdine fallito ");
		
		System.out.println(".......testCercaCategorieDistintePerOrdine fine: PASSED.............");
	}
	
	private static void testSommaPrezziPerUnaCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testSommaPrezziPerUnaCategoria inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziPerUnaCategoria fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziPerUnaCategoria fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziPerUnaCategoria fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testSommaPrezziPerUnaCategoria: categoria non aggiunta ");
		
		if(articoloServiceInstance.sommaPrezziDiCategoria(categoriaReloaded2) != 50L)
			throw new RuntimeException("testSommaPrezziPerUnaCategoria fallito ");
		System.out.println(".......testSommaPrezziPerUnaCategoria fine: PASSED.............");	
		
	}
	
	
	private static void testCercaOrdinePerDataSpedizionePiuRecenteCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaOrdinePerDataSpedizionePiuRecenteCategoria inizio.............");

		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testCercaOrdinePerDataSpedizionePiuRecenteCategoria fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaOrdinePerDataSpedizionePiuRecenteCategoria fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testCercaOrdinePerDataSpedizionePiuRecenteCategoria fallito ");
		
		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testCercaOrdinePerDataSpedizionePiuRecenteCategoria: categoria non aggiunta ");

		Ordine ordineReloaded =ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if(ordineServiceInstance.cercaOrdinePerSpedizionePiuRecenteCategoria(categoriaInstance) ==null)
			throw new RuntimeException("testCercaOrdinePerDataSpedizionePiuRecenteCategoria fallito ");
			
		System.out.println(".......testCercaOrdinePerDataSpedizionePiuRecenteCategoria fine: PASSED.............");
	}
	//DA ERRORE VUOTO
	private static void testcercaCodiciDegliOrdiniNelMeseAnno(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testcercaCodiciDegliOrdiniNelMeseAnno inizio.............");
		 
		Date dataSpedizione=new SimpleDateFormat("yyyy-mm-dd").parse("2022-02-01");
		Ordine ordineInstance = new Ordine("Gianluca", "Via nani", dataSpedizione,new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno fallito ");

		Categoria categoriaInstance = new Categoria("descr", "codi1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno fallito ");
		
		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno: categoria non aggiunta ");
		Articolo articoloReloaded2= articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());
		if (articoloReloaded2.getCategorie().isEmpty())
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno: categoria non aggiunta ");
		Ordine ordineReloaded =ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineInstance.getId());
		if (ordineReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno: categoria non aggiunta ");
		System.out.println(ordineReloaded.getArticoli().size());
		System.out.println(articoloReloaded2.getCategorie().size());
		System.out.println(categoriaReloaded2.getArticoli().size());
	    Date dataCercata=new SimpleDateFormat("yyyy-mm-dd").parse("2022-02-01");
		List<String> prova=categoriaServiceInstance.cercaCodicidegliOrdiniDuranteMeseAnno(dataCercata);
	    if(prova.isEmpty())
			throw new RuntimeException("testcercaCodiciDegliOrdiniNelMeseAnno fallito ");
			
		System.out.println(".......testcercaCodiciDegliOrdiniNelMeseAnno fine: PASSED.............");
	}

	
	private static void testSommaPrezziStessoDestinatario(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testSommaPrezziStessoDestinatario inizio.............");

		Ordine ordineInstance = new Ordine("destinsomma", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziStessoDestinatario fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziStessoDestinatario fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziStessoDestinatario fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testSommaPrezziStessoDestinatario: categoria non aggiunta ");
		
		if(articoloServiceInstance.sommaPrezziStessoDestinatario("destinsomma") != 50L)
			throw new RuntimeException("testSommaPrezziStessoDestinatario fallito ");
		System.out.println(".......testSommaPrezziStessoDestinatario fine: PASSED.............");	
		
	}
	
	private static void testCercaIndirizziPerNumeroSerialeCon(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaIndirizziPerNumeroSerialeCon inizio.............");

		Ordine ordineInstance = new Ordine("destinsomma", "Via nani", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon: categoria non aggiunta ");
		
		if(ordineServiceInstance.cercaIndirizziPerNumeroSerialeCon("numero").isEmpty())
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon fallito ");
		System.out.println(".......testCercaIndirizziPerNumeroSerialeCon fine: PASSED.............");	
		
	}
	
	private static void testCercaArticoliConErrore(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaArticoliConErrore inizio.............");

		Ordine ordineInstance = new Ordine("destinsomma", "Via nani",new Date(),new SimpleDateFormat("yyyy-mm-dd").parse("2022-09-01"));
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testCercaArticoliConErrore fallito ");

		Articolo articoloInstance = new Articolo("decrizione1", "numeroSeriale1", 50, new Date(), ordineInstance);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaArticoliConErrore fallito ");

		Categoria categoriaInstance = new Categoria("descrizione1", "codice1");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testCercaArticoliConErrore fallito ");

		
		Categoria categoriaReloaded1 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorie(articoloInstance.getId());

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloInstance, categoriaInstance);
		categoriaServiceInstance.aggiungiArticoloACategoria(categoriaInstance, articoloInstance);

		Categoria categoriaReloaded2 = categoriaServiceInstance
				.caricaSingoloElementoEagerArticoliCategoria(categoriaInstance.getId());
		if (categoriaReloaded2.getArticoli().isEmpty())
			throw new RuntimeException("testCercaArticoliConErrore: categoria non aggiunta ");
		
		if(articoloServiceInstance.cercaArticoliConErrori().isEmpty())
			throw new RuntimeException("testCercaIndirizziPerNumeroSerialeCon fallito ");
		System.out.println(".......testCercaIndirizziPerNumeroSerialeCon fine: PASSED.............");	
		
	}
	
	
	
}
