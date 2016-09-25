import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;




public class HardwareDrive {

	final GpioController gpio;

	final GpioPinDigitalOutput pin0;
	final GpioPinDigitalOutput pin1;
	final GpioPinDigitalOutput pin2;
	final GpioPinDigitalOutput pin3;
	
	public HardwareDrive(){
		gpio = GpioFactory.getInstance();
		pin0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.LOW);
		pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
		pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW);
		pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.LOW);
		//UART!!!!!!!!!!!!!!!!!!!!!!!!
		//UART uart= new UART();
			
	}
	
	public void pinSwitch(boolean value, int number){
		
		
			 switch (number) {
			 	case 0: 
			 			if(value==false){
			 				pin0.low();
			 				System.out.println("--> GPIO 0 state should be: OFF");
			 			}
			 			else {
			 				pin0.high();
			 				System.out.println("--> GPIO 0 state should be: ON");
			 			}
			 			break;
			 	case 1:
			 			if(value==false){
			 				pin1.low();
			 				System.out.println("--> GPIO 1 state should be: OFF");
			 			}
			 			else {
			 				pin1.high();
			 				System.out.println("--> GPIO 1 state should be: ON");
			 			}
			 			break;
			 	case 2: 
		 				if(value==false){
		 					pin2.low();
		 					System.out.println("--> GPIO 2 state should be: OFF");
		 				}
		 				else {
		 					pin2.high();
		 					System.out.println("--> GPIO 2 state should be: ON");
		 				}
		 				break;
			 	case 3:
		 				if(value==false){
		 					pin3.low();
		 					System.out.println("--> GPIO 3 state should be: OFF");
		 				}
		 				else {
		 					pin3.high();
		 					System.out.println("--> GPIO 3 state should be: ON");
		 				}
		 				break;
			}	
	}
}
