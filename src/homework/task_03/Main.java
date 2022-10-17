package homework.task_03;
    /*
    Цветочница.
    Определить иерархию цветов.
    Создать несколько объектов-цветов.
    Собрать букет (используя аксессуары) с определением его стоимости.
    Провести сортировку цветов в букете на основе уровня свежести.
    Найти цветок в букете, соответствующий заданному диапазону длин стеблей
     */

    import java.util.Arrays;

    public class Main {
    public static void main(String[] args) {

        Flowers f1= new Flowers("Роза",10,5,8);
        Flowers f2= new Flowers("Ромашка",10,6,10);
        Flowers f3= new Flowers("Тюльпан",10,10,5);
        Flowers f4= new Flowers("Орхидеи",10,7,6);

        Flowers a1 = new Flowers(11,"A - Ленточка");
        Flowers a2 = new Flowers(16,"A - Упаковочная Бумага");
        Flowers a3 = new Flowers(18,"A - Упаковочная Пленка");

        Flowers[] buk_1 = new Flowers[]{f1,f2,f3,f4,a1};
        Flowers[] buk_2 = new Flowers[]{f4,a2,f3,f2,f2,f4};
        Flowers[] buk_3 = new Flowers[]{f4,f3,f3,f2,f2,a3};

        Flowers[][] allBuk = {buk_1, buk_2, buk_3 };

        int counter = 1;
        for (int i = 0; i < allBuk.length; i++ ){
            System.out.println("------Букет----- " + counter);
            buket(allBuk[i]);
            searchStem(allBuk[i],5,6);
            Arrays.sort(allBuk[i]);
            System.out.println(Arrays.toString(allBuk[i]));
            counter++;
        }
    }

        static void searchStem(Flowers[] arrBuk, int from, int to){

            for (int i = 0; i < arrBuk.length; i++ ){
                if(arrBuk[i].getFlowers_name() == null){
                } if (arrBuk[i].getStem() >= from && arrBuk[i].getStem() <= to) {
                    System.out.println( arrBuk[i].getFlowers_name() + " стебель  " + arrBuk[i].getStem());
                }
            }
        }

    public static void buket(Flowers[] arrBuk){
        int price = 0;
        for (int i = 0; i < arrBuk.length; i++ ){
            if(arrBuk[i].getFlowers_name() == null){
                System.out.println( arrBuk[i].getAccessories_name() );
            }else {
                System.out.println( arrBuk[i].getFlowers_name() );
            }
            price += arrBuk[i].getPrice();
        }
        System.out.println("Цена букета = " + price);
    }
}