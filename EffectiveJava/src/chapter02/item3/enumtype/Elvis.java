package chapter02.item3.enumtype;

/**
 * 枚举实现单例
 */
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }
}
