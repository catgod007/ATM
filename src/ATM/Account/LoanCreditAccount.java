package ATM.Account;

import ATM.Interface.Loanable;
//贷款可透支账户
public class LoanCreditAccount extends CreditAccount implements Loanable {
    private double loanAmount;//贷款额度（Task04新增）
    public LoanCreditAccount() {
    }

    public LoanCreditAccount(Long id, String password, String name, String personId, String email, double balance, int type, double ceiling, double loanAmount) {
        super(id, password, name, personId, email, balance, type, ceiling);
        this.loanAmount = loanAmount;
    }

    @Override
    public Account requestLoan(double money) {
        if(money >=0){
            this.setLoanAmount((this.getLoanAmount()+money));
            return this;
        }
        return null;
    }

    @Override//还款
    public Account payLoan(double money) {
        if((this.getBalance()+this.getCeiling())>=money){//余额的最低值为-信用额度
            this.setBalance(this.getBalance() - money);
            this.setLoanAmount(this.getLoanAmount() - money);
            return this;
        }
        return null;
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
                ", ceiling=" + this.getCeiling()+
                ", loanAmount=" + loanAmount +
                "} ";
    }
}
