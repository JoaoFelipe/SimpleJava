public class Program {

    public static void main(String[] args) {
        System.out.println("test");
    }

    @Edible(true)
    @Overridable(value = true)
    static void teste() {
        System.out.println("teste");
    }

}



