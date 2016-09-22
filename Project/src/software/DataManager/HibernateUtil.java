package software.DataManager;

import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This class initialize Hibernate
 *
 * @author: FEM
 * @version: 06/11/2015
 */

public class HibernateUtil {

	public static SessionFactory psessionFactory;
	public static Session psession;

	public static void initialize(DatabaseConnection db) {
		try {
			AnnotationConfiguration conf = new AnnotationConfiguration();
			try {
				conf.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
				conf.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				conf.setProperty("hibernate.connection.url",
						"jdbc:postgresql://localhost:" + db.getPortName() + "/" + db.getDatabaseName());
			} catch (Exception pe) {
				JOptionPane.showMessageDialog(null, "Database error Nº 2001");
			}

			conf.setProperty("hibernate.connection.username", db.getUserName());
			conf.setProperty("hibernate.connection.password", db.getPassword());
			conf.setProperty("hibernate.connection.pool_size", "10");
			conf.setProperty("hibernate.hbm2ddl.auto", "update");

			conf.addPackage("software.DomainModel.AnalysisEntity");
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Artifact.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.ArtifactType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Condition.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Environment.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.EnvironmentType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Formula.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.GenericScenario.class);

			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.IndirectMetric.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.DirectMetric.class);

			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.QualityAttribute.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.QualityRequirement.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Response.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.ResponseMeasure.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.ResponseType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.ResponseMeasureType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.SpecificScenario.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Stimulus.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.StimulusSource.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.StimulusSourceType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.StimulusType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.System.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Unit.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.TacticType.class);
			conf.addAnnotatedClass(software.DomainModel.AnalysisEntity.Tactic.class);

			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.StartPoint.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.ORJoin.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.ANDJoin.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.ORFork.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.ANDFork.class);
			conf.addAnnotatedClass(
					software.DomainModel.SoftwareArchitectureSpecificationEntity.SpecificationParameter.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.Responsibility.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.Path.class);
			conf.addAnnotatedClass(
					software.DomainModel.SoftwareArchitectureSpecificationEntity.CompositeComponent.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.SimpleComponent.class);
			conf.addAnnotatedClass(software.DomainModel.SoftwareArchitectureSpecificationEntity.Architecture.class);

			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.Indicator.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.IndicatorType.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.ResponsabilityIndicator.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.SystemIndicator.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.SimulationParameter.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.Simulator.class);
			conf.addAnnotatedClass(software.DomainModel.ReportsEntity.Run.class);

			try {
				psessionFactory = conf.buildSessionFactory();
				psession = psessionFactory.openSession();
			} catch (HibernateException pe) {
				JOptionPane.showMessageDialog(null, pe);
			}
		} catch (HeadlessException pex) {
			throw new ExceptionInInitializerError(pex);
		} catch (MappingException pex) {
			throw new ExceptionInInitializerError(pex);
		}
	}

	public Boolean isConnection() {
		try {
			try {
				getSession().connection().isClosed();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} catch (HibernateException pe) {
			return false;
		}
	}

	public static Session getSession() throws HibernateException {
		return psession;
	}

}
