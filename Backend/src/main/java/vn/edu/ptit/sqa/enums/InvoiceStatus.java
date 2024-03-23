package vn.edu.ptit.sqa.enums;

public enum InvoiceStatus {
    PAID("paid"),
    DEBT("debt"),
    UNPAID("unpaid");

    private String des;

    InvoiceStatus(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
