package com.prototransl.proto.pack.entity;


public class Uint8 extends Number implements Comparable<Uint8> {

    private static final long serialVersionUID = -2010873547061112692L;

    private final long v;

    public Uint8(int i) {
        v = 0xFFFFFFFFL & i;
    }

    public Uint8(String l) {
        v = Long.valueOf(l);
    }

    public static Uint8 toUInt(int i) {
        if (i == 0 || i == 1) {
            return Uint8Cache.cache[i];
        }
        return new Uint8(i);
    }

    public static Uint8 toUInt(boolean i) {
        return toUInt(i ? 1 : 0);
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
        Uint8 other = (Uint8) obj;
        if (v != other.v)
            return false;
        return true;
    }

    public int compareTo(Uint8 rhs) {
        return (int) (this.v - rhs.intValue());
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

    @Override
    public short shortValue() {
        return (short) v;
    }

    private static class Uint8Cache {
        static final Uint8[] cache;

        static {
            cache = new Uint8[2];
            cache[0] = new Uint8(0);
            cache[1] = new Uint8(1);
        }

        private Uint8Cache() {
        }

    }

}
