package uk.ac.aber.dcs.cs12320.main_assignment;

abstract class Driver {
    private int token;
    String name;
    int receiptNum;

    int getToken() {
        return token;
    }

    void setToken(int token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    void setName(String name){
        this.name = name;
    }

    int getReceiptNum(){
        return receiptNum;
    }

    void setReceiptNum(int receiptNum){
        this.receiptNum = receiptNum;
    }
}
