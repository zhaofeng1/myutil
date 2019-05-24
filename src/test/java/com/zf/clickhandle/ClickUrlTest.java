package com.zf.clickhandle;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author zhaofeng
 * @Date2019/4/25 10:22
 * @Version V1.0
 **/
public class ClickUrlTest {

    @Test
    public void getS2sClickUrl() {
        String path = "E:\\logs\\clickurl\\data_s2s.txt";
        try {
            File f = new File(path);
            List<String> list = FileUtils.readLines(f);
            String[] tempArray = null;
            String offerid = "";
            String subsite = "";
            String geo = "";
            String appinfoid = "";
            String templateUrl = "http://pixel.admobclick.com/v1/ad/click?subsite_id=%s&transaction_id=davm&id=%s&offer_id=%s&geo=%s&aid={idfa}&client_version=10&idfa={idfa}&tmark=1536993257793&t=1&p71=1&p26=0&p4={tid}";

            for (String s : list) {
//                System.out.println(s);
                tempArray = s.split("\t");
                if (tempArray != null && tempArray.length == 12) {
                    offerid = tempArray[1];
                    subsite = tempArray[3];
                    geo = tempArray[5];
                    appinfoid = tempArray[11];

                    System.out.println(String.format(templateUrl, subsite, appinfoid, offerid, geo));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getS2sClickUrlV1() {
        String path = "E:\\logs\\clickurl\\data_s2s_v1.txt";
        try {
            File f = new File(path);
            List<String> list = FileUtils.readLines(f);
            String[] tempArray = null;
            String offerid = "";
            String subsite = "";
            String geo = "";
            String appinfoid = "";
            String templateUrl = "http://pixel.admobclick.com/v1/ad/click?subsite_id=%s&transaction_id=davm&id=%s&offer_id=%s&geo=%s&aid={idfa}&client_version=10&idfa={idfa}&tmark=1536993257793&t=1&p71=1&p26=0&p4={tid}";

            for (String s : list) {
//                System.out.println(s);
                tempArray = s.split("\t");
                if (tempArray != null && tempArray.length == 8) {
                    offerid = tempArray[4];
                    subsite = tempArray[3];
                    geo = tempArray[1];
                    appinfoid = tempArray[7];

                    System.out.println(String.format(templateUrl, subsite, appinfoid, offerid, geo));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSdkClickUrl() {
        String path = "E:\\logs\\clickurl\\data_sdk.txt";
        try {
            File f = new File(path);
            List<String> list = FileUtils.readLines(f);
            String[] tempArray = null;
            String subsite = "";
            String sourceid = "";
            String source = "";
            String maxpayout = "";
            String appid = "";
            String pid = "";
            String offerid = "";
            String geo = "";
            String appinfoid = "";
            String templateUrl = "http://ad.click.kaffnet.com/v1/click?type=01&p1=%s&p2=%s&p3=%s&p8=%s&p12=%s&p26=0&p4={tid}&p5=%s&p6=%s&p7={idfa}&p11=en&p15=%s&p71=1&t=1&p100={idfa}";

            for (String s : list) {
                tempArray = s.split("\t");
                if (tempArray != null && tempArray.length == 13) {
                    appid = tempArray[0];
                    offerid = tempArray[1];
                    source = tempArray[2];
                    subsite = tempArray[3];
                    pid = tempArray[4];
                    geo = tempArray[5];
                    maxpayout = tempArray[6];
                    appinfoid = tempArray[11];
                    sourceid = tempArray[12];

                    System.out.println(String.format(templateUrl, subsite, sourceid, source, maxpayout, offerid, pid, geo, appid));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws Exception {
        String str = "https://rv-gateway.supersonicads.com/gateway/sdk/request?callback=jQuery19102567935582732769_1556502174895&v=3.0.17&controllerId=1556502174895_0.4870893650732032&applicationKey=8bd578d5&applicationUserId=14C5E250-7EEE-4EC4-B096-23FED1338A53&debug=0&appOrientation=none&connectionType=4g&deviceOs=android&SDKVersion=5.60&deviceOSVersion=9&deviceOEM=apple&deviceVolume=0.9261964500083091&immersiveMode=false&deviceIds[AID]=14C5E250-7EEE-4EC4-B096-23FED1338A53&deviceWidthDP=278&deviceHeightDP=494&deviceLanguage=ZH-HANS-CN&deviceModel=iPhone 6S&diskFreeSize=32768&country=&isLimitAdTrackingEnabled=false&bundleId=com.mobi.pengqiu&jb=false&mcc=0&mnc=0&appVersion=1.0.4&batteryLevel=100&localTime=1556502174887&timezoneOffset=-240&deviceScreenScale=2.7&androidIsVersion=1556502174887&sdkAbName=1004.1&moatSupported=true&deviceApiLevel=28&demandSource=SupersonicAds&impressionsCount=0&fpf=58&integrationType=A&_=1556502174887";
        String enStr = URLEncoder.encode(str, "UTF-8");
        System.out.println(enStr);
        URI uri = new URI(str);
        System.out.println(uri.toString());
    }
}
