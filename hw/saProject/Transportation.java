package saProject;

import java.util.UUID;

public class Transportation implements TakeRide{
    private UUID num;
    int gas;
    int speed;
    int passengers;
    int maxPassengers;
    int fare;
    boolean status; // 버스에서 true는 운행, false는 차고지행, 택시에서 true는 운행, false는 일반

    // Getter
    public UUID getNum(){
        return this.num;
    }
    // Setter
    public void setNuM(UUID name){
        this.num = num;
    }

    public Transportation(UUID num, int gas, int speed, int passengers, int maxPassengers,int fare, boolean status) {
        this.num = num;
        this.gas = gas;
        this.speed = speed;
        this.passengers = passengers;
        this.maxPassengers = maxPassengers;
        this.fare = fare;
        this.status = status;
    }
    // 버스, 택시 모두 초기 주유량 100, 속도 40, 승객 0
    public Transportation(UUID num,  int maxPassengers,int fare,boolean status) {
        this(num,100,40,0, maxPassengers, fare, status);
    }


    @Override
    public void takeRide(int newPassengers) {

    }


}
