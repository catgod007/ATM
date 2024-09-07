public class CreditAccount extends Account{
    private double ceiling;//信用额度

    public CreditAccount() {
    }

    public CreditAccount(Long id, String password, String name, String personId, String email, double balance, int type, double ceiling) {
        super(id, password, name, personId, email, balance,type);
        this.ceiling=ceiling;
    }

    //取款
    public Account withdraw(double money){
        if(this.getBalance()+ceiling < money){
            System.out.println("余额不足，请重新输入取款金额");
        }else if (this.getBalance() >= money){
            this.setBalance(this.getBalance()-money);
        } else if (this.getBalance() < money) {
            this.setCeiling(ceiling - (money-this.getBalance()));
            this.setBalance(0.0);
        }else {
            System.out.println("机器故障，请联系银行工作人员");
        }
        return this;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "ceiling=" + ceiling +
                "} " + super.toString();
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }
}

