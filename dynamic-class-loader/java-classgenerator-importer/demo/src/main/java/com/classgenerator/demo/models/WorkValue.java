package com.classgenerator.demo.models;

public class WorkValue {
    private Integer val1;
    private Integer val2;

    public WorkValue(Integer val1, Integer val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public Integer getVal1() {
        return val1;
    }

    public void setVal1(Integer val1) {
        this.val1 = val1;
    }

    public Integer getVal2() {
        return val2;
    }

    public void setVal2(Integer val2) {
        this.val2 = val2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((val1 == null) ? 0 : val1.hashCode());
        result = prime * result + ((val2 == null) ? 0 : val2.hashCode());
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
        WorkValue other = (WorkValue) obj;
        if (val1 == null) {
            if (other.val1 != null)
                return false;
        } else if (!val1.equals(other.val1))
            return false;
        if (val2 == null) {
            if (other.val2 != null)
                return false;
        } else if (!val2.equals(other.val2))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Trabajo [val1=" + val1 + ", val2=" + val2 + "]";
    }

}
