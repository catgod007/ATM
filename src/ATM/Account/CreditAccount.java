package ATM.Account;

import ATM.Account.Account;

public class CreditAccount extends Account {
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
        }else if (this.getBalance()+ceiling > money){
            this.setBalance(this.getBalance()-money);
        }else{
            System.out.println("信用额度已用尽");
        }
        return this;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "id=" + this.getId() +
                ", password='" + this.getPassword() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", personId='" + this.getPersonId() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", balance=" + this.getBalance() +
                ", type=" + this.getType() +
                ", ceiling=" + ceiling+
                '}';
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

}

