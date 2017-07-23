package learn.com.newssimple.Utils;



import java.util.List;

import learn.com.newssimple.beans.ExpressName;

/**
 * Created by zyx on 2017/7/13.
 */

public class AddExpressName {

    public static List<ExpressName> addData(List<ExpressName> expressList){
        if (expressList!=null) {
            expressList.add(new ExpressName("顺丰", "sf"));
            expressList.add(new ExpressName("申通", "sto"));
            expressList.add(new ExpressName("圆通", "yt"));
            expressList.add(new ExpressName("韵达",""));
            expressList.add(new ExpressName("天天","tt"));
            expressList.add(new ExpressName("EMS","ems"));
            expressList.add(new ExpressName("中通","zto"));
            expressList.add(new ExpressName("汇通","ht"));
            expressList.add(new ExpressName("全峰","qf"));
            expressList.add(new ExpressName("德邦","db"));
            expressList.add(new ExpressName("国通","gt"));
            expressList.add(new ExpressName("如风达","rfd"));
            expressList.add(new ExpressName("京东快递","jd"));
            expressList.add(new ExpressName("宅急送","zjs"));
            expressList.add(new ExpressName("EMS国际","emsg"));
            expressList.add(new ExpressName("Fedex国际","fedex"));
            expressList.add(new ExpressName("邮政国内（挂号信）","yzgn"));
            expressList.add(new ExpressName("UPS国际快递","ups"));
            expressList.add(new ExpressName("中铁快运","ztky"));
        }return expressList;
    }

  }
