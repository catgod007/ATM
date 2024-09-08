package ATM.Interface;

import ATM.Account.Account;

//贷款功能的接口
public interface Loanable {
//    a)贷款(requestLoan)
//    参数：money贷款金额
//    返回类型：Account
    public Account requestLoan(double money);

//    b)还贷(payLoan)
//    参数：money还贷款金额
//    返回类型：Account
    public  Account payLoan(double money);
}
