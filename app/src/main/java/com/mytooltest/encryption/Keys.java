/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.mytooltest.encryption;


public final class Keys {
	
	private static final String CHARSET = "UTF-8";
	
	
    //（RSA）公钥
	public static final String PUBLIC = "KSApATkuXiJtPhQjOicFRys/IGwsNDEoL1MzIS8hbS8MNSIsACUrBTZCAionJSUlMQtFAUwoUQtYB1IBQF8DXisuUhpYEUFaQQBmXiMiHAUNEEA5LHoZHVIOKQIdLgMoayUqCCIhD00CXjVsABdRCz1WHRo9CnoDDy4hNw4xODo0QQUVJRsFBRAfLQkcWQ4eEyUpMDsFPEVCDBQTAhAeLi0BAV9XIyJdJiU/QTd5DiQeWkESJgddLEoLKE8BHhVbGTQoaF4SJg4PD0wnIV0aD04TIComJS4s";
	public static final String SALT = "dingtone.me";

	private static final String PUBLIC_DTPAY_DN1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn8mHGBUleTc/vX+3lnXBkbiIGJWDxRyVSLWl/ttqSlNstHm/BeTJ4t8mSFazcOxKe4Gmkf4YwfnRti1Wn3vs12ScNIyQ2d6Kx9Uc5mSDofsbyXUiM2IfY69FKFbAkqEV4C2dSRyb1d4XtMxuKXZBisFB05JMMAEnyjMgnRPkLkwIDAQAB";
	private static final String PUBLIC_DTPAY_PN1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDH+HTJgiCTn/nuXy8PI2rmxh3ChdCT3LClOHbbsyX+AVUKPpJ+8ssRYW0H0szv+0z5GZ21mvLdlZZnI/u+CF6kGgtJH6lbkrb6aH00c+p6py8pTdVNQLd+l8uaG/rfIOwl/xAY4l6E3HA/hJVILcGIj6Tp2cWm9uhjPBfjrb0qoQIDAQAB";

	public static final String PUBLIC_CREDIT_CARD_PHOTO = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy1K8C0dryB24N4b3ULf1DNnsa\n" +
			"dz6Te9Wi1n3tpiDY8LySQIuTwcUMgkx23qsCJN9OOrVvZUyK1WLIamTojZ9aO2G2\n" +
			"jLACJ2F4JFBMcdbjmEYfNeG6U1JeMnQlFvWP52p3p5/zvQoSfji/L6vQ8y0jOgfC\n" +
			"bz6Ng6CMB5sd9s8bFQIDAQAB";


	/**
	 * Restore the original key.
	 * 
	 * 
	 * @param key the covering key
	 * @param salt the salt using to recover original key
	 * @return
	 */
	public static String recover(String key, String salt) {
		
		byte[] keyBytes = null;
		try
		{
			keyBytes = Coder.decryptBASE64(key);
			byte[] saltBytes = salt.getBytes(CHARSET);
			
			for(int i = 0; i < keyBytes.length; i++) {
				keyBytes[i] = (byte)(keyBytes[i] ^ saltBytes[i % saltBytes.length]);
			}
			
			return new String(keyBytes, CHARSET);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return key;
	}

	public static String getPublicKeyForDTPay() {
		return PUBLIC_DTPAY_PN1;
	}
}
