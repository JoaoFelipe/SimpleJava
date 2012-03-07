public class Program {

    public static void main(String[] args) {
        System.out.println("main");
    }
    interface I { 
        public void test(); 
    }
    
    class C implements I {

        @Override
        public void test() {
            System.out.println("test");
        }
    }
}