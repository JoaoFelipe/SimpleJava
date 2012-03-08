public enum Program {
    ELEMENTO1, ELEMENTO2;

    static void teste() {
        System.out.println("teste");
    }

    public static void main(String[] args) {
        System.out.println("main");
        
    }
    interface I { 
        public void test(); 
    }
    
    public enum C implements I {
        INNER1, INNER2;
        
        @Override
        public void test() {
            System.out.println("test");
        }
    }
}