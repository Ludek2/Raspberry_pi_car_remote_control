import java.awt.AWTException;


public class RobotCtrl {
	
	private HardwareDrive HardwareDrv;
	
	private int currentX;
	private int currentY;
	
	
	
	public RobotCtrl() throws AWTException{
		//robot = new Robot();
		//robot.setAutoDelay(1);
		
		HardwareDrv= new HardwareDrive();
		setCurrentX(0);
		setCurrentY(0);
		stop();
		turnStraight();
	}
	
	 
	public int getCurrentX() {
		return currentX;
	}


	public void setCurrentX(int currentX) {
		if (currentX <= 255 && currentX >= -255) this.currentX = currentX;
		else if( currentX > 0) this.currentX= 255; // maximum value
		else if( currentX < 0) this.currentX= -255; // minimum value
		else this.currentX=0;
		
	}


	public int getCurrentY() {
		return currentY;
	}


	public void setCurrentY(int currentY) {
		if (currentY <= 255 && currentY >= -255) this.currentY = currentY;
		else if( currentY > 0) this.currentY= 255; // maximum value
		else if( currentY < 0) this.currentY= -255; // minimum value
		else this.currentY=0;
	}


	public void process(int x, int y, String event){
		
		switch(event){
			case "MOUSE_MOVE": {
				robotMove( x, y );
				break;
			}
			case "MOUSE_CLICKED": {
				setCurrentX(0);
				setCurrentY(0);
				stop();
				turnStraight();
				break;
			}
		}
		
	}
	
	
	private void robotMove(int x, int y){
		setCurrentX( this.currentX+x);
		setCurrentY( this.currentY+y);
		
		
		System.out.println( "X: "+currentX + " Y: " +currentY );
		if( this.currentX > 100) turnRight();
		else if( this.currentX < -100) turnLeft();
		else turnStraight();
		
		if( this.currentY > 100) goForward();
		else if (this.currentY < -100) goBackward();
		else stop();
		
	}
	
	public void goForward(){
		HardwareDrv.pinSwitch(false,1); //disable backward signal
		HardwareDrv.pinSwitch(true,0); // enable forward signal
	}
	
    public void goBackward(){
    	HardwareDrv.pinSwitch(false,0); //disable forward signal
		HardwareDrv.pinSwitch(true,1); // enable backward signal
	}
    
    public void turnRight(){
    	HardwareDrv.pinSwitch(false,2); //disable left signal
		HardwareDrv.pinSwitch(true,3); // enable right signal
    }
    
    public void turnLeft(){
    	HardwareDrv.pinSwitch(false,3); //disable right signal
		HardwareDrv.pinSwitch(true,2); // enable left signal
    }
    
    public void stop(){
    	HardwareDrv.pinSwitch(false,0);
		HardwareDrv.pinSwitch(false,1); 
    }
    
    public void turnStraight(){
    	HardwareDrv.pinSwitch(false,2); 
		HardwareDrv.pinSwitch(false,3); 
    }


}
