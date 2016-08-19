package com.chen.mycontacts.util;

/**
 * Created by peng.cheng on 2016/8/16.
 */
public class SeveralUtil {
    public static String getInfoFromPhoneNumber(String phoneNumber) {
        String result = "default";
        switch (phoneNumber.length()) {
            case 11:
                String first = phoneNumber.substring(0, 3);
                switch(first) {
                    case "135":
                    case "136":
                    case "137":
                    case "138":
                    case "139":
                    case "147":
                    case "150":
                    case "151":
                    case "152":
                    case "157":
                    case "158":
                    case "159":
                    case "182":
                    case "183":
                    case "184":
                    case "187":
                    case "188":
                    case "178":
                        result = "中国移动";
                        break;
                    case "130":
                    case "131":
                    case "132":
                    case "145":
                    case "155":
                    case "156":
                    case "185":
                    case "186":
                    case "176":
                        result = "中国联通";
                        break;
                    case "133":
                    case "153":
                    case "180":
                    case "181":
                    case "189":
                    case "177":
                        result = "中国电信";
                        break;
                    case "170":
                        char fourth = phoneNumber.charAt(3);
                        switch (fourth) {
                            case '0':
                                result = "中国电信";
                                break;
                            case '5':
                                result = "中国移动";
                                break;
                            case '9':
                                result = "中国联通";
                                break;
                        }
                        break;
                    case "134":
                        if(phoneNumber.charAt(3) == '9') {
                            result = "中国电信";
                        } else {
                            result = "中国移动";
                        }
                        break;
                    default:
                        break;
                }
        }
        return result;
    }
}
