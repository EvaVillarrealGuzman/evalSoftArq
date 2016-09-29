package DomainModel.AnalysisEntity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * This class defines metrics
 *
 * @author: FEM
 * @version: 04/05/2016
 */

@Entity
@Table(name = "DIRECTMETRICS" )
@PrimaryKeyJoinColumn(name="id" )
public class DirectMetric extends Metric {

}
