package hr.fer.zemris.java.p12.dao;


import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.p12.model.PollOptions;
import hr.fer.zemris.java.p12.model.Polls;


/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 *
 */
public interface DAO {
	public Map<Long,Polls> getAllPolls() throws DAOException;
	public Polls getPoll(long id) throws DAOException;
	public Map<Long,PollOptions> getPollOptionsByPollID(long id) throws DAOException;
	public void incrementVotesPollOptions(long id) throws DAOException;
}