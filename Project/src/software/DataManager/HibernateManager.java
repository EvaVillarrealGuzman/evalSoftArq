package software.DataManager;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import project.preferences.NewSystemPreferencePage;

/**
 * This class management Hibernate (list class, save and update objects)
 * 
 * @author: FEM
 * @version: 06/11/2015
 */

public class HibernateManager extends HibernateUtil {
	public Transaction tx;

	public Transaction getTx() {
		return tx;
	}

	public void setTx(Transaction ptx) {
		this.tx = ptx;
	}

	public List listClass(Class pclass, String prdenAttribute) {
		Criteria crit = getSession().createCriteria(pclass).addOrder(Order.asc(prdenAttribute));
		return crit.list();
	}

	public List listClass(Class pclase, String patributoOrden, boolean pstate) {
		Criteria crit = getSession().createCriteria(pclase).addOrder(Order.asc(patributoOrden))
				.add(Restrictions.eq("state", pstate));
		return crit.list();
	}

	public boolean saveObject(Object pobjet) {
		try {
			Session s = HibernateUtil.getSession();
			Transaction tx = s.beginTransaction();
			s.save(pobjet);
			tx.commit();
			System.out.println(" saveObjet() " + pobjet.getClass() + ": " + pobjet.toString()); // NOPMD by Usuario-Pc on 10/06/16 22:02
			JOptionPane.showOptionDialog(null, "Done successfully!", "Notice", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(NewSystemPreferencePage.class.getResource("/Icons/success.png")),
					new Object[] { "OK" }, "OK");
			return true;
		} catch (Exception pex) {
			System.out.println("error " + pex); // NOPMD by Usuario-Pc on 10/06/16 22:02
			System.out.println( // NOPMD by Usuario-Pc on 10/06/16 22:02
					"Repository.saveObjet(Object objetj)" + pobjet.getClass() + ": " + pobjet.toString() + pex);
			pex.printStackTrace();
			JOptionPane.showOptionDialog(null, "The object can not be saved", "Error", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					new ImageIcon(NewSystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			getTx().rollback();
			return false;
		}
	}

	public boolean updateObject(Object pobjet) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		try {
			s.update(pobjet);
			tx.commit();
			System.out.println(" updateObjet() " + pobjet.getClass() + ": " + pobjet.toString()); // NOPMD by Usuario-Pc on 10/06/16 22:02
			JOptionPane.showOptionDialog(null, "Done successfully!", "Notice", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(NewSystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			return true;
		} catch (HibernateException pe) {
			JOptionPane.showMessageDialog(null, pe);
			tx.rollback();
			JOptionPane.showOptionDialog(null, "You can not save data. \nThese have been modified by someone else.",
					"Error", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
					new ImageIcon(NewSystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			JOptionPane.showOptionDialog(null, "The object can not be updated" + pe.getMessage(), "Error",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE,
					new ImageIcon(NewSystemPreferencePage.class.getResource("/Icons/error.png")), new Object[] { "OK" },
					"OK");
			return false;
		}
	}

}
