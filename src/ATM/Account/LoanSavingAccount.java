package ATM.Account;

import ATM.Interface.Loanable;
//贷款不可透支账户
public class LoanSavingAccount extends SavingAccount implements Loanable {
    @Override//贷款
    public Account requestLoan(double money) {
        if(money >=0){
            this.setLoanAmount((this.getLoanAmount()+money));
            return this;
        }
        return null;
    }
    @Override//还款
    public Account payLoan(double money) {
        if(money >=0 && money <= this.getBalance()){
            this.setBalance(this.getBalance() - money);
            this.setLoanAmount(this.getLoanAmount() - money);
            return this;
        }else {
            System.out.printf("余额不足");
            return null;
        }
    }
}
