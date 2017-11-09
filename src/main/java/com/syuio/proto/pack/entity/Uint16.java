package com.syuio.proto.pack.entity;


public class Uint16 extends Number implements Comparable<Uint16> {

    private static final long serialVersionUID = 3955020413111712056L;
    private long v;

    public Uint16(int i) {
        v = 0xFFFFFFFFL & i;
    }

    public Uint16(long l) {
        v = l;
    }

    public Uint16(String l) {
        v = Long.valueOf(l);
    }

    public static Uint16 toUInt(int i) {
        return new Uint16(i);
    }

    @Override
    public String toString() {
        return Long.toString(v);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (v ^ (v >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Uint16 other = (Uint16) obj;
        if (v != other.v)
            return false;
        return true;
    }

    public int compareTo(Uint16 rhs) {
        return (int) (this.v - rhs.longValue());
    }

    @Override
    public int intValue() {
        return (int) v;
    }

    @Override
    public long longValue() {
        return v;
    }

    @Override
    public float floatValue() {
        return (float) v;
    }

    @Override
    public double doubleValue() {
        return (double) v;
    }
}
