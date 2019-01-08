import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

public class TimeManage extends ManageSystem {
    private String userName;
    private Date beginTime;
    private Date overTime;
    private double totalTime;
    TreeSet<ItemTime> timeItems = new TreeSet<ItemTime>();

    //String转日期
    public Date StrToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = null;
        date = format.parse(str);
        return date;
    }

    //日期转String
    public String DateToStr(Date data){

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String str = format.format(data);
        return str;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    //計算時間差
    public double calculateTotalTime(Date beginTime,Date overTime){
        //System.out.println("結束時間getTime:"+overTime.getTime()+"開始時間getTime："+beginTime.getTime());
        double x = ((double)(beginTime.getTime())-(double)(overTime.getTime()))/(60*60*1000);
        return x;
    }

    @Override
    public void inputInitialInfo(String userName, Double totalTime) {
        setUserName(userName);
        setTotalTime(totalTime);
    }

    @Override
    public int chooseCategory(String category) {
        int virtualCategory;
        if (category.equals("承諾時間")){
            virtualCategory = 3;
        }else if (category.equals("維護時間")){
            virtualCategory = 2;
        }else {
            virtualCategory = 1;
        }
        return virtualCategory;
    }

    @Override
    public int choosePriority(String priority) {
        int virtualPriority;
        if (priority.equals("緊急")) {
            virtualPriority = 3;
        }else if (priority.equals("重要")){
            virtualPriority = 2;
        }else {
            virtualPriority = 1;
        }
        return virtualPriority;
    }

    public void addItem(String itemName, double cost, int category, int priority,String begin,String over) {
        ItemTime item = new ItemTime(itemName,cost,category,priority,begin,over);
        timeItems.add(item);
    }

    public   void addItem(String itemName, double cost, int category, int priority) {

    }


    @Override
    public void output() {
        double sum = 0.0,remain=0.0;
        int flag=0;
        String begin="",begin2="",over="",tmp="",subbegin="",tmp2="",subbegin2="";
        double b=0.0,o=0.0;
        Iterator<ItemTime> itemIterator = timeItems.iterator();
        while(itemIterator.hasNext()){
            ItemTime item = itemIterator.next();
            sum += item.getCost();
            remain = getTotalTime()-sum;
            if(sum<=getTotalTime()){

                if(flag==0){
                    System.out.println("可支配時間:   "+item.getBeginTime()+"--"+item.getOverTime()+"   共可支配總時間："+(int)getTotalTime()+"時"+(int)((getTotalTime()-(int)getTotalTime())*60.0)+"分    ");//(int)remain+"時"+(int)((remain-(int)remain)*60)+"分    "
                    begin=item.getBeginTime(); //第一每段開始
                    flag=1;
                }
                try {
                    b=((double)(StrToDate(begin).getTime()))/(60*60*1000)+8.0;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                o = b+item.getCost();
                if(o-(int)o!=0){

                    if((int)((o-(int)o)*60)<10){
                        tmp2="0"+String.valueOf((int)((o-(int)o)*60));
                    }else{
                        tmp2=String.valueOf((int)((o-(int)o)*60));
                    }
                    subbegin2 = begin.substring(3,5);//
                    begin2=begin.replaceFirst(subbegin2,tmp2);
                    //System.out.println(begin.replaceFirst(subbegin2,tmp2));
                }
                if(o<10){
                    tmp="0"+String.valueOf((int)o);
                } else {
                    tmp=String.valueOf((int)o);
                }
                subbegin = begin.substring(0,2);
                if(o-(int)o!=0){
                    over=begin2.replaceFirst(subbegin,tmp);
                }else {
                    over=begin.replaceFirst(subbegin,tmp);
                }


                System.out.println(begin+"--"+over+"  "+"活動："+item.getItemName()+"  "+
                        " 花費 "+(int)item.getCost()+"時" +(int)((item.getCost()-(int)item.getCost())*60.0) +"分    "
                        +"剩餘可支配時間："+(int)remain+"時"+(int)((remain-(int)remain)*60.0)+"分    ");
                begin= over;

            }
            else if(sum>getTotalTime()){
                System.out.println("活動："+item.getItemName()+" 時長 "
                        +(int)item.getCost()+"時" +(int)((item.getCost()-(int)item.getCost())*60.0) +"分    "+"超出可支配時間");
            }
            else {
                System.out.println("無其他可支配！");
                break;
            }
        }


    }
}
