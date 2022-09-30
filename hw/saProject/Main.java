package saProject;

public class Main {
    public static void main(String[] args) {
        String str = "-".repeat(15);
        System.out.println("\n"+str+"버스"+str+"\n");

        /*Bus bus1 = new Bus();
        Bus bus2 = new Bus();

        Transportation[] bus = {bus1, bus2};
        for (Transportation b : bus) {
            System.out.println(b.num+"번 버스 운행 시작");
        }
        if(!bus1.num.equals(bus2.num)) System.out.println("버스 번호가 다릅니다");

        System.out.println("\n"+str+"승객 3명 탑승");
        bus1.takeRide(3);

        System.out.println("\n"+str+"과속 가즈아");
        bus1.changeSpeed(50);

        System.out.println("\n"+str+"주유량 40 감소");
        bus1.changeGas(-50);

        System.out.println("\n"+str+"차고지행");
        bus1.changeStatus(true);

        System.out.println("\n"+str+"주유하고 다시 운행");
        bus1.changeGas(20);
        bus1.changeStatus(false);

        System.out.println("\n"+str+"승객 20명 탑승");
        bus1.takeRide(20);

        System.out.println("\n"+str+"주유량 65 감소");
        bus1.changeGas(-65); //주유량 부족

        System.out.println("\n"+str+"승객 11명 탑승 시도");
        bus1.takeRide(11); // 최대 승객 수 초과

        System.out.println("\n"+str+"승객 5명 탑승");
        bus1.takeRide(5);

        System.out.println("\n"+str+"주유량 5 감소");
        bus1.changeGas(-5);
*/

        System.out.println("\n"+str+"택시"+str+"\n");

        // 택시 두 대
        Taxi taxi1 = new Taxi();
        Taxi taxi2 = new Taxi();
        Transportation[] taxi = {taxi1, taxi2};
        for (Transportation t : taxi) {
            System.out.println(t.num+"번 택시");
        }
        if(!taxi1.num.equals(taxi2.num)) System.out.println("택시 번호가 다릅니다");


        System.out.println("\n"+str+"5명 탑승 시도");
        taxi1.takeRide(5,"서울역",5);

        System.out.println("\n"+str+"4명 탑승");
        taxi1.takeRide(4,"광화문",4);

        System.out.println("\n"+str+"주유량 -40");
        taxi1.changeGas(-40);

        System.out.println("\n"+str+"2명 추가 탑승 시도");
        taxi1.takeRide(2,"뚝섬",2);

        System.out.println("\n"+str+"요금 결제");
        taxi1.payment();

        System.out.println("\n"+str+"주유량 -60");
        taxi1.changeGas(-60);

        System.out.println("\n"+str+"2명 탑승 시도");
        taxi1.takeRide(2,"뚝섬",2);

        System.out.println("\n"+str+"주유량 50");
        taxi1.changeGas(50);

        System.out.println("\n"+str+"3명 탑승 시도");
        taxi1.takeRide(3,"반포한강공원",5);

        System.out.println("\n"+str+"속도 40 추가");
        taxi1.changeSpeed(40);

        System.out.println("\n"+str+"요금 결제");
        taxi1.payment();
    }
}
