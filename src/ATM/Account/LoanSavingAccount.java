package ATM.Account;

import ATM.Interface.Loanable;
//贷款不可透支账户
public class LoanSavingAccount extends SavingAccount implements Loanable {
    private double loanAmount;//贷款额度（Task04新增）
    public LoanSavingAccount(){};

    public LoanSavingAccount(Long id, String password, String name, String personId, String email, double balance, int type, double loanAmount) {
        super(id, password, name, personId, email, balance, type);
        this.loanAmount = loanAmount;
    }

    @Override//贷款
    public Account requestLoan(double money) {
        if(money >=0){
            this.setLoanAmount((this.getLoanAmount()+money));
            return this;
        }
        return null;
    }
    @Override//还款
    public Account payLoan(double money) {//余额最低为0
        if(money >=0 && money <= this.getBalance()){
            this.setBalance(this.getBalance() - money);
            this.setLoanAmount(this.getLoanAmount() - money);
            return this;
        }else {
            System.out.printf("余额不足");
            return null;
        }
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public String toString() {
        return "LoanCreditAccount{" +
                "id=" + this.getId() +
                ", password='" + this.getPassword() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", personId='" + this.getPersonId() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", balance=" + this.getBalance() +
                ", type=" + this.getType() +
                ", loanAmount=" + loanAmount +
                "} ";
    }
}
