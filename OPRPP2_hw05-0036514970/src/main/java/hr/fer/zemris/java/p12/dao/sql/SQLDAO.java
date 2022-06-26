package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollOptions;
import hr.fer.zemris.java.p12.model.Polls;

public class SQLDAO implements DAO {

	@Override
	public Map<Long, Polls> getAllPolls() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		Map<Long,Polls> mapa = new HashMap<Long, Polls>();
		try(PreparedStatement ps = con.prepareStatement("SELECT * FROM Polls")){
			try(ResultSet rs = ps.executeQuery()) {
				while(rs!=null && rs.next()) {
					mapa.put(
							rs.getLong(1),
							new Polls(rs.getLong(1), rs.getString(2), rs.getString(3))
							);
				}
			}
				
			
		} catch (SQLException e) {
			throw new DAOException("Pogreška prilikom dohvata liste Polls.", e);
		}
		return mapa;

	}

	@Override
	public Map<Long, PollOptions> getPollOptionsByPollID(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		Map<Long,PollOptions> mapa = new HashMap<Long, PollOptions>();
		try(PreparedStatement ps = con.prepareStatement("SELECT * FROM PollOptions WHERE pollID=?")){
			ps.setLong(1, id);
			try(ResultSet rs = ps.executeQuery()) {
				while(rs!=null && rs.next()) {
					mapa.put(
							rs.getLong(1),
							new PollOptions(
									rs.getLong(1), 
									rs.getLong(4), 
									rs.getString(2), 
									rs.getString(3), 
									rs.getInt(5)
									)
							);
				}
			}
				
			
		} catch (SQLException e) {
			throw new DAOException("Pogreška prilikom dohvata liste PollsOptions.", e);
		}
		return mapa;
	}

	@Override
	public Polls getPoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		Polls poll = null;
		try(PreparedStatement ps = con.prepareStatement("SELECT * FROM Polls where id=?")){
			ps.setLong(1, id);
			try(ResultSet rs = ps.executeQuery()) {
				while(rs!=null && rs.next()) {
					if(poll == null) {
						poll =  new Polls(rs.getLong(1), rs.getString(2), rs.getString(3));
						continue;
					}
					throw new DAOException("ID nije jedinstven");
				}
				return poll;
			}	
			
		} catch (SQLException e) {
			throw new DAOException("Pogreška prilikom dohvata liste Polls.", e);
		}
	}

	@Override
	public void incrementVotesPollOptions(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try(PreparedStatement ps = con.prepareStatement("UPDATE PollOptions set votesCount=votesCount + 1 WHERE id=?")){
			ps.setLong(1,id); 
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Pogreška prilikom ažuriranju pollOptions.", e);
		}
		
	}

	



}