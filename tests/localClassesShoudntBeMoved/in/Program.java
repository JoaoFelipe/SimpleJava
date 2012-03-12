public class Program {

    public static void main(String[] args) {
        System.out.println("iniciando");
        
        
        
        teste();
        System.out.println("finalizando");
    }

    static void teste() {
        class LocalInnerClass {
            int a;
        }
        LocalInnerClass cls = new LocalInnerClass();
    }

}