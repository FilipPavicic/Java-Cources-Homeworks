package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {
	
	private static final String NAME_POLLS_TABLE = "Polls";
	private static final String NAME_POLLOPTIONS_TABLE = "PollOptions";
	
	private static final String DEFINITION_POLLS_TABLE = 
			"""
			CREATE TABLE Polls
				 (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
				 title VARCHAR(150) NOT NULL,
				 message CLOB(2048) NOT NULL
				)
			""";
	private static final String DEFINITION_POLLOPTIONS_TABLE = 
			"""
			CREATE TABLE PollOptions
				 (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
				 optionTitle VARCHAR(100) NOT NULL,
				 optionLink VARCHAR(150) NOT NULL,
				 pollID BIGINT,
				 votesCount BIGINT,
				 FOREIGN KEY (pollID) REFERENCES Polls(id) ON DELETE CASCADE
				)
			""";
	private static final String[][] TABLES = {
			{NAME_POLLS_TABLE, DEFINITION_POLLS_TABLE},
			{NAME_POLLOPTIONS_TABLE, DEFINITION_POLLOPTIONS_TABLE},
	};
	private static final String[][] BENDOVI = {
			{"Prljavo kazalište", "https://www.youtube.com/watch?v=axaKzbLL5Ws&ab_channel=JAvolimPrljavoKazali%C5%A1te"},
			{"Plavi orkestar", "https://www.youtube.com/watch?v=OmbAGzpgKIE&ab_channel=NikolaKolund%C5%BEi%C4%87"},
			{"Bijelo dugme", "https://www.youtube.com/watch?v=fwpyL0w-zyI&ab_channel=BijeloDugme"},
			{"Bajaga", "https://www.youtube.com/watch?v=A8sFkwwf8Xk&ab_channel=BajagaiInstruktori"},
			{"Pravila igre", "https://www.youtube.com/watch?v=39a3BdTWEDc&ab_channel=PravilaIgre-RulesoftheGame"},
			{"Crvena jabuka", "https://www.youtube.com/watch?v=Wl2RGKNCAJ0&ab_channel=CroRecVEVO"},
			{"Daleka obala", "https://www.youtube.com/watch?v=2EMmmGkY4zY&ab_channel=DALEKAOBALACRORECOFFICIAL"},
			{"Magazin", "https://www.youtube.com/watch?v=hlluayds-EI&ab_channel=GrupaMagazin"},
			{"Parni valjak", "https://www.youtube.com/watch?v=coEMbzlkzV4&ab_channel=CroatiaRecords"}
	};
	private static final String[][] PROGRAMI = {
			{"Java", "https://www.java.com/en/"},
			{"Python", "https://www.python.org/"},
			{"C", "https://en.wikipedia.org/wiki/C_(programming_language)"},
			{"C++", "https://www.cplusplus.com/"},
	};
	private static final String[][][] INIT_VALUES = {
			BENDOVI,
			PROGRAMI
	};
	private static final String[][] INIT_VALUES_POLLS = {
			{"Glasanje za omiljeni bend:","Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!"},
			{"Glasanje za omiljeni Programski jezik:","Od sljedećih programskih jezika, koji Vam je programski jezik najdraži? Kliknite na link kako biste glasali!"}
	};

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String host = null;
		int port = 0;
		String name = null;
		String user = null;
		String password = null;
		try(InputStream input = sce.getServletContext().getResourceAsStream("/WEB-INF/dbsettings.properties")){
			Properties prop = new Properties();
			prop.load(input);
			host = prop.getProperty("host");
			if(host == null) throw new InvalidParameterException("host properie nije pronađeno");
			if(prop.getProperty("port") == null) throw new InvalidParameterException("port properie nije pronađeno");
			port = Integer.parseInt(prop.getProperty("port"));
			user = prop.getProperty("user");
			if(user == null) throw new InvalidParameterException("user properie nije pronađeno");
			name = prop.getProperty("name");
			if(name == null) throw new InvalidParameterException("name properie nije pronađeno");
			password = prop.getProperty("password");
			if(password == null) throw new InvalidParameterException("password properie nije pronađeno");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String connectionURL = "jdbc:derby://"+host+":"+port+"/"+name+";user="+user+";password="+ password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.client.ClientAutoloadedDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		createTablesIfNotExits(cpds);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	private static void createTablesIfNotExits(DataSource dataSource) {
		try(Connection con = dataSource.getConnection()) {
			boolean shouldRewrite= false;
			for(String[] tableDefinition : TABLES) {
				DatabaseMetaData dbmd = con.getMetaData();
		        try(ResultSet rs = dbmd.getTables(null, null, tableDefinition[0].toUpperCase(),null)){
		        	if(rs.next()) {
		        		if(tableDefinition[0].equals("Polls")) {
		        			try(PreparedStatement ps = con.prepareStatement("SELECT count(*) FROM Polls")){
		        				try(ResultSet rows = ps.executeQuery()){
		        					if(rows.next()) {
		        						if(rows.getInt(1) == 0) shouldRewrite = true;
		        					}	
		        				}
		        			}
		        		}
		        	} else {
		        		try(PreparedStatement pst = con.prepareStatement(tableDefinition[1])){
		        			pst.execute();
		        			shouldRewrite = true;

		        		}
		        	}
		        } catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(shouldRewrite) {
				addInitialPools(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}
		

	private static void addInitialPools(Connection con) throws SQLException {
		try(PreparedStatement ps = con.prepareStatement(
				"INSERT INTO "+NAME_POLLS_TABLE+" (title, message) values (?,?)",Statement.RETURN_GENERATED_KEYS)){
			int i = 0;
			for(String[] poll : INIT_VALUES_POLLS) {
				ps.setString(1, poll[0]);
				ps.setString(2, poll[1]);
				ps.executeUpdate();
				try(ResultSet IDs = ps.getGeneratedKeys()){
					try(PreparedStatement ps1 = con.prepareStatement(
						"INSERT INTO "+NAME_POLLOPTIONS_TABLE+
						" (optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)")){
						if(IDs.next()) {
							long id = IDs.getLong(1);
							for(String[] values : INIT_VALUES[i]) {
								ps1.setString(1, values[0]);
								ps1.setString(2, values[1]);
								ps1.setLong(3, id);
								ps1.setInt(4, 0);
								ps1.addBatch();
							}
							ps1.executeBatch();
						}
					}
				}
				i++;
			}
			
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
