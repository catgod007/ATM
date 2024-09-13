package ATM;

import ATM.Account.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

//银行类
public class Bank {
    private List<Account> accounts = newAccounts();;//所有账户对象信息
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

    //0.账号id自动生成
    private Long IDGenerator() {//存储id的文件存在 就读取文件中的id然后生产自增1后存入文件;不存在 就将现在生成的ID存入文件,进行文件存在的操作
        File file = new File("id.txt");
        if (!file.exists()) {
            return writer1(file);
        }else {
            return write(file);
        }
    }
    //0.1无存储id的文件时，产生id,写入id+1,生成新的id信息文件
    private Long writer1(File file){
        try {
                StringBuilder stringBuilder = new StringBuilder();
                String coNCode = "86";//国家代码
                String postCode = "404100";//邮编
                Calendar calendar = Calendar.getInstance();//获取Calendar对象
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                stringBuilder.append(coNCode);
                stringBuilder.append(postCode);
                stringBuilder.append(year);
                stringBuilder.append(month);
                //格式化
                DecimalFormat df = new DecimalFormat("0000");
                String str = df.format(index + 1);
                stringBuilder.append(str);
                //写入存储id的文件中
                FileOutputStream fos = new FileOutputStream(file);
                System.out.println(stringBuilder.toString());
                Long w = Long.valueOf(stringBuilder.toString());
                fos.write((String.valueOf(w+1)).getBytes());
//                System.out.println((String.valueOf(w+1)));
                fos.close();
                return w;
        } catch (IOException i) {
            System.out.println("第一次写入失败！");
            i.printStackTrace();
        }
        return null;
    }
    //0.2 有存储id的文件时，读取id,写入id+1,生成新的id信息文件
    private Long write(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            //读取存储id的文件中id
            byte[] cbuf = new byte[4];
            int len;
            String str = new String();
            while ((len = fis.read(cbuf)) != -1) {
                str += (new String(cbuf,0,len));
            }
            fis.close();
            System.out.println(str);
            Long w = Long.valueOf(str.toString());
//            System.out.println(str);
            //自增写入文件
            FileOutputStream fos = new FileOutputStream(file);
                fos.write((String.valueOf(w + 1)).getBytes());
//                System.out.println((String.valueOf(w + 1)));
                fos.close();
                return w;
            } catch(Exception e){
                System.out.println("自动生成ID失败！");
                e.printStackTrace();
            }
            return null;
        }

    //1.开户
    public Account register(String password,String repassword,String name,String personID,String email,double balance,int type) {
        Account account = null;
        if(password.equals(repassword)){
            if (type == 0) {
                account = new SavingAccount(IDGenerator(), password, name, personID, email, balance,type);
            } else if (type == 1) {
                account = new CreditAccount(IDGenerator(), password, name, personID, email,balance,type,3000);
            } else if (type == 2) {
                account = new LoanSavingAccount(IDGenerator(), password, name, personID, email,balance,type,1000);
            } else if (type == 3) {
                account = new LoanCreditAccount(IDGenerator(), password, name, personID, email,balance,type,3000,1000);
            } else {
                System.out.println("账户创建失败");;
            }

            if(account != null){//当账户创建时，就将账户信息写入相应的存储文件
                accounts.add(account);
                saveAccount(accounts);
                index++;
            }
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

    //5.设置透支额度(透支额度只会在设置时改变)
    public Account updateCeiling(Long id,String password,double money){
        for (Account account: accounts){
            if (account != null && id.equals(account.getId()) && password.equals(account.getPassword())){
//                if(account instanceof CreditAccount){//验证账户是否为信用账户
                if(account.getType() == 1 || account.getType() == 3){//验证账户是否为信用账户
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
            } else if (account instanceof LoanCreditAccount){
                CeilingSum += ((LoanCreditAccount) account).getCeiling();
            }
        }
        return CeilingSum;
    }

    //9. 贷  款(requestLoan)    //参数：id 账户，money贷款金额
    public Account requestLoan(Long id,double money){
        Account account = requestLoan(id, money);
        return account;
    }

    //10. 还贷款(requestLoan)  //参数：id 账户，money还贷款金额
    public Account payLoan(Long id,double money){
        Account account = payLoan(id, money);
        return account;
    }

    //11. 统计所有账户贷款的总额(totoal)
    public double totoal(){
        double totalSum = 0L;
        for (Account account:accounts ) {
            if(account instanceof LoanSavingAccount){
                totalSum += ((LoanSavingAccount) account).getLoanAmount();
            }else if(account instanceof LoanCreditAccount){
                totalSum += ((LoanCreditAccount) account).getLoanAmount();
            }
        }
        return totalSum;
    }

    //12. 能够打印所有用户的总资产排名
    //1）、一个用户可能会有多个账号,以身份证号为准.
    //2）、总资产指多个账户余额的总和,不需要考虑贷款账户的贷款额
    public void assetRankingSort(){
        //创建收集身份证的列表
        HashSet<String> idSet = new HashSet<String>();//自动去查(set是否有自动排序)
        for (Account account:accounts) {
                idSet.add(account.getPersonId());
        }
        //创建列表用于存储（身份证id：余额总和）
        Map<String,Double> assetRankingHashMap = new HashMap<String,Double>();
        for(String str : idSet){
            double sum = 0.0;
            for (Account account:accounts) {
                if(str != null && str.equals(account.getPersonId())){
                    sum += account.getBalance();
                }
            }
            assetRankingHashMap.put(str,sum);
        }
        System.out.println(assetRankingHashMap);
        //TreeSet实现比较器排序（按照Map对象(身份证，总余额)中的总余额排序插入）
        TreeSet<Map.Entry<String, Double>> sd = new TreeSet<>(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (int) (o2.getValue()-o1.getValue());
            }
        });
        //循环Map每个键值对象存入,就完成排行榜
        for (Map.Entry<String, Double> stringDoubleEntry : assetRankingHashMap.entrySet()) {
            sd.add(stringDoubleEntry);
        }
        System.out.println("sd: "+sd);

        for (Map.Entry<String,Double> mapping : sd)
        {
            System.out.print("身份证号： " + mapping.getKey());
            System.out.println("总余额： " + mapping.getValue());
        }
    }

    //13.对象序列化存入文件(全量覆盖)
    private void saveAccount(List<Account> accounts) {
        try {
            //创建序列化流对象
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Accounts.txt"));
            //写入对象
            out.writeObject(accounts);
            //释放资源
            out.close();
            System.out.println("对象序列化成功！");
        } catch (IOException i) {
            System.out.println("对象序列化失败！");
            i.printStackTrace();
        }
    }
    
    //14.将文件中的反序列化出来成为对象
    private ArrayList<Account> outAccount(){
        ArrayList<Account> accounts = null;
        try{
            //创建反序列化流
            FileInputStream fi = new FileInputStream("Accounts.txt");
            ObjectInputStream in = new ObjectInputStream(fi);
            //读取对象
            accounts = (ArrayList<Account>) in.readObject();
            in.close();
            fi.close();
            System.out.println("对象反序列化成功！");
        }catch (Exception i){
            System.out.println("对象反序列化失败！");
            i.printStackTrace();
        }
        //打印输出
        if(accounts.size() > 0){
            for(Account account:accounts){
                System.out.println(account);
            }
        }
        return accounts;
    }

    //15.将序列化的对象赋值给accounts集合，供其他方法使用
    private ArrayList<Account> newAccounts(){
        if(new File("Accounts.txt").exists()){//存在序列化文件就取出序列化文件中的对象集合
            //将已序列化的对象集合取出
            return outAccount();
        }else {//账户信息在创建时就序列化写入文件，故如果从文件不存在则账户不存在
            return new ArrayList<Account>();
    }
}


















}
