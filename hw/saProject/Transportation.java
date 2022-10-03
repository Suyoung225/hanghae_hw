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

    // 버스, 택시 모두 초기 주유량 100, 속도 40, 승객 0
    public Transportation(UUID num,  int maxPassengers,int fare, boolean status) {
        this.num = num;
        this.gas = 100;
        this.speed = 40;
        this.passengers = 0;
        this.maxPassengers = maxPassengers;
        this.fare = fare;
        this.status = status;
    }

    @Override
    public void takeRide(int newPassengers) {

    }


}
