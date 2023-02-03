package messages;

public class Range {
			public int ULTRASOUND=0;
			public int INFRARED=1;
			public Header header;
			public int radiation_type;
			public int field_of_view;
			public double min_range;
			public double max_range;
			public double range;
			
			
			

			   public Range(int ULTRASOUND,int INFRARED,Header header, int radiation_type, int field_of_view, double min_range, double max_range, double range)
{
			        this.ULTRASOUND = ULTRASOUND;
			        this.INFRARED = INFRARED;
			        this.radiation_type = radiation_type;
			        this.field_of_view = field_of_view;
			        this.min_range = min_range;
			        this.max_range = max_range;
			        this.range = range;



			    }
			    public Range(){}
			}

			

