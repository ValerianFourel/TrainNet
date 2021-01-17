
import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {

if(this.lineMap==null){
			
			return 0;
		} else {
		
		return this.lineMap.length; 
		}// change this!
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		
		if(!(current.hasConnection)){
			return getNext(current);
		} else if(current.hasConnection && !(current.equals(previous.getTransferStation()))){
			
			return current.getTransferStation();
		} else if(current.hasConnection && current.equals(previous.getTransferStation())){
			return getNext(current);
		}
			
		
		
		
	
		
		throw new StationNotFoundException(current.getName());

		// YOUR CODE GOES HERE
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) throws StationNotFoundException {

		if(station.isLeftTerminal()){
			reverseDirection();
			return station.getRight();
		} else if(station.isRightTerminal()){
			reverseDirection();
			return station.getLeft();
		} else if(goingRight){
			return station.getRight();
		} else if(!goingRight) {
			return station.getLeft();
		}
			
			throw  new StationNotFoundException(station.getName());
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) throws StationNotFoundException {

		for(int i =0;i<this.lineMap.length;i++){
			if(this.lineMap[i].getName().equals(name)){
				return this.lineMap[i];
			}
		}
		throw new StationNotFoundException(name);
	}

	public void sortLine() {
		
		
		int n =this.lineMap.length;
		TrainStation temp = new TrainStation(null);

		for(int i =0;i<n+1;i++){

			for(int j=i;j<n;j++){
					
			if(Character.compare(this.lineMap[i].getName().charAt(0),this.lineMap[j].getName().charAt(0))>0)	
			{
					temp=this.lineMap[i];
					this.lineMap[i]=this.lineMap[j];
					this.lineMap[j]=temp;
				}
			}
		}
		
			
					
		this.leftTerminus = this.lineMap[0];
		this.rightTerminus = this.lineMap[this.lineMap.length-1];
		
		
		for(int i = 1; i<n;i++){
			this.lineMap[i].setLeft(this.lineMap[i-1]);
			this.lineMap[i-1].setRight(this.lineMap[i]);
		}
			
		this.lineMap=getLineArray();
		
	}

	public TrainStation[] getLineArray() {

		if(this.leftTerminus.getRight().equals(this.rightTerminus)){
					
			TrainStation[] array = new TrainStation[2];		
			array[0]=this.leftTerminus;
			array[1]=this.rightTerminus;		
			return array;

		} else if(getSize()==2){
			

			
			TrainStation[] array = new TrainStation[3];
			array[0]=this.leftTerminus;		
			array[1]=this.leftTerminus.getRight();		
			array[2]=this.rightTerminus;
			
			return array;
			
		} else if(getSize()==3){
					
			TrainStation[] array = new TrainStation[4];
			
			array[0]=this.leftTerminus;
			array[1]=this.leftTerminus.getRight();
			array[2]=this.rightTerminus.getLeft();
			array[3]=this.rightTerminus;
					
			return array;

			
		} else if(getSize()==4){
					
			TrainStation[] array = new TrainStation[5];			
			
			array[0]=this.leftTerminus;	
			array[1]=this.leftTerminus.getRight();
			array[3]=this.rightTerminus.getLeft();
			array[2]=array[3].getLeft();
			array[4]=this.rightTerminus;
			
					
			return array;
			
			
		} else if(getSize()==5){
				
			
			if(this.rightTerminus.equals(this.lineMap[4])&&(this.leftTerminus.equals(this.lineMap[0]))){
			
			
		
			TrainStation[] array = new TrainStation[5];

			array[0]=this.leftTerminus;	
			array[1]=this.leftTerminus.getRight();
			array[2]=array[1].getRight();
			array[3]=this.rightTerminus.getLeft();
			array[4]=this.rightTerminus;
			
			
			return array;
			
			
			
		
		} else {
			
			
			
			
	
			TrainStation[] array = new TrainStation[5];
			
			
			
			

			array[0]=this.leftTerminus;	
			array[1]=this.leftTerminus.getRight();
			array[2]=array[1].getRight();
			array[3]=this.rightTerminus.getLeft();
			array[4]=this.rightTerminus;
			
			
			return array;
			
			
		}
		
			
		} else {
		

		TrainStation[] array = new TrainStation[5];
		
		
		
		

		array[0]=this.leftTerminus;	
		array[1]=this.leftTerminus.getRight();
		array[2]=array[1].getRight();
		array[3]=this.rightTerminus.getLeft();
		array[4]=this.rightTerminus;
		
		
		return array;
		
		}
		
		
		
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}
	

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		int n=this.lineMap.length;
	//	TrainStation temp = new TrainStation(null);
		
		
		
		
		
		
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[4];
	

		
		
		
		for(int i = 1; i<n;i++){
			shuffledArray[i].setLeft(shuffledArray[i-1]);
			shuffledArray[i-1].setRight(shuffledArray[i]);
		}
		
		
		
	
			
			/* 
			 * not an object problem 
			 */
		
	

		
		
		
		
		


	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2)){
					System.out.println("I want to die in a car crash   "+ current.getName()+"   "+curr2.getName());
					return false;
				}
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
