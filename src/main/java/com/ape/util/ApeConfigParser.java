package com.ape.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.android.common.speech.LoggingEvents.VoiceSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ApeConfigParser {
    private static final String BRAND_NAME_PROP = "ro.product.brand";
    private static final String CUSTOMER_COUNTRY_PROP = "ro.product.locale.region";
    private static final String CUSTOMER_OPERATOR_PROP = "ro.product.operator";
    private static final boolean DEBUG = false;
    private static final int MAX_INCLUDE_DEPTH = 10;
    private static final String MTK_HARDWARE = "mt";
    private static final String MTK_RIL = "mtk";
    private static final String PLATEFORM_INFO_HARDWARE = "ro.hardware";
    private static final String PLATEFORM_INFO_RIL = "gsm.version.ril-impl";
    private static final String PRODUCT_NAME_PROP = "ro.target";
    private static final String QUALCOMM_HARDWARE = "qcom";
    private static final String QUALCOMM_RIL = "qualcomm";
    private static final String TAG = "ApeConfigParser";
    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static final String[] validType = new String[]{"bool", "integer", "string", "string-array", "integer-array"};
    private String FEATURE_FILE_PATH;
    private String FEATURE_FILE_PATH_DEBUG;
    private String brandName;
    private File configFile = null;
    private String configFilePathInApk;
    private String configVersion;
    private String customerCountry;
    private String customerOperator;
    private InputStream fileStream;
    private int includeDepth;
    private boolean isConfigFileExist = false;
    private boolean isConfigInApk = false;
    private boolean isDebugMode = false;
    private final Context mContext;
    private final HashMap<String, String> mTypeMap = new HashMap();
    private final HashMap<String, String> mValueMap = new HashMap();
    private String module;
    private PLATEFORM plateform = null;
    private String productName;
    private String requirementsVersion;
    private boolean searchConfigfromOperator = false;
    private boolean searchConfigfromTrunk = false;
    private boolean showDebugToast = true;
    private XmlPullParser xmlParser;

    private enum PLATEFORM {
        QUALCOMM,
        MTK,
        OTHER
    }

    public ApeConfigParser(Context context, String path) {
        this.mContext = context;
        getCustomerInfo();
        getConfigFilePath(path);
        Log.i(TAG, "ApeConfigParser init, config path: " + this.FEATURE_FILE_PATH);
        initFeatureConfigIfNull();
        if (this.xmlParser != null) {
            configParserAll();
        }
    }

    public ApeConfigParser(Context context, String path, boolean showtoast) {
        this.mContext = context;
        this.showDebugToast = showtoast;
        getCustomerInfo();
        getConfigFilePath(path);
        Log.i(TAG, "ApeConfigParser init, config path: " + this.FEATURE_FILE_PATH);
        initFeatureConfigIfNull();
        if (this.xmlParser != null) {
            Log.i(TAG, "ApeConfigParser configParserAll start...");
            configParserAll();
        }
    }

    private void getConfigFilePath(String path) {
        if (path != null) {
            if (path.startsWith("/")) {
                this.FEATURE_FILE_PATH = Environment.getRootDirectory() + path;
                this.FEATURE_FILE_PATH_DEBUG = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
                return;
            }
            this.FEATURE_FILE_PATH = Environment.getRootDirectory() + "/" + path;
            this.FEATURE_FILE_PATH_DEBUG = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
        }
    }

    private void initFeatureConfig() {
        if (this.xmlParser == null) {
            if (this.FEATURE_FILE_PATH_DEBUG == null || this.FEATURE_FILE_PATH == null) {
                this.isConfigInApk = getConfigFilePathInApk(true);
            } else {
                this.configFile = new File(this.FEATURE_FILE_PATH_DEBUG);
                if (this.configFile.exists() && this.configFile.isFile()) {
                    this.isDebugMode = true;
                    this.isConfigInApk = false;
                    if (this.showDebugToast && this.mContext != null) {
                        Toast.makeText(this.mContext, "Current is debug mode...", 1).show();
                    }
                    Log.i(TAG, "ApeConfigParser is debug mode.");
                } else {
                    this.isDebugMode = false;
                    this.isConfigInApk = getExactConfigFilePathInApk();
                    if (!this.isConfigInApk) {
                        this.configFile = new File(this.FEATURE_FILE_PATH);
                    }
                }
            }
            if (this.isConfigInApk) {
                this.isConfigFileExist = true;
                this.xmlParser = Xml.newPullParser();
                Log.i(TAG, "ApeConfigParser isConfigInApk, configFilePathInApk:" + this.configFilePathInApk);
            } else if (this.configFile != null && this.configFile.exists() && this.configFile.isFile()) {
                this.isConfigFileExist = true;
                this.xmlParser = Xml.newPullParser();
            } else if (getConfigFilePathInApk(true)) {
                this.isConfigInApk = true;
                this.isConfigFileExist = true;
                this.xmlParser = Xml.newPullParser();
                Log.i(TAG, "ApeConfigParser isConfigInApk, configFilePathInApk:" + this.configFilePathInApk);
            } else {
                this.isConfigFileExist = false;
                Log.e(TAG, "ApeConfigParser initFeatureConfig configFile not exist...");
            }
        }
    }

    private boolean isValidType(String type) {
        return type != null && Arrays.asList(validType).contains(type);
    }

    private void initFeatureConfigIfNull() {
        if (this.xmlParser == null) {
            initFeatureConfig();
        }
    }

    private boolean getConfigInApk() {
        if (this.mContext == null) {
            return false;
        }
        try {
            if (this.mContext.getAssets().list("config").length != 0) {
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isAssetFileExist(String path) {
        if (this.mContext == null || path == null) {
            return false;
        }
        try {
            this.mContext.getAssets().open(path).close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTrunkProject(String brandName) {
        return TextUtils.isEmpty(brandName) || brandName.equalsIgnoreCase("tinno") || brandName.equalsIgnoreCase("Android") || brandName.equalsIgnoreCase("alps");
    }

    private boolean getExactConfigFilePathInApk() {
        if (this.mContext == null) {
            return false;
        }
        String directoryName = this.brandName;
        if (isTrunkProject(directoryName)) {
            directoryName = "trunk";
        }
        if (!TextUtils.isEmpty(this.customerOperator)) {
            directoryName = this.customerOperator;
        }
        directoryName = directoryName.toLowerCase();
        if (directoryName.equalsIgnoreCase("trunk")) {
            if (!TextUtils.isEmpty(this.productName)) {
                this.configFilePathInApk = "config/trunk/config_" + this.productName.toLowerCase() + ".xml";
            }
        } else if (!TextUtils.isEmpty(this.customerCountry) && !TextUtils.isEmpty(this.productName)) {
            this.configFilePathInApk = "config/" + directoryName + "/config_" + this.customerCountry.toLowerCase() + "_" + this.productName.toLowerCase() + ".xml";
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config_" + this.productName.toLowerCase() + ".xml";
            }
        } else if (!TextUtils.isEmpty(this.productName)) {
            this.configFilePathInApk = "config/" + directoryName + "/config_" + this.productName.toLowerCase() + ".xml";
        }
        return isAssetFileExist(this.configFilePathInApk);
    }

    private boolean getConfigFilePathInApk(boolean operatorSearch) {
        if (this.mContext == null) {
            return false;
        }
        this.searchConfigfromTrunk = false;
        this.searchConfigfromOperator = false;
        String directoryName = this.brandName;
        if (isTrunkProject(directoryName)) {
            directoryName = "trunk";
        }
        if (operatorSearch && !TextUtils.isEmpty(this.customerOperator)) {
            directoryName = this.customerOperator;
            this.searchConfigfromOperator = true;
        }
        directoryName = directoryName.toLowerCase();
        if (directoryName.equalsIgnoreCase("trunk")) {
            this.searchConfigfromTrunk = true;
            if (TextUtils.isEmpty(this.productName)) {
                this.configFilePathInApk = "config/trunk/config.xml";
            } else {
                this.configFilePathInApk = "config/trunk/config_" + this.productName.toLowerCase() + ".xml";
                if (!isAssetFileExist(this.configFilePathInApk)) {
                    this.configFilePathInApk = "config/trunk/config.xml";
                }
            }
        } else if (!TextUtils.isEmpty(this.customerCountry) && !TextUtils.isEmpty(this.productName)) {
            this.configFilePathInApk = "config/" + directoryName + "/config_" + this.customerCountry.toLowerCase() + "_" + this.productName.toLowerCase() + ".xml";
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config_" + this.productName.toLowerCase() + ".xml";
            }
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config_" + this.customerCountry.toLowerCase() + ".xml";
            }
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config.xml";
            }
        } else if (!TextUtils.isEmpty(this.customerCountry)) {
            this.configFilePathInApk = "config/" + directoryName + "/config_" + this.customerCountry.toLowerCase() + ".xml";
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config.xml";
            }
        } else if (TextUtils.isEmpty(this.productName)) {
            this.configFilePathInApk = "config/" + directoryName + "/config.xml";
        } else {
            this.configFilePathInApk = "config/" + directoryName + "/config_" + this.productName.toLowerCase() + ".xml";
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/" + directoryName + "/config.xml";
            }
        }
        if (isAssetFileExist(this.configFilePathInApk)) {
            return true;
        }
        if (this.searchConfigfromTrunk) {
            return false;
        }
        if (this.searchConfigfromOperator) {
            Log.i(TAG, "ApeConfigParser getConfigFilePathInApk not found in operator search...");
            return getConfigFilePathInApk(false);
        }
        if (TextUtils.isEmpty(this.productName)) {
            this.configFilePathInApk = "config/trunk/config.xml";
        } else {
            this.configFilePathInApk = "config/trunk/config_" + this.productName.toLowerCase() + ".xml";
            if (!isAssetFileExist(this.configFilePathInApk)) {
                this.configFilePathInApk = "config/trunk/config.xml";
            }
        }
        return isAssetFileExist(this.configFilePathInApk);
    }

    private void configParserAll() {
        this.includeDepth = 0;
        try {
            if (this.isConfigInApk) {
                this.fileStream = this.mContext.getAssets().open(this.configFilePathInApk);
            } else {
                this.fileStream = new FileInputStream(this.configFile);
            }
            this.xmlParser.setInput(this.fileStream, "UTF-8");
            int eventType = this.xmlParser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 2:
                        String mtype = this.xmlParser.getName();
                        if (!mtype.equals("include")) {
                            if (!isValidType(mtype)) {
                                break;
                            }
                            String key_name = this.xmlParser.getAttributeValue(null, "name");
                            String value = this.xmlParser.getAttributeValue(null, VoiceSearch.EXTRA_QUERY_UPDATED_VALUE);
                            if (key_name == null) {
                                break;
                            }
                            this.mValueMap.put(key_name, value);
                            this.mTypeMap.put(key_name, mtype);
                            break;
                        }
                        subConfigParserAll(this.xmlParser.getAttributeValue(null, "file"));
                        break;
                    default:
                        break;
                }
                eventType = this.xmlParser.next();
            }
            if (this.fileStream != null) {
                this.fileStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.i(TAG, "FileNotFoundException-file " + this.FEATURE_FILE_PATH);
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            Log.i(TAG, "Exception in config xml parser ");
            e2.printStackTrace();
        } catch (IOException e3) {
            Log.i(TAG, "initFeatureConfig--IOException");
            e3.printStackTrace();
        }
    }

    private void subConfigParserAll(String filename) {
        if (filename != null) {
            File file = null;
            String filePath = null;
            String filePathInApk = null;
            if (this.isConfigInApk) {
                if (filename.startsWith("/")) {
                    filePathInApk = "config" + filename;
                } else {
                    filePathInApk = this.configFilePathInApk.substring(0, this.configFilePathInApk.lastIndexOf(47)) + "/" + filename;
                }
                if (isAssetFileExist(filePathInApk)) {
                    Log.i(TAG, "ApeConfigParser configParserAll sub-file-InApk:" + filePathInApk);
                } else {
                    return;
                }
            }
            if (this.isDebugMode) {
                filePath = this.FEATURE_FILE_PATH_DEBUG.substring(0, this.FEATURE_FILE_PATH_DEBUG.lastIndexOf(47)) + "/" + filename;
            } else {
                filePath = this.FEATURE_FILE_PATH.substring(0, this.FEATURE_FILE_PATH.lastIndexOf(47)) + "/" + filename;
            }
            file = new File(filePath);
            if (file.exists() && file.isFile()) {
                Log.i(TAG, "ApeConfigParser configParserAll sub-file:" + filePath);
            } else {
                return;
            }
            this.includeDepth++;
            if (this.includeDepth > 10) {
                throw new IllegalStateException("The include depth is more than MAX_INCLUDE_DEPTH: 10");
            }
            try {
                InputStream subfileStream;
                if (this.isConfigInApk) {
                    subfileStream = this.mContext.getAssets().open(filePathInApk);
                } else {
                    subfileStream = new FileInputStream(file);
                }
                XmlPullParser subXmlParser = Xml.newPullParser();
                subXmlParser.setInput(subfileStream, "UTF-8");
                for (int eventType = subXmlParser.getEventType(); eventType != 1; eventType = subXmlParser.next()) {
                    switch (eventType) {
                        case 2:
                            String mtype = subXmlParser.getName();
                            if (!mtype.equals("include")) {
                                if (!isValidType(mtype)) {
                                    break;
                                }
                                String key_name = subXmlParser.getAttributeValue(null, "name");
                                String value = subXmlParser.getAttributeValue(null, VoiceSearch.EXTRA_QUERY_UPDATED_VALUE);
                                if (key_name == null) {
                                    break;
                                }
                                this.mValueMap.put(key_name, value);
                                this.mTypeMap.put(key_name, mtype);
                                break;
                            }
                            subConfigParserAll(subXmlParser.getAttributeValue(null, "file"));
                            break;
                        default:
                            break;
                    }
                }
                if (subfileStream != null) {
                    subfileStream.close();
                }
            } catch (FileNotFoundException e) {
                Log.i(TAG, "FileNotFoundException-file " + filePath);
                e.printStackTrace();
            } catch (XmlPullParserException e2) {
                Log.i(TAG, "Exception in config xml parser ");
                e2.printStackTrace();
            } catch (IOException e3) {
                Log.i(TAG, "initFeatureConfig--IOException");
                e3.printStackTrace();
            }
        }
    }

    private void configInfoParser() {
        try {
            if (this.isConfigInApk) {
                this.fileStream = this.mContext.getAssets().open(this.configFilePathInApk);
            } else {
                this.fileStream = new FileInputStream(this.configFile);
            }
            this.xmlParser.setInput(this.fileStream, "UTF-8");
            int eventType = this.xmlParser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 2:
                        String mtype = this.xmlParser.getName();
                        if (mtype != null && (mtype.equalsIgnoreCase("resources") || mtype.equalsIgnoreCase("configs"))) {
                            this.module = this.xmlParser.getAttributeValue(null, "module");
                            this.configVersion = this.xmlParser.getAttributeValue(null, "configVersion");
                            this.requirementsVersion = this.xmlParser.getAttributeValue(null, "requirementsVersion");
                            Log.i(TAG, "ApeConfigParser configInfoParser, module: " + this.module + ",configVersion:" + this.configVersion + ",requirementsVersion:" + this.requirementsVersion);
                            if (this.fileStream != null) {
                                this.fileStream.close();
                                return;
                            }
                            return;
                        }
                    default:
                        break;
                }
                eventType = this.xmlParser.next();
            }
            if (this.fileStream != null) {
                this.fileStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.i(TAG, "FileNotFoundException-file " + this.FEATURE_FILE_PATH);
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            Log.i(TAG, "Exception in config xml parser ");
            e2.printStackTrace();
        } catch (IOException e3) {
            Log.i(TAG, "initFeatureConfig--IOException");
            e3.printStackTrace();
        }
    }

    public boolean getBoolean(String name) {
        String value = null;
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            return false;
        }
        if (this.mValueMap.containsKey(name)) {
            value = (String) this.mValueMap.get(name);
        }
        if (value == null) {
            return false;
        }
        if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }

    public boolean getBoolean(String name, boolean defvalue) {
        String value = null;
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            return defvalue;
        }
        if (this.mValueMap.containsKey(name)) {
            value = (String) this.mValueMap.get(name);
        }
        if (value == null) {
            return defvalue;
        }
        boolean z = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
        return z;
    }

    public int getInteger(String name) {
        String value = null;
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            return -1;
        }
        if (this.mValueMap.containsKey(name)) {
            value = (String) this.mValueMap.get(name);
        }
        if (value != null) {
            return Integer.valueOf(value).intValue();
        }
        return -1;
    }

    public int getInteger(String name, int defvalue) {
        String value = null;
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            return defvalue;
        }
        if (this.mValueMap.containsKey(name)) {
            value = (String) this.mValueMap.get(name);
        }
        return value != null ? Integer.valueOf(value).intValue() : defvalue;
    }

    public String getString(String name) {
        initFeatureConfigIfNull();
        if (this.xmlParser != null && this.mValueMap.containsKey(name)) {
            return (String) this.mValueMap.get(name);
        }
        return null;
    }

    public String getString(String name, String defvalue) {
        initFeatureConfigIfNull();
        if (this.xmlParser == null || !this.mValueMap.containsKey(name)) {
            return defvalue;
        }
        String value = (String) this.mValueMap.get(name);
        Log.i(TAG, "ApeConfigParser, getString-name: " + name + ",value:" + value);
        return value;
    }

    public int[] getIntArray(String name) {
        int[] iArr = null;
        initFeatureConfigIfNull();
        if (this.xmlParser != null && this.mValueMap.containsKey(name)) {
            String value = (String) this.mValueMap.get(name);
            if (value != null) {
                String[] strarray;
                if (value.contains(",")) {
                    strarray = value.split(",");
                } else if (value.contains(";")) {
                    strarray = value.split(";");
                } else {
                    strarray = value.contains("/") ? value.split("/") : new String[]{value};
                }
                if (strarray != null) {
                    int len = strarray.length;
                    iArr = new int[len];
                    for (int i = 0; i < len; i++) {
                        iArr[i] = Integer.valueOf(strarray[i]).intValue();
                    }
                }
            }
        }
        return iArr;
    }

    public String[] getStringArray(String name) {
        initFeatureConfigIfNull();
        if (this.xmlParser == null || !this.mValueMap.containsKey(name)) {
            return null;
        }
        String value = (String) this.mValueMap.get(name);
        if (value == null) {
            return null;
        }
        String[] strarray;
        if (value.contains(",")) {
            strarray = value.split(",");
        } else if (value.contains(";")) {
            strarray = value.split(";");
        } else {
            strarray = value.contains("/") ? value.split("/") : new String[]{value};
        }
        return strarray;
    }

    public HashMap<String, String> getValueMap() {
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            Log.e(TAG, "ApeConfigParser -getValueMap failed,xmlParser is null");
            return null;
        }
        if (this.mValueMap.isEmpty()) {
            configParserAll();
        }
        return this.mValueMap;
    }

    public HashMap<String, String> getTypeMap() {
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            Log.e(TAG, "ApeConfigParser -getTypeMap failed,xmlParser is null");
            return null;
        }
        if (this.mTypeMap.isEmpty()) {
            configParserAll();
        }
        return this.mTypeMap;
    }

    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    public boolean isConfigFileExist() {
        return this.isConfigFileExist;
    }

    public boolean isHaveItem(String name) {
        initFeatureConfigIfNull();
        if (this.xmlParser == null || this.mValueMap == null || !this.mValueMap.containsKey(name)) {
            return false;
        }
        return true;
    }

    public String getMoudleName() {
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            Log.e(TAG, "ApeConfigParser -getMoudleName failed,xmlParser is null");
            return null;
        }
        configInfoParser();
        return this.module;
    }

    public String getConfigVersion() {
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            Log.e(TAG, "ApeConfigParser -getConfigVersion failed,xmlParser is null");
            return null;
        }
        configInfoParser();
        return this.configVersion;
    }

    public String getRequirementsVersion() {
        initFeatureConfigIfNull();
        if (this.xmlParser == null) {
            Log.e(TAG, "ApeConfigParser -getRequirementsVersion failed,xmlParser is null");
            return null;
        }
        configInfoParser();
        return this.requirementsVersion;
    }

    public String getCustomerCountry() {
        return this.customerCountry;
    }

    public String getCustomerOperator() {
        return this.customerOperator;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getCustomerBrand() {
        return this.brandName;
    }

    private void getCustomerInfo() {
        this.customerCountry = getProperty(CUSTOMER_COUNTRY_PROP);
        this.customerOperator = getProperty(CUSTOMER_OPERATOR_PROP);
        this.productName = getProperty(PRODUCT_NAME_PROP);
        this.brandName = getProperty(BRAND_NAME_PROP);
        if (this.brandName != null && this.brandName.equalsIgnoreCase("wiko")) {
            String wikoUnify = getProperty("ro.wiko.type");
            if (wikoUnify != null && wikoUnify.equalsIgnoreCase("unify")) {
                this.customerCountry = getProperty("persist.ro.customer.country");
                if (this.customerCountry == null || this.customerCountry.length() == 0) {
                    this.customerCountry = getProperty("persist.sys.area.current");
                }
            }
        }
        Log.i(TAG, "ApeConfigParser -brandName:" + this.brandName + " ,Country:" + this.customerCountry + " ,Operator:" + this.customerOperator + " ,productName:" + this.productName);
    }

    private String getProperty(String key) {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");
                mGetMethod = mClassType.getDeclaredMethod("get", new Class[]{String.class});
            }
            if (mGetMethod == null) {
                return null;
            }
            return (String) mGetMethod.invoke(mClassType, new Object[]{key});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PLATEFORM getPlatform() {
        if (this.plateform == null) {
            String plateformInfoHardware = getProperty(PLATEFORM_INFO_HARDWARE);
            String plateformInfoRIL = getProperty(PLATEFORM_INFO_RIL);
            Log.i(TAG, "getPlatform, plateformInfoHardware : " + plateformInfoHardware + " , plateformInfoRIL : " + plateformInfoRIL);
            if (plateformInfoRIL != null) {
                if (plateformInfoRIL.toLowerCase().contains(QUALCOMM_RIL)) {
                    this.plateform = PLATEFORM.QUALCOMM;
                } else if (plateformInfoRIL.toLowerCase().contains(MTK_RIL)) {
                    this.plateform = PLATEFORM.MTK;
                } else {
                    this.plateform = PLATEFORM.OTHER;
                }
            }
            if ((this.plateform == null || this.plateform == PLATEFORM.OTHER) && plateformInfoHardware != null) {
                if (plateformInfoHardware.toLowerCase().contains(QUALCOMM_HARDWARE)) {
                    this.plateform = PLATEFORM.QUALCOMM;
                } else if (plateformInfoHardware.toLowerCase().contains(MTK_HARDWARE)) {
                    this.plateform = PLATEFORM.MTK;
                } else {
                    this.plateform = PLATEFORM.OTHER;
                }
            }
            Log.i(TAG, "getPlatform, plateform : " + this.plateform);
        }
        return this.plateform;
    }

    public boolean isQualcommPlatform() {
        return PLATEFORM.QUALCOMM == getPlatform();
    }

    public boolean isMtkPlatform() {
        return PLATEFORM.MTK == getPlatform();
    }
}
