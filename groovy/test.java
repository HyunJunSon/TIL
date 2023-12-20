
class Test {
    static int method(String arg) {
        return 1;
    }
    static int method(Object arg) {
        return 2;
    }
    public static void main(String[] args) {

        Object o = "Object";
        int result = method(o);

        System.out.println(result);
    }
}