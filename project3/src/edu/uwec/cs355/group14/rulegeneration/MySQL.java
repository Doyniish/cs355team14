package edu.uwec.cs355.group14.rulegeneration;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MySQL {
	public Connection cn;
	private String url = "jdbc:mysql://dario.cs.uwec.edu:3306/";
	private String dbName = "CS355GROUP14";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "CS355GROUP14"; 
	private String password = "W687124$";
	
	private String open() {
		String ret = "";
		try {
			if (cn == null || cn.isClosed()) {
				Class.forName(driver).newInstance();
				cn = DriverManager.getConnection(url+dbName,userName,password);
			}
		} catch (Exception e) {
			ret = e.getMessage();
		}
		return ret;
	}
	private void close() {
		if (cn == null) {
			return;
		}
		try { 
			cn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cn = null;
		}
	}
	
	public void recreateTables() {
			ArrayList<String> actions = new ArrayList<String>();
			actions.add("DROP TABLE IF EXISTS Transaction;");
			actions.add("DROP TABLE IF EXISTS Rule;");
			actions.add("DROP TABLE IF EXISTS RuleSet;");
			actions.add("DROP TABLE IF EXISTS TransactionSet;");
			
			String createTableString = "CREATE TABLE TransactionSet (\n"
										+ "	ts_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
										+ "	ts_minSupportLevel DOUBLE NOT NULL,\n"
										+ "	ts_minConfidenceLevel DOUBLE NOT NULL,\n"
										+ " ts_datetime DATETIME NOT NULL\n"
										+ ");";
			actions.add(createTableString);
			
			createTableString =	 "CREATE TABLE Transaction (\n"
								+ "trans_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
								+ "trans_items LONGTEXT NOT NULL,\n"
								+ "ts_id INT NOT NULL,\n"
								+ "FOREIGN KEY (ts_id) REFERENCES TransactionSet(ts_id)\n"
								+ ");";
			actions.add(createTableString);
			
			createTableString =	"CREATE TABLE RuleSet (\n"
								+ "	rs_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
								+ "	rs_minConfidenceLevel DOUBLE NOT NULL,\n"
								+ "	ts_id INT NOT NULL,\n"
								+ "	FOREIGN KEY (ts_id) REFERENCES TransactionSet(ts_id)\n"
								+ ");";
			actions.add(createTableString);
			
			createTableString =	"CREATE TABLE Rule (\n"
								+ "rule_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
								+ "rule_antecedent LONGTEXT NOT NULL,\n"
								+ "rule_consequent LONGTEXT NOT NULL,\n"
								+ "rule_actualConfidenceLevel DOUBLE NOT NULL,\n"
								+ "rs_id INT NOT NULL,\n"
								+ "FOREIGN KEY(rs_id) REFERENCES RuleSet(rs_id)\n"
								+ ");";
			actions.add(createTableString);
			
			try {
				open();
				Statement stmt = cn.createStatement();
				for(String action : actions) {
					stmt.addBatch(action);
				}
				stmt.executeBatch();
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public int saveTransactions(TransactionSet transactionSet) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		String datetime = sdf.format(calendar.getTime());
		
		int ts_id = -1;
				
		String sql =	"INSERT INTO TransactionSet (ts_minSupportLevel, ts_minConfidenceLevel, ts_datetime) "
						+ "VALUES (" + transactionSet.getMinSupportLevel() + ", " + transactionSet.getMinConfidenceLevel() + ", '" + datetime + "');";
				
		try {
			open();
			Statement stmt = cn.createStatement();
			stmt.executeUpdate(sql);
			ResultSet res = stmt.executeQuery("SELECT ts_id FROM TransactionSet WHERE ts_datetime = '" + datetime + "'");
			res.next();
			ts_id = res.getInt(1);
			
			for(Transaction transaction : transactionSet.getTransactionSet()) {
				sql =	"INSERT INTO Transaction (trans_items, ts_id) "
						+ "VALUES ('" + transaction.getItems() + "', " + ts_id + ");";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ts_id;
	}
	
	public void saveRules(AssociationRuleSet associationRuleSet, int ts_id) {
		String sql =	"INSERT INTO RuleSet (rs_minConfidenceLevel, ts_id) "
						+ "VALUES (" + associationRuleSet.getMinConfidenceLevel() + ", " + ts_id + ");";
		int rs_id = -1;
		
		try {
			open();
			Statement stmt = cn.createStatement();
			stmt.executeUpdate(sql);
			
			ResultSet res = stmt.executeQuery("SELECT rs_id FROM RuleSet WHERE ts_id = " + ts_id);
			res.next();
			rs_id = res.getInt(1);
			
			for(AssociationRule rule : associationRuleSet.getRules()) {
				sql =	"INSERT INTO Rule (rule_antecedent, rule_consequent, rule_actualConfidenceLevel, rs_id) "
						+ "VALUES ('" + rule.getAntecedent() + "', '" + rule.getConsequent() + "', " + rule.getConfidenceLevel() + ", " + rs_id + ");";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}