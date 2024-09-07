import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;//所有账户对象信息
    private long index;//账户数量

    public Bank() {
        this.accounts = new ArrayList<Account>();
        this.index = 0;
    }

    //1.开户
    public Account register(Long id,String password,String repassword,String name,String personID,String email,int type){
        if(password.equals(repassword)){
            if (type == 0) {
                Account account = new SavingAccount(id, password, name, personID, email, 0,type);
                accounts.add(account);
                index++;
                return account;
            } else if (type == 1) {
                Account account = new CreditAccount(id, password, name, personID, email,0,0,0);
                accounts.add(account);
                index++;
                return account;

            } else {
                System.out.println("账户创建失败");;
            }
        }
        return null;
    }
    //2.用户登录
    public Account login(Long id,String password){
       for(Account account :accounts){
           if(account.getId().equals(id) && account.getPassword().equals(password)){
               return account;
           }
       }
        return null;
    }

    //3.用户存款
    public Account deposit(Long id,double money){
        for(Account account :accounts){
            if(account.getId().equals(id)){
                account.setBalance(account.getBalance() + money);
                return account;
            }
        }
        return null;
    }

    //4.用户取款
    public Account withdraw(Long id,String password,double money){
        for (Account account : accounts){
            if (account.getId().equals(id) && account.getPassword().equals(password)){
                account.withdraw(money);
                return account;
            }
        }
        return null;
    }

    //5.设置透支额度
    public Account updateCeiling(Long id,String password,double money){
        for (Account account: accounts){
            if (account.getId().equals(id) && account.getPassword().equals(password)){
                if(account instanceof CreditAccount){//验证账户是否为信用账户
                    ((CreditAccount) account).setCeiling(money);
                }
            }
        }
        return null;
    }

    //6.转账功能
    public boolean transfer(Long from,String passwordFrom,Long to,double money){//转出账户，密码，转入账户，金额
        Account fromAccount = login(from,passwordFrom);
        Account toAccount = login(to,"");
        if(fromAccount.getBalance() >= money){
            for (Account account:accounts){
                if(account.getId() == to){
                    fromAccount.withdraw(money);
                    account.deposit(money);
                    return true;
                }else {
                    System.out.printf("账号错误");
                }
            }
        }
        return false;
    }

    //7.统计银行所有账户余额总数
    public double balanceSum(){
        double sum = 0;
        for(Account account : accounts){
            sum += account.getBalance();
        }
        return sum;
    }

    //8.统计所有信用账户透支额度总数
    public double ceilingSum(){
        double sum = 0;
        for(Account account : accounts){
            if (account instanceof CreditAccount){
                sum += ((CreditAccount) account).getCeiling();
            }
        }
        return sum;
    }

    //9.重写toString方法：查看对象的内容
    @Override
    public String toString(){
        if(accounts != null){
            for (Account account : accounts){
                return "账户信息:"+account.toString();
            }
        }
        return null;
    }
}
