
public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	//YOUR CODE GOES HERE
    	
    	for(int i =0;i<this.networkLines.length;i++){
    		this.networkLines[i].shuffleLine();
    	

    	}
    	
    	
    }
    
    public void undance() {
    	//YOUR CODE GOES HERE
    	for(int i=0;i<this.networkLines.length;i++){
    		this.networkLines[i].sortLine();
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	TrainLine curLine = null; //use this variable to store the current line.
    	TrainStation curStation= null; //use this variable to store the current station. 
    	
    	
    	int hoursCount = 0;
    	System.out.println("Departing from "+startStation);
    	
    	//YOUR CODE GOES HERE
    	
    	curLine = getLineByName(startLine);
    	curStation = curLine.findStation(startStation);
    	TrainLine finLine = getLineByName(endLine);
    	TrainStation finStation = finLine.findStation(endStation);
    	TrainStation previous = curStation;
    	TrainStation temp =null;
    	
    	
    	while(!curStation.equals(finStation)) {

    		hoursCount++;
    		
    		if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount;
    		}
    		
    		temp = curStation;
    		curStation = curLine.travelOneStation(curStation, previous);   
	    	curLine = curStation.getLine();

    		
    		
    		
    		
    		
    		//prints an update on your current location in the network.
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	previous = temp;
	    	
	    	if((hoursCount%swapFreq)==0){
    			dance();
    		}
	    	
	    	curStation.setTrainLine(curLine);

    	}
	    	
	    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
	    	return hoursCount;
    }
    
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName){
    	
    	for(int i =0;i<this.networkLines.length;i++){
			if(this.networkLines[i].getName().equals(lineName)){
				return this.networkLines[i];
			}
			
		}
		throw new LineNotFoundException(lineName);
    	
    }
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}
