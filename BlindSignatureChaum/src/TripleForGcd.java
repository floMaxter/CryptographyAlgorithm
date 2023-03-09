public class TripleForGcd {
    private long gcd;
    private long u;
    private long v;

    public TripleForGcd(long gcd, long u, long v) {
        this.gcd = gcd;
        this.u = u;
        this.v = v;
    }

    public long getGcd() {
        return gcd;
    }

    public long getU() {
        return u;
    }

    public long getV() {
        return v;
    }

    public void setGcd(long gcd) {
        this.gcd = gcd;
    }

    public void setU(long u) {
        this.u = u;
    }

    public void setV(long v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "TripleForGcd{" +
                "gcd=" + gcd +
                ", u=" + u +
                ", v=" + v +
                '}';
    }
}
