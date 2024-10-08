package ATM.Account;

import ATM.Account.Account;

public class SavingAccount extends Account {
    public SavingAccount() {
    }

    public SavingAccount(Long id, String password, String name, String personId, String email, double balance,int type) {
        super(id, password, name, personId, email, balance,type);
    }

    //取款
    public Account withdraw(double money){
        if(this.getBalance() < money){
            System.out.printf("余额不足，请重新输入取款金额");
        }else {
            this.setBalance(this.getBalance() - money);;
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
                '}';
    }

}
