package ATM.Account;

import ATM.Interface.Loanable;
//贷款可透支账户
public class LoanCreditAccount extends CreditAccount implements Loanable {
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
        if(money >= 0){
            this.setBalance(this.getBalance() - money);
            this.setLoanAmount(this.getLoanAmount() - money);
            return this;
        }
        return null;
    }
}
