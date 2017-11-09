package com.syuio.proto.pack.entity;

import java.math.BigInteger;

public class Uint64 extends Number implements Comparable<Uint64> {

    private static final long serialVersionUID = -1441706982311794007L;
    private BigInteger v;

    public Uint64(int i) {
        if (i < 0) {
            long x = i;
            v = new BigInteger(1, new byte[]{(byte) (x >> 56), (byte) (x >> 48), (byte) (x >> 40),
                    (byte) (x >> 32), (byte) (x >> 24), (byte) (x >> 16), (byte) (x >> 8), (byte) (x)});
        } else {
            v = new BigInteger(String.valueOf(i));
        }
    }

    public Uint64(long x) {
        v = new BigInteger(1, new byte[]{(byte) (x >> 56), (byte) (x >> 48), (byte) (x >> 40),
                (byte) (x >> 32), (byte) (x >> 24), (byte) (x >> 16), (byte) (x >> 8), (byte) (x)});
    }

    public Uint64(String l) {
        v = new BigInteger(l);
    }

    public static Uint64 toUInt(int i) {
        return new Uint64(i);
    }

    public static Uint64 toUInt(long i) {
        return new Uint64(i);
    }


    @Override
    public String toString() {
        return v.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (v.longValue() ^ (v.longValue() >>> 32));
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
        Uint64 other = (Uint64) obj;
        if (v != other.v)
            return false;
        return true;
    }

    @Override
    public int intValue() {
        return this.v.intValue();
    }

    @Override
    public long longValue() {
        return this.v.longValue();
    }

    @Override
    public float floatValue() {
        return this.v.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.v.doubleValue();
    }

    public int compareTo(Uint64 o) {
        return this.v.divide(o.v).intValue();
    }
}
