package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.User;
import hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public User addUser(String firstName, String lastName, String email, String nick, String hashPassword)
			throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(hashPassword);
		user.setBlogEntries(new ArrayList<BlogEntry>());
		em.persist(user);
		return user;
	}
	
	@Override
	public User getUserByNick(String nick) throws DAOException {
		if(nick == null) return null;
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)em.createQuery(Kljucevi.USER_BY_NICK_QUERY).
				setParameter(Kljucevi.NICK, nick).getResultList();
		if(users.isEmpty()) return null;
		return users.get(0);
	}
	
	@Override
	public List<User> getAllUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)em.createQuery(Kljucevi.USERS_QUERY).getResultList();
		return users;
	}
	
	@Override
	public BlogEntry addBlogEntry(Date createDate, String title, String text, User user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry be = new BlogEntry();
		be.setCreatedAt(createDate);
		be.setLastModifiedAt(createDate);
		be.setTitle(title);
		be.setText(text);
		be.setComments(new ArrayList<BlogComment>());
		be.setUser(user);
		be.setSrca(new ArrayList<User>());
		em.persist(be);
		return be;
	}
	
	@Override
	public BlogEntry editBlogEntry(long id, Date editData, String title, String text) throws DAOException {
		BlogEntry be = getBlogEntry(id);
		be.setLastModifiedAt(editData);
		be.setTitle(title);
		be.setText(text);
		return be;
		
	}
	
	@Override
	public BlogComment addCommentInBlogEntry(long id, String comment, String userEmail) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogComment bc = new BlogComment();
		bc.setBlogEntry(DAOProvider.getDAO().getBlogEntry(id));
		bc.setMessage(comment);
		bc.setPostedOn(new Date());
		bc.setUsersEMail(userEmail);
		em.persist(bc);
		return bc;
	}
	
	@Override
	public void addSrce(User user, long blogEntryId) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry be = getBlogEntry(blogEntryId);
		be.getSrca().add(user);
		return;
	}
	

}