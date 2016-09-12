package software.BusinessLogic;

import software.DataManager.DOM;
import software.DataManager.DatabaseConnection;
import software.DataManager.HibernateManager;
import software.DataManager.HibernateUtil;

/**
 * This class is responsible for the management package: Analysis
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class SystemConfigurationManager extends HibernateManager {

	/**
	 * Attributes
	 */
	private static SystemConfigurationManager manager;
	private DatabaseConnection db;

	/**
	 * Builder
	 */
	public SystemConfigurationManager() {
		super();
	}

	/**
	 * Get SystemConfigurationManager. Applied Singleton pattern
	 */
	public static SystemConfigurationManager getManager() {
		if (manager == null) {
			synchronized (SystemConfigurationManager.class) {
				manager = new SystemConfigurationManager();
			}
		}
		return manager;
	}

	public DatabaseConnection getDb() {
		if (db == null) {
			synchronized (DatabaseConnection.class) {
				db = new DatabaseConnection();
			}
		}
		return db;
	}

	public void setDb(DatabaseConnection db) {
		this.db = db;
	}

	public String getUserName() {
		return this.getDb().getUserName();
	}

	public String getPortNumber() {
		return this.getDb().getPortName();
	}

	public String getPassword() {
		return this.getDb().getPassword();
	}

	public Boolean isConnection(String password, String username, String portnumber) {
		this.getDb().setPassword(password);
		this.getDb().setUserName(username);
		this.getDb().setPortName(portnumber);
		if (HibernateUtil.getSession().isOpen()) {
			HibernateUtil.getSession().close();
		}
		HibernateUtil.initialize(this.getDb());
		HibernateUtil hu = new HibernateUtil();
		return hu.isConnection();
	}

	public Boolean updateConnectionData(String password, String username, String portnumber) {
		try {
			this.getDb().setPassword(password);
			this.getDb().setUserName(username);
			this.getDb().setPortName(portnumber);
			DOM dom = new DOM();
			dom.writePassword(password);
			dom.writePortNumber(portnumber);
			dom.writeUserName(username);
			if (HibernateUtil.getSession().isOpen()) {
				HibernateUtil.getSession().close();
			}
			HibernateUtil.initialize(this.getDb());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}