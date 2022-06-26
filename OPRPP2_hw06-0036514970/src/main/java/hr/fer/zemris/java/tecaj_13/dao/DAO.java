package hr.fer.zemris.java.tecaj_13.dao;

import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Dodaje korisnik u bazu podataka 
	 * 
	 * @param firstName ime korisnika
	 * @param lastName prezime korisnika
	 * @param email email korisnika
	 * @param nick jedinstveni nadinak za korisnika
	 * @param hashPassword lozinka korisnika hashirana algoritmom SHA1
	 * @return Kreirani objekt koriniska
	 * @throws DAOException ako deđe do pogreške prilikom dodanjava korisnika
	 */
	public User addUser(String firstName, String lastName, String email, String nick, String hashPassword) throws DAOException;
	
	/**
	 * Dohvaća korisnika s zadanim nadimkom. Ako takav korisinik ne postoji vraća <code>null</code>
	 * @param nick nadimak korisnika, ako je null funkcija vraća null
	 * @return objekt korisnika, ili <code>null</code> ako korisnik nije pronađen
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public User getUserByNick(String nick) throws DAOException;
	
	/**
	 * Dohvaća sve korisnike iz baze podataka
	 * 
	 * @return Listu svih korisnika
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<User> getAllUsers() throws DAOException;
	
	public BlogEntry addBlogEntry(Date createDate, String title, String text, User user) throws DAOException;
	
	public BlogEntry editBlogEntry(long id, Date editData, String title, String text) throws DAOException;
	
	public BlogComment addCommentInBlogEntry(long id, String comment, String userEmail) throws DAOException;
	
	public void addSrce(User user, long blogEntryId) throws DAOException;
		

	
}