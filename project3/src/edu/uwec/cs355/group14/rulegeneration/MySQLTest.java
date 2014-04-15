package edu.uwec.cs355.group14.rulegeneration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
		Transaction transaction = new Transaction("{Apples, Beer, Diapers}");
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.5;
		TransactionSet transactionSet = new TransactionSet(transaction,
				minSupportLevel, minConfidenceLevel);
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

		assertNotSame("Two saved transactions are not the same",
				ts_id, ts_id2);
	}

	public void testSaveRules() {
		AssociationRuleSet associationRuleSet = new AssociationRuleSet();
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
