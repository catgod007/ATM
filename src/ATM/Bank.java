package ATM;

import ATM.Account.Account;
import ATM.Account.CreditAccount;
import ATM.Account.SavingAccount;

import java.text.DecimalFormat;
import java.util.*;

//银行类
public class Bank {
    private ArrayList<Account> accounts = new ArrayList<Account>();;//所有账户对象信息
    private int index = 0;//账户数量
    //饿汉式单例
    private static Bank bank = new Bank();
    public static Bank getBank() {
        return bank;
    }
    private  Bank() {
    }
    //懒汉式单例
//    private static Bank bank = null;
//    public static Bank getBank() {
//        if(bank == null){
//            bank = new Bank();
//        }
//        return bank;
//    }
//    private Bank() {
//    }

    //账号id自动生成
    private Long IDGenerator() {
        StringBuilder stringBuilder = new StringBuilder();
        String coNCode = "86";//国家代码
        String postCode = "404100";//邮编
        Calendar calendar = Calendar.getInstance();//获取Calendar对象
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        stringBuilder.append(coNCode);
        stringBuilder.append(postCode);
        stringBuilder.append(year);
        stringBuilder.append(month);
        //格式化
        DecimalFormat df = new DecimalFormat("0000");
        String str = df.format(index + 1);
        stringBuilder.append(str);
        return Long.valueOf(stringBuilder.toString());
    }

    //1.开户
    public Account register(String password,String repassword,String name,String personID,String email,double balance,int type){
        Account account = null;
        if(password.equals(repassword)){
            if (type == 0) {
                account = new SavingAccount(IDGenerator(), password, name, personID, email, balance,type);
                IDGenerator();
            } else if (type == 1) {
                account = new CreditAccount(IDGenerator(), password, name, personID, email,balance,type,3000);
                IDGenerator();
            } else {
                System.out.println("账户创建失败");;
            }
            accounts.add(account);
            index++;
            return account;
        }
        return null;
    }
    //2.用户登录
    public Account login(Long id,String password){
       for(Account account :accounts){
           if(account != null && id.equals(account.getId()) && password.equals(account.getPassword())){
               return account;
           }
       }
        return null;
    }

    //3.用户存款
    public Account deposit(Long id,double money){
        for(Account account :accounts){
            if( account != null && id.equals(account.getId())){
                account.deposit(money);
                return account;
            }
        }
        return null;
    }

    //4.用户取款
    public Account withdraw(Long id,String password,double money){
        for (Account account : accounts){
            if (account != null && id.equals(account.getId()) && password.equals(account.getPassword())){
                account.withdraw(money);
                return account;
            }
        }
        return null;
    }

    //5.设置透支额度
    public Account updateCeiling(Long id,String password,double money){
        for (Account account: accounts){
            if (account != null && id.equals(account.getId()) && password.equals(account.getPassword())){
//                if(account instanceof CreditAccount){//验证账户是否为信用账户
                if(account.getType() == 1){//验证账户是否为信用账户
                    double d = ((CreditAccount)account).getCeiling()+money;
                    ((CreditAccount)account).setCeiling(d);
                    return account;
                }
            }
        }
        return null;
    }

    //6.转账功能
    public boolean transfer(Long from,String passwordFrom,Long to,double money){//转出账户，密码，转入账户，金额
        boolean flag = false;
        Account fromAccount = withdraw(from,passwordFrom,money);
        if(fromAccount != null){
           Account toAccount = deposit(to,money);
           if(toAccount != null){
               flag = true;
           }
        }
        return flag;
    }

    //7.统计银行所有账户余额总数
    public double balanceSum(){
        double BalanceSum = 0.0;
        for(Account account : accounts){
            BalanceSum += account.getBalance();
        }
        return BalanceSum;
    }

    //8.统计所有信用账户透支额度总数
    public double ceilingSum(){
        double CeilingSum = 0;
        for(Account account : accounts){
            if (account instanceof CreditAccount){
                CeilingSum += ((CreditAccount) account).getCeiling();
            }
        }
        return CeilingSum;
    }

    //a)贷  款(requestLoan)
    //参数：id 账户，money贷款金额
    //      (Long id , double money)
    //返回类型：Account
    public Account requestLoan(Long id,double money){
        Account account = requestLoan(id, money);
        return account;
    }

    //b)还贷款(requestLoan)
    //参数：id 账户，money还贷款金额
    //      (Long id , double money)
    //返回类型：Account
    public Account payLoan(Long id,double money){
        Account account = payLoan(id, money);
        return account;
    }

    //c)统计所有账户贷款的总额(totoal)
    //参数：无
    //返回类型：double
    public double totoal(){
        double totalSum = 0L;
        for (Account account:accounts ) {
            totalSum += account.getLoanAmount();
        }
        return totalSum;
    }

    //为Bank类添加一个方法，能够打印所有用户的总资产排名（提高部分）
    //说明:
    //1）、一个用户可能会有多个账号,以身份证号为准.
    //2）、总资产指多个账户余额的总和,不需要考虑贷款账户的贷款额
    public void assetRankingSort(){//有待优化
        //创建收集身份证的列表
        HashSet<String> idSet = new HashSet<String>();//自动去查
        for (Account account:accounts) {
                idSet.add(account.getPersonId());
        }
        //创建列表用于存储（身份证id：余额总和）
        Map<String,Double> assetRankingTreeMap = new TreeMap<String,Double>();
        for(String str : idSet){
            double sum = 0.0;
            for (Account account:accounts) {
                if(str != null && str.equals(account.getPersonId())){
                    sum += account.getBalance();
                }
            }
            assetRankingTreeMap.put(str,sum);
        }
        //安装余额总和排序
        //将Map转换成List
        List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(assetRankingTreeMap.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String,Double>>(){
            @Override
            public int compare(Map.Entry<String,Double> asset1, Map.Entry<String,Double> asset2)
            {
                Double a = asset1.getValue();
                Double b = asset2.getValue();

                //按照总余额降序
                return b.compareTo(a);
            }
        });

        //遍历排行榜列表
        System.out.println("assetRankingTreeMap集合,按照总余额降序：");
        for (Map.Entry<String,Double> mapping : list)
        {
            System.out.println("身份证号：" + mapping.getKey());
            System.out.println("总余额：" + mapping.getValue());
        }
    }
}
