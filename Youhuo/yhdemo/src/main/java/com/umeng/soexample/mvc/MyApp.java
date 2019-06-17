package com.umeng.soexample.mvc;


import com.jess.arms.base.BaseApplication;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApp extends BaseApplication {
    public static final String appId = "2016092300574180";
    public static final String primaryKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRgO" +
            "DLTlt4L2MurPjGnSNk9cg/zdeAidB2MkENylxHIzjN1jyr8Yn5FQRxCs6YwIBRJkIK7LwRzwOocr/6yM34UYuif" +
            "THLfo1Zg77vbx2tx33sWUoMC5yOdfeFIVvR3Pacat+X31rC/drU7v2qKpAx0rMxu1dhXhnrHfCER+slr7tyynjjc" +
            "w2cyYfmnz9/QxuQ8jmvF5KR6l8lQsoUoNxUBgXwZjTWUJyfO10vLmyeozf5cGr2FWFB4KVvS5kCreWuXglnydAWT" +
            "ELewD8CcVabxv9EN1p7hAKfMJ3Alh2n37cdJhmQH2r4hF/c58dQOCX7hrnNuyUhBQlzcYiP4yP5AgMBAAECggEAe" +
            "x1+l+g0U0xdctgfJAdANKQXDY3Bg9yYi/pXuzB3xiWSYfCdo6ub4Cudd3z6dJult4RUq2PGepccdEKsGKoiRbPSH+" +
            "oo4ROyzIRCYdw2U52R+hNPKUQF8zD0ACzKwZdi/9UaXio6ZPVhXXzfHEYpfk7XGwR2mkeCTMQnh/ZGjLqMf+Wb9Big" +
            "QYRYeIqQxrg2OC+Fxm5nmaXZsQvTtLC0j79J14UPNF+Az8bK2ASChRncJXSq8ngs6NYlClUbXwxTKGTh7ytqEq8J3Z" +
            "jG/JfGmXc2S/cqT2S/L0aIiKjZg0po/6WXuu6KZsryPbDecZlYHclXeI/2BX04lJW4YQY4gQKBgQDdc2CmihtQ2KUY7" +
            "7hPXmdttmJ+6XW2y9nHrc1hzQbxoZ18aBXCTy8FbHTklvqtoaUGrVvZCMiSZMcS+iHNlqfouT9OJdZT9FtKqAQI3FE2" +
            "j1DC9MrMjcxDvc6Sv1uleltyaZDgsJWW6rwKmwEIhbM7eX90pFDUJz1kswl9wXs26QKBgQCoNDUY2Wle8Hemo+bHkiX" +
            "LBOQ7B0dDguWemFNr8VzaVqarFOWkCuzPHyESc0OCTR7C0CjvruqZWn+MCdssB/1HyIweUsWpATWqXyeNXNBcKyTl7y5" +
            "nz98mS4XSaPNdbwL6CcjUGfDV0zYMlMkvYG2KDxa3QnLeN1OCkAFigL56kQKBgG3g/8XipOcad+DFoUgQaXVwl7KtGDFZ" +
            "OHfxbwTl/FoMfN3rr1A4hfZE+EMr/roEiKOlhPtNdmfdBUIPRpKB/FJnCFg4Qf5qMpn6f/X9i3Zs6f+yAqY4fW/nUAZUW" +
            "J7k9VbxtmcmdzLoyeDhREP8QxBxmqI/nbOGvNmoVD9Hr2FxAoGBAJlUBfbh8QC/WYLJM81GtzbK/bWlW9v5zZD1sE3cll" +
            "Td2k5n3THdV6My8DXeZnK4FEtn73kVzPsAGPax4V3Q5pxx/vCBaXc3CBGHbgmZysHJ/nevwXCWeAdznsiALSBF2sPM+Y" +
            "oHH5f7UfDEDGUolJHeUUm6w/U3H+QLm4eMbpZRAoGASzx6PcI42e+J7voKLktJnIOYBx9Zg/Y7vyQ6EYeeBBgf9+RrgjL" +
            "DS3CLb4KEBNebnUvAc5Sj8YnXXpMA4OgCtM+BxMMBa1NLoCaLKchCnd1/r70/yk6n2PgsQMBpT3h61U7OifoMB7451+bNMf" +
            "+ueA6w/FE3qBJ038DUBguwIe4=";

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.setLogEnabled(true);
        //UMConfigure.init(this,"5c981ea161f564699f0002f0","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

}
