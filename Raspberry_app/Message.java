import java.util.Scanner;


public class Message {
	
	public enum member{
		X("INT"), Y("INT"), MOUSE_MOVE("STRING"), MOUSE_CLICKED("STRING");;
		
		public String type;
		
		private member(String type){
			this.type=type;
		}
	}
	
	private int x;
	private int y;
	private String event; 
	
	
	public Message(String received_string){
		
		String [] devided_str = received_string.split("\\s*&\\s*");
		
		for(int i=0; i<devided_str.length; i++){
			
			if(devided_str[i].contains("X")) setX( Integer.parseInt(devided_str[i].replaceAll("[X=]", "")));
			else if(devided_str[i].contains("Y")) setY(Integer.parseInt(devided_str[i].replaceAll("[Y=]", "")));
			else if(devided_str[i].contains("MOUSE_MOVE"))setEvent(devided_str[i]);
			else if(devided_str[i].contains("MOUSE_CLICKED"))setEvent(devided_str[i]);
			//else if(devided_str[i].contains("MOUSE_MOVE"))Mouse_move = devided_str[i].replace("MOUSE_MOVE=", "");
		//find values with consideration of types
		}
		System.out.println("x:"+ x);
		System.out.println("y:"+ y);
		
	}

	public String getEvent(){
		return event;
	}
	
	public int getX() {
		return x;
	}

	public void setEvent(String event){
		this.event=event;
	}

	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}
	
	
}
