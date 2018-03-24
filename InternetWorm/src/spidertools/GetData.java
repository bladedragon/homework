package spidertools;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetData {
public static Map<String,String> getClassName(String stuID) throws IOException {
    String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);

    Pattern pattern = Pattern.compile("<tr\\s?><td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>");
    Matcher matcher = pattern.matcher(data);
    Map<String,String> clazz = new LinkedHashMap<>();
    while(matcher.find())
    {
       clazz.put(matcher.group(1),matcher.group(2));
    }
    return clazz;
    }
    public static ArrayList<String> getClassNum(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        Pattern pattern = Pattern.compile("<tr\\s?><td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>");
        Matcher matcher = pattern.matcher(data);
        ArrayList<String> name = new ArrayList<>();
        while(matcher.find())
        {
            name.add(matcher.group(1));
        }
        return name;
    }

    public static ArrayList<String> getType(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        Pattern pattern = Pattern.compile("<tr\\s?><td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>");
        Matcher matcher = pattern.matcher(data);
        ArrayList<String>  type = new ArrayList<>();
        while(matcher.find())
        {
            type.add(matcher.group(3));
        }
        return type;
    }
    public static ArrayList<String> getclassification(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        Pattern pattern = Pattern.compile("<tr\\s?><td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\' align=\'center\'>(.*?)</td>");
        Matcher matcher = pattern.matcher(data);
        ArrayList<String> classification = new ArrayList<>();
        while(matcher.find())
        {
            classification.add(matcher.group(4));
        }
        return classification;
    }
    public static ArrayList<String> getState(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        Pattern pattern = Pattern.compile("<tr\\s?><td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\' align=\'center\'>(.*?)</td>\\n?\\s*?<td rowspan=\'\\d\' align=\'center\'>(.*?)</td>");
        Matcher matcher = pattern.matcher(data);
        ArrayList<String> state = new ArrayList<>();
        while(matcher.find())
        {
            state.add(matcher.group(5));
        }
        return state;
    }
       public static Map<String,ArrayList<String >> getSchedule(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        String str = "<td\\s?>(.{2,6})\\s?</td>\\n?\\s*?<td>星期(\\d)\\s?第(.{3,4})节\\s?(.*?)</td><td>(.*?)</td>";
        String   newstr =str.replaceAll("\\s{2,}"," ");
        Pattern pattern = Pattern.compile(newstr);
        Matcher matcher = pattern.matcher(data);
        Map<String, ArrayList<String>> schedule = new LinkedHashMap<>();

        while (matcher.find()) {

            ArrayList<String> time = new ArrayList<>();
            if (schedule.containsKey(matcher.group(1))) {
                for (int i = 1; i < 4; i++) {
                    schedule.get(matcher.group(1)).add(matcher.group(1 + i));
                }
            } else {
                for (int i = 1; i < 4; i++) {
                    time.add(matcher.group(1 + i));
                }
                schedule.put(matcher.group(1), time);
            }

        }
        return schedule;
    }
    public static Map<String,ArrayList<String>> getPlace(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        String str = "<td\\s?>(.{2,6})\\s?</td>\\n?\\s*?<td>星期(\\d)\\s?第(.{3,4})节\\s?(.*?)</td><td>(.*?)</td>";
        String   newstr=str.replaceAll("\\s{2,}"," ");
        Pattern pattern = Pattern.compile(newstr);
        Matcher matcher = pattern.matcher(data);
        Map<String,ArrayList<String>> place = new LinkedHashMap<>();

        while(matcher.find()){
            ArrayList<String> p = new ArrayList<>();
            p.add(matcher.group(5));
            if(place.containsKey(matcher.group(1))){
                place.get(matcher.group(1)).add(matcher.group(5));
            }else
            place.put(matcher.group(1),p);
        }
        return place;
    }
    public static Map<String,String> getClassToTeacher(String stuID) throws IOException {
        String data = Tools.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stuID);
        String str = "<td rowspan='(\\d)' align='center'>.*?</td>\\n?\\s?<td>(.{2,6})</td>\\n?\\s*?<td>星期(\\d)\\s?第(.{3,4})节\\s?(.*?)</td><td>(.*?)</td>";
        String   newstr=str.replaceAll("\\s{2,}"," ");
        Pattern pattern = Pattern.compile(newstr);
        Matcher matcher = pattern.matcher(data);
        ArrayList<String> classnum = getClassNum(stuID);
        Map<String, String > classToTeacher = new LinkedHashMap();
        int i=0;
//        for (String string:getClassName()){
//            classToTeacher.put(string,null);
//        }
            while (matcher.find()) {
//           String n = matcher.group(1);
//            int num = Integer.parseInt(n);
//            for( int i=1;i<=num;i++){
//                classToTeacher.get(classname.get(i-1)).add(matcher.group(2));
            classToTeacher.put( classnum.get(i),matcher.group(2));
            i++;

        }

        return classToTeacher;
    }

    public static void main(String[] args) throws IOException {
        Map<String,String> num = getClassName("2017210246");
        for(String key:num.keySet()){
            System.out.println(key+"===="+num.get(key));
        }
        System.out.println("--------------------班级代号--------------------");
        Map<String,String> cTot = getClassToTeacher("2017210246");
        for(String key:cTot.keySet()){
            System.out.println(key+"===="+cTot.get(key));
        }
        System.out.println("--------------------关系--------------------");
        Map<String, ArrayList<String>> p = getPlace("2017210246");
        for(Map.Entry<String, ArrayList<String>> entry : p.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for(String value : values) {
                System.out.println(key + " --> " + value);
            }
        }
        System.out.println("--------------------地点--------------------");
        ArrayList<String> cnum = getClassNum("2017210246");
        for(String tmp:cnum)
            System.out.println(tmp);

        System.out.println("--------------------班级名称--------------------");
        ArrayList<String> type = getType("2017210246");
        for(String tmp:type)
            System.out.println(tmp);
        System.out.println("--------------------类型--------------------");
        ArrayList<String> state = getState("2017210246");
        for(String tmp:state)
            System.out.println(tmp);

        System.out.println("--------------------状态--------------------");
        ArrayList<String> cf = getclassification("2017210246");
        for(String tmp:cf)
            System.out.println(tmp);

        System.out.println("--------------------分类--------------------");
        Map<String, ArrayList<String>> ct = getSchedule("2016210049");
        Set<String> keys = ct.keySet();
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            ArrayList arrayList = ct.get(key);
            for(Object o : arrayList) {
                System.out.println(key+"==="+o);
            }
        }

        System.out.println("--------------------时间--------------------");
    }
}


