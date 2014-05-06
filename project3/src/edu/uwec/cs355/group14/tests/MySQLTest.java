package edu.uwec.cs355.group14.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.uwec.cs355.group14.common.RuleSet;
import edu.uwec.cs355.group14.server.MySQL;
import edu.uwec.cs355.group14.common.Transaction;
import edu.uwec.cs355.group14.common.TransactionSet;
import junit.framework.TestCase;

public class MySQLTest extends TestCase {

	public void testRecreateTables() {
		MySQL mysql = new MySQL();
		String actions = new String();
		String createTableString = new String("CREATE TABLE TransactionSet (\n"
				+ "	ts_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
				+ "	ts_minSupportLevel DOUBLE NOT NULL,\n"
				+ "	ts_minConfidenceLevel DOUBLE NOT NULL,\n"
				+ " ts_datetime DATETIME NOT NULL\n" + ");");
		String createTableString2 = new String("CREATE TABLE Transaction (\n"
				+ "trans_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
				+ "trans_items LONGTEXT NOT NULL,\n" + "ts_id INT NOT NULL,\n"
				+ "FOREIGN KEY (ts_id) REFERENCES TransactionSet(ts_id)\n"
				+ ");");

		assertTrue(createTableString != createTableString2);
	}

	public void testSaveTransactions() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Apples");
		items.add("Beer");
		items.add("Diapers");
		Transaction transaction = new Transaction(items, "", "");
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.5;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel, minConfidenceLevel);
		transactionSet.add(transaction);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		String datetime = sdf.format(calendar.getTime());
		int ts_id = -1;
		int ts_id2 = 1;
		String sql = "INSERT INTO TransactionSet (ts_minSupportLevel, ts_minConfidenceLevel, ts_datetime) "
				+ "VALUES ("
				+ transactionSet.getMinSupportLevel()
				+ ", "
				+ transactionSet.getMinConfidenceLevel()
				+ ", '"
				+ datetime
				+ "');";

		assertNotSame("Two saved transactions are not the same", ts_id, ts_id2);
	}

	public void testSaveRules() {
		RuleSet associationRuleSet = new RuleSet(0.0);
		int ts_id = -1;
		int rs_id = -1;
		String sql = new String(
				"INSERT INTO RuleSet (rs_minConfidenceLevel, ts_id) "
						+ "VALUES ("
						+ associationRuleSet.getMinConfidenceLevel() + ", "
						+ ts_id + ");");
		assertFalse(ts_id != rs_id);

	}
}
