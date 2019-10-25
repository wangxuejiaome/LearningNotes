package wxj.me.javase.innerclasses.demostration.controller;

/**
 * Create by 18113881 on 2019/5/8 10 : 10
 */
public class GreenhouseController {
    public static void main(String[] args) {
        GreenhouseControls greenhouseControls = new GreenhouseControls();
        // Instead of hard-writing. you could parse
        // configuration information from a text file here:
        greenhouseControls.addEvent(greenhouseControls.new Bell(900));
        Event[] eventList = {
                greenhouseControls.new ThermostatNight(0),
                greenhouseControls.new LightOn(200),
                greenhouseControls.new LightOff(400),
                greenhouseControls.new WaterOn(600),
                greenhouseControls.new ThermostatDay(1400)
        };
        greenhouseControls.addEvent(greenhouseControls.new Restart(2000, eventList));
        greenhouseControls.addEvent(new GreenhouseControls.Terminate(5000));
        greenhouseControls.run();
    }
}
