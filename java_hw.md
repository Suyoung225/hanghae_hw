# 자바 과제


* 다음 코드를 실행하면 출력 결과로 5를 기대했는데 4가 출력되었습니다. 어디에서 잘못 작성된 것일까요?
```
int var1=5;
int var2=2;
double var3=var1/var2;
int var4=(int)(var3*var2);
System.out.println(var4);
```

var1/var2 는 정수 연산으로 결과가 2.5가 아닌 2가 되고, 2를 double 타입의 변수 var3에 저장하므로 var3는 2.0이 됩니다.
따라서 연산 결과 var4는 4가 됩니다.


--
* 다음 코드를 실행했을 때 출력 결과는 무엇입니까? (증감연산자에 대해 알아보세요!)
```
int x=10;
int y=20;
int z = (++x) + (y--);
System.out.println(z);
```

(++x)에는 값이 11, (y--)에는 그대로 20이라는 값이 저장되어 z는 31이 됩니다
연산 후 x는 11, y는 19로 바뀝니다.


--
while문과 Math.random() 메소드를 이용해서 2개의 주사위를 던졌을 때 나오는 눈을 (눈1, 눈2) 형태로 출력하고, 눈의 합이 5가 아니면 계속 주사위를 던지고, 눈의 합이 5이면 실행을 멈추는 코드를 작성해보세요. 눈의 합이 5가 되는 조합은 (1,4), (4,1), (2,3), (3,2)입니다.

```
public class Hello {
    public static void main(String[] args) {
        int a = 0, b = 0;
        System.out.println("시작!");
        while(a+b!=5){
            a = (int)(Math.random()*6)+1;
            b = (int)(Math.random()*6)+1;
            System.out.println("("+a+","+b+")");
        }
        System.out.println("끝!");

    }
}
```
