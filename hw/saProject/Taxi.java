package saProject;

import java.util.UUID;

public class Taxi extends Transportation implements ChangeGas, ChangeSpeed {

    int minDistance; // 기본거리
    int distanceFares; // 거리당 요금
    int totalFare; // 누적 요금

    public Taxi(UUID num,  int maxPassengers, int fare, boolean status, int minDistance,  int distanceFares, int totalFare) {
        super(num, maxPassengers, fare,status);
        this.minDistance = minDistance;
        this.distanceFares = distanceFares;
        this.totalFare = totalFare;
    }
    // 택시 객체 생성 시 최대 승객수 4, 기본 요금 3000, 상태 일반, 기본 거리(최소 거리) 1, 거리당 추가요금: 1000
    public Taxi() {
        this(UUID.randomUUID(),4,3000,false,2,1000,0);
    }

    // 승객 탑승
    public void takeRide(int newPassengers, String destination, int distance) {
        if(this.gas<10) {
            System.out.println("주유가 필요합니다.탑승이 불가능합니다.");
        }else if(this.status){
            System.out.println("다른 승객이 타고 있습니다. 탑승이 불가능합니다");
        } else if(newPassengers > this.maxPassengers){
            System.out.println("최대 승객 수가 초과되었습니다. 탑승이 불가능합니다");
        } else{
            this.status = true; // 운행중(false)으로 택시 상태 변경
            System.out.printf("승객 %d 명이 택시에 탑승하였습니다\n",newPassengers);
            System.out.println("목적지: "+destination);
            System.out.println("목적지까지 거리: "+distance);
            if(distance < this.minDistance){
                System.out.println("지불할 요금: "+ this.fare);
                this.totalFare += this.fare;
            }else{
                System.out.println("지불할 요금: "+ (this.fare+this.distanceFares*(distance-this.minDistance)));
                this.totalFare += (this.fare+this.distanceFares*(distance-this.minDistance));
            }
            System.out.println("상태: 운행중");
        }
    }


    @Override // 주유량 변경
    public void changeGas(int gas) {
        this.gas += gas;
        System.out.println("주유랑: "+this.gas);
        if(this.gas < 10){
            System.out.println("상태: 운행 블가");
            System.out.printf("누적 요금: %d원\n",this.totalFare);
            System.out.println("주유가 필요합니다!!!");
        }
    }

    @Override // 속도 변경
    public void changeSpeed(int speed) {
        this.speed += speed;
        System.out.println("현재 속도:" + this.speed);
    }

    // 요금 결제
    public void payment(){
        if(this.status){
            System.out.println("주유량: "+this.gas);
            System.out.println("누적 요금:"+ this.totalFare);
            this.status = false;
        }else{ // 승객을 태우고 있지 않음
            System.out.println("결제할 고객이 없습니다.");
        }
    }
}
