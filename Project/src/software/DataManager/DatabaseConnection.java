package software.DataManager;

import software.Security.CryptoUtils;

/**
 * Constant definitions for plug-in preferences
 */
public class DatabaseConnection {

	private String UserName = DOM.readUserName();
	private String PortName = DOM.readPortNumber();
	private String Password = DOM.readPassword();

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPortName() {
		return PortName;
	}

	public void setPortName(String portName) {
		PortName = portName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
