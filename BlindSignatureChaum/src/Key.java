public class Key {
    private long firstNumber;
    private long module;

    public Key() {
    }

    public Key(long firstNumber, long module) {
        this.firstNumber = firstNumber;
        this.module = module;
    }

    public long getFirstNumber() {
        return firstNumber;
    }

    public long getModule() {
        return module;
    }
}
