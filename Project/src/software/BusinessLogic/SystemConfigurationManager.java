package software.BusinessLogic;

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

	public String getUserName() {
		return DatabaseConnection.UserName;
	}

	public String getPortNumber() {
		return DatabaseConnection.PortName;
	}

	public String getPassword() {
		return DatabaseConnection.Password;
	}

	public Boolean isConnection() {
		HibernateUtil hu = new HibernateUtil();
		return hu.isConnection();
	}

}