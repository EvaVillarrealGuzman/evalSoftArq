package DomainModel.ReportsEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import DomainModel.AnalysisEntity.Metric;

        @Entity
        @Table(name = "INDICATOR")
        public class Indicator implements Comparable{
              
               //Attributes
               @Id
           @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
               private int id ;
              
               private Double value ;
              
               @ManyToOne(targetEntity = IndicatorType.class)
               private IndicatorType type ;
              
               @ManyToOne(targetEntity = Metric. class)
               private Metric metric;
              
               //CompareTo
               @Override
           public int compareTo(Object p) {
               Indicator t = (Indicator) p;
               return this .toString().compareTo(t.toString());
           }
}
