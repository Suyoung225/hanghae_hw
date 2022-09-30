package saProject;

import java.util.UUID;

public class Bus extends Transportation implements ChangeStatus, ChangeGas, ChangeSpeed {

    // 버스 객체 생성시  최대 승객수 30, 요금 1000
    public Bus() {
        super(UUID.randomUUID(),30, 1000,true);
    }


    // 버스 상태 변경
    @Override
    public void changeStatus(boolean toGarage) {
        System.out.println("주유량: "+this.gas);
        if(this.gas <= 0 || toGarage) {
            this.status = false; // 버스 상태 차고지행으로 변경
            System.out.println("차고지로 갑니다~~~~");
            this.passengers = 0; // 탑승 승객 0명으로 초기화
        }else if(gas < 10) {
            System.out.println("주유가 필요합니다!!!!");
        }else if(!toGarage){
            System.out.println("다시 운행 레츠기릿!!!");
            this.status = true; // 버스 상태 운행중으로 변경
        }
    }


    // 승객 탑승
    public void takeRide(int newPassengers) {
        if(this.passengers+newPassengers > this.maxPassengers){
            System.out.println("탑승 가능 인원이 초과되었습니다. 탑승이 불가능합니다");
        }else if(!this.status){
            System.out.println("차고지행 버스입니다. 탑승이 불가능합니다.");
        }else{
            this.passengers += newPassengers;
            System.out.println("총 탑승 승객 수: "+ this.passengers);
            System.out.println("잔여 승객 수: "+ (this.maxPassengers - this.passengers));
            System.out.println("요금 확인: " + this.passengers*this.fare);
        }
    }

    // 주유량 변경
    @Override
    public void changeGas(int gas) {
        this.gas += gas;

        if(!this.status){ // 차고지에 있는 경우
            System.out.println("상태: 차고지행");
        }else if(this.gas<10){ // 주유량이 10보다 낮은 경우 차고지행이 되거나, 경고 메시지 뜨도록 changeStatus 함수 호출
            changeStatus(false);
        }else{ //운행 중이며 주유량이 10 이상인 경우 주유량 출력
            System.out.println("주유량: "+this.gas);
        }
    }

    // 속도변경 및 주유량 확인
    @Override
    public void changeSpeed(int speed) {
        this.speed += speed;
        System.out.println("현재 속도:" + this.speed);
        System.out.println("주유량을 확인해 주세요");
        if(this.gas<10)  System.out.println("주유가 필요합니다!!!!");
    }
}
